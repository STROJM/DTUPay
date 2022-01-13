package g15.account;

import g15.account.adaptors.BankAdaptor;
import g15.account.adaptors.CustomerApiAdaptor;
import g15.account.adaptors.MerchantApiAdaptor;
import g15.account.repositories.AccountRepository;
import g15.account.services.AccountService;
import messaging.v2.RabbitMqClient;

public class StartUp {
	public static void main(String[] args) throws Exception {
		new StartUp().startUp();
	}

	private void startUp() throws Exception {
		System.out.println("startup");

		var customerClient = new RabbitMqClient("rabbitMq");
		var merchantClient = new RabbitMqClient("rabbitMq");

		BankAdaptor bankAdaptor = new BankAdaptor();
		AccountRepository accountRepository = new AccountRepository();
		AccountService service = new AccountService(accountRepository, bankAdaptor);

		new CustomerApiAdaptor(customerClient, service);
		new MerchantApiAdaptor(merchantClient, service);

		System.out.println("account running");
	}
}
