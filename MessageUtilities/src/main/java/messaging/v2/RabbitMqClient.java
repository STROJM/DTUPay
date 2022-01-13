package messaging.v2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class RabbitMqClient implements IMessagingClient{
    private final Channel channel;
    private static final Charset ENCODING = StandardCharsets.UTF_8;
    private static final String EXCHANGE = "events";
    private static final String QUEUE_TYPE = "direct";

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

    private <T> void log(Message<T> message, String method){
        System.out.printf("Calling %s with message %s%n", method, message.model.getClass().getName());
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
        final String corrId = UUID.randomUUID().toString();
        final String routing_key = request.getClass().getSimpleName();

        String replyQueueName = channel.queueDeclare().getQueue();
        var props = getProperties(corrId, replyQueueName);

        channel.basicPublish(EXCHANGE, routing_key, props, serialize(request));

        final BlockingQueue<TResponse> response = new ArrayBlockingQueue<>(1);

        var channelTag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                TResponse model = deserialize(delivery.getBody(), responseType);
                response.offer(model);
            }
        }, consumerTag -> {
        });

        var result = response.poll(10, TimeUnit.SECONDS);
        channel.basicCancel(channelTag);
        return result;
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
