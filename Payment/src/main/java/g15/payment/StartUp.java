package g15.payment;

import g15.payment.adaptors.BankAdaptor;
import g15.payment.adaptors.MessageAdaptor;
import g15.payment.repositories.EventStore;
import implementation.MessagingClientFactory;

public class StartUp {
	public static void main(String[] args) throws Exception {
		new StartUp().startUp();
	}

	private void startUp() {
		System.out.println("startup");
		var client = MessagingClientFactory.create();

		BankAdaptor bankAdaptor = new BankAdaptor();
		var eventStore = new EventStore(client);
		PaymentService service = new PaymentService(eventStore, bankAdaptor);
		new MessageAdaptor(client, service);
		System.out.println("payment running");
	}
}
