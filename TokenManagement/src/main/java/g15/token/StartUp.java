package g15.token;

import g15.token.adaptors.MessageAdaptor;
import implementation.MessagingClientFactory;

/**
 * @author Roar Nind Steffensen
 */
public class StartUp {
	public static void main(String[] args) {
		new StartUp().startUp();
	}

	private void startUp() {
		System.out.println("startup");
		var mq = MessagingClientFactory.create();
		new MessageAdaptor(mq);
		System.out.println("token-management running");
	}
}
