package messaging;

import java.util.function.Consumer;

public interface MessageQueue {

	void publish(Event message);
	void publish(Event message, String topic);
	void addHandler(String eventType, Consumer<Event> handler);
	void addHandler(String eventType, Consumer<Event> handler, String topic);
}
