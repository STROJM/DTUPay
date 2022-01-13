package g15.token;

import g15.token.services.MessageService;
import messaging.v2.RabbitMqClient;

public class StartUp {
	public static void main(String[] args) throws Exception {
		new StartUp().startUp();
	}

	private void startUp() {
		System.out.println("startup");
		var mq = new RabbitMqClient("rabbitMq");
		new MessageService(mq);
		System.out.println("token-management running");
	}
}
