package g15.account;

import messaging.implementations.RabbitMqQueue;

public class StartUp {
	public static void main(String[] args) throws Exception {
		new StartUp().startUp();
	}

	private void startUp() throws Exception {
		System.out.println("startup");
		var mq = new RabbitMqQueue("rabbitMq");

		while(true){
			Thread.sleep(1000);
			System.out.println("up");
		}
	}
}
