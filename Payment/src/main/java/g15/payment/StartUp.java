package g15.payment;

import g15.payment.adaptors.BankAdaptor;
import g15.payment.adaptors.MessageAdaptor;
import g15.payment.repositories.PaymentRepository;
import messaging.v2.RabbitMqClient;

public class StartUp {
	public static void main(String[] args) throws Exception {
		new StartUp().startUp();
	}

	private void startUp() {
		System.out.println("startup");
		var queue = new RabbitMqClient("rabbitMq");

		BankAdaptor bankAdaptor = new BankAdaptor();
		PaymentRepository paymentRepository = new PaymentRepository();
		PaymentService service = new PaymentService(paymentRepository, bankAdaptor);
		new MessageAdaptor(queue, service);
		System.out.println("payment running");
	}
}
