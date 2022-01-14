package messaging.v2;

import com.google.gson.Gson;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class RabbitMqClient implements IMessagingClient{
    private final Map<String, CallAwaiter> awaiters = new ConcurrentHashMap<>();
    private final Channel channel;
    private static final Charset ENCODING = StandardCharsets.UTF_8;
    private static final String EXCHANGE = "events";
    private static final String QUEUE_TYPE = "direct";
    private String replyQueueName = null;

    public RabbitMqClient(String host) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE, QUEUE_TYPE);
            System.out.printf("Connected to exchange %s with queue type %s%n", EXCHANGE, QUEUE_TYPE);
        } catch (IOException | TimeoutException e) {
            throw new Error(e);
        }
    }

    public void enableAwaitingCalls() {
        try {
            if(replyQueueName != null) return;
            replyQueueName = channel.queueDeclare().getQueue();
            registerAwaiters();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> void log(Message<T> message, String method){
        System.out.printf("Calling %s with message %s%n", method, message.model.getClass().getSimpleName());
    }
    private void log(String message){
        System.out.println(message);
    }

    public <TModel> void reply(Message<TModel> message){
        try {
            log(message, "reply");
            var corrId = message.delivery.getProperties().getCorrelationId();
            var replyTo = message.delivery.getProperties().getReplyTo();
            var props = getProperties(corrId);
            channel.basicPublish("", replyTo, props, serialize(message.model));
        } catch (IOException e1) {
            throw new Error(e1);
        }
    }

    public <TModel> void forward(Message<TModel> message, Class<TModel> modelType) {
        try {
            log(message, "forward");
            var corrId = message.delivery.getProperties().getCorrelationId();
            var replyTo = message.delivery.getProperties().getReplyTo();
            var props = getProperties(corrId, replyTo);
            channel.basicPublish(EXCHANGE, modelType.getSimpleName(), props, serialize(message.model));
        } catch (IOException e1) {
            throw new Error(e1);
        }
    }

    public <TModel> void register(Consumer<Message<TModel>> handler, Class<TModel> modelClass) {
        try {
            log("register");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE, modelClass.getSimpleName());

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                try {
                    var model = deserialize(delivery.getBody(), modelClass);
                    handler.accept(Message.from(delivery, model));
                } catch (Exception e)
                {
                    System.out.printf("Consumption error: %s%n", e.getMessage());
                    throw new Error(e);
                }
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
        } catch (IOException e1) {
            throw new Error(e1);
        }
    }

    public <TRequest,TResponse> TResponse call(TRequest request, Class<TResponse> responseType) throws IOException, InterruptedException {
        if (replyQueueName == null) throw new IllegalStateException("Awaitable calls not enabled");

        final String corrId = UUID.randomUUID().toString();
        final String routing_key = request.getClass().getSimpleName();
        var props = getProperties(corrId, replyQueueName);

        final BlockingQueue<TResponse> response = new ArrayBlockingQueue<>(1);
        awaiters.put(corrId, CallAwaiter.from(response, responseType));
        channel.basicPublish(EXCHANGE, routing_key, props, serialize(request));

        return response.poll(10, TimeUnit.SECONDS);
    }

    private void registerAwaiters() throws IOException {
        channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            var awaiter = awaiters.remove(delivery.getProperties().getCorrelationId());
            if (awaiter == null) return;
            var model = deserialize(delivery.getBody(), awaiter.getResponseType());
            log("Awaiter released, with response type: " + awaiter.getResponseType().getSimpleName());
            awaiter.getResponse().offer(model);
        }, consumerTag -> {
        });
    }

    private AMQP.BasicProperties getProperties(String corrId) {
        return new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .build();
    }

    private AMQP.BasicProperties getProperties(String corrId, String replyQueueName) {
        return new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();
    }

    private <TResponse> TResponse deserialize(byte[] bytes, Class<TResponse> responseType) {
        return new Gson().fromJson(new String(bytes, ENCODING), responseType);
    }

    private <TModel> byte[] serialize(TModel model){
        return new Gson().toJson(model).getBytes(ENCODING);
    }
}
