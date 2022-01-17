package g15.account.adaptors;

import g15.account.exceptions.InvalidBankAccountException;
import g15.account.services.AccountService;
import implementation.IMessagingClient;
import implementation.Message;
import messages.register.CustomerRegisterMessage;
import messages.register.CustomerRegisterResponse;

public class CustomerApiAdaptor {
    private final IMessagingClient client;
    private final AccountService accountService;

    public CustomerApiAdaptor(IMessagingClient client, AccountService accountService){
        this.client = client;
        this.accountService = accountService;
        this.client.register(this::handleCustomerRegisterEvent, CustomerRegisterMessage.class);
    }

    public void handleCustomerRegisterEvent(Message<CustomerRegisterMessage> message) {
        var registryAttempt = message.model;
        CustomerRegisterResponse response = null;

        try {
            this.accountService.registerUserAccount(registryAttempt);
            response = new CustomerRegisterResponse(registryAttempt.getBankAccountNumber(), true, "");
        } catch (InvalidBankAccountException e) {
            response = new CustomerRegisterResponse(registryAttempt.getBankAccountNumber(), false, e.getMessage());
        }

        this.client.reply(message.update(response));
    }
}
