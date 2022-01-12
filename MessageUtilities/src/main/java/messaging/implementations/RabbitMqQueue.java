package messaging.implementations;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import messaging.Event;
import messaging.MessageQueue;

public class RabbitMqQueue implements MessageQueue {

	private final String default_topic = "events";
	private final String queueType = "topic";
	private final String exchange = "events_exchange";

	private Channel channel;

	public RabbitMqQueue(String hostname) {
		channel = setUpChannel(hostname);
	}


	private Channel setUpChannel(String hostname) {
		Channel channel;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(hostname);
			Connection connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(exchange, queueType);
		} catch (IOException | TimeoutException e) {
			throw new Error(e);
		}
		return channel;
	}


	@Override
	public void publish(Event event) {
		publish(event, default_topic);
	}

	@Override
	public void publish(Event event, String topic) {
		String message = new Gson().toJson(event);
		System.out.println("[x] Publish event " + message);
		try {
			channel.basicPublish(exchange, topic, null, message.getBytes("UTF-8"));
		} catch (IOException e) {
			throw new Error(e);
		}
	}


	@Override
	public void addHandler(String eventType, Consumer<Event> handler) {
		addHandler(eventType, handler, default_topic);
	}

	@Override
	public void addHandler(String eventType, Consumer<Event> handler, String topic) {
		System.out.println("[x] handler "+handler+" for event type " + eventType + " installed");
		try {
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, exchange, topic);

			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");

				System.out.println("[x] handle event "+message);

				Event event = new Gson().fromJson(message, Event.class);

				if (eventType.equals(event.getType())) {
					handler.accept(event);
				}
			};
			channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
			});
		} catch (IOException e1) {
			throw new Error(e1);
		}
	}
}