package g15.token;

import g15.token.services.MessageService;
import messaging.implementations.RabbitMqQueue;

public class StartUp {
	public static void main(String[] args) throws Exception {
		new StartUp().startUp();
	}

	private void startUp() throws Exception {
		System.out.println("startup");
		var mq = new RabbitMqQueue("rabbitMq");
		new MessageService(mq);
	}
}
