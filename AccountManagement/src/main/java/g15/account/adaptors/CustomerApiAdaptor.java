package g15.account.adaptors;

import g15.account.exceptions.InvalidBankAccountException;
import g15.account.messages.CustomerRegisterMessage;
import g15.account.messages.CustomerRegisterResponse;
import g15.account.services.AccountService;
import messaging.Event;
import messaging.MessageQueue;

public class CustomerApiAdaptor {
    private MessageQueue queue;
    private AccountService accountService;

    public CustomerApiAdaptor(MessageQueue queue, AccountService accountService) {
        this.queue = queue;
        this.accountService = accountService;
        this.queue.addHandler("CustomerRegisterMessage", this::handleCustomerRegisterEvent);
    }

    public void handleCustomerRegisterEvent(Event event) {
        var registryAttempt = event.getArgument(0, CustomerRegisterMessage.class);
        CustomerRegisterResponse response = null;

        try {
            this.accountService.registerUserAccount(registryAttempt);
            response = new CustomerRegisterResponse(registryAttempt.getBankAccountNumber(), true, "");
        } catch (InvalidBankAccountException e) {
            response = new CustomerRegisterResponse(registryAttempt.getBankAccountNumber(), false, e.getMessage());
        }

        Event responseEvent = new Event("CustomerRegisterFinishedMessage", new Object[] { response });
        this.queue.publish(responseEvent);
    }
}
