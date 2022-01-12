package g15.account.adaptors;

import g15.account.exceptions.InvalidBankAccountException;
import g15.account.messages.MerchantRegisterMessage;
import g15.account.messages.MerchantRegisterResponse;
import g15.account.services.AccountService;
import messaging.Event;
import messaging.MessageQueue;

public class MerchantApiAdaptor {
    private MessageQueue queue;
    private AccountService accountService;

    public MerchantApiAdaptor(MessageQueue queue, AccountService accountService) {
        this.queue = queue;
        this.accountService = accountService;
        this.queue.addHandler("MerchantRegisterMessage", this::handleMerchantRegisterEvent);
    }

    public void handleMerchantRegisterEvent(Event event) {
        var registryAttempt = event.getArgument(0, MerchantRegisterMessage.class);
        MerchantRegisterResponse response = null;

        try {
            this.accountService.registerUserAccount(registryAttempt);
            response = new MerchantRegisterResponse(registryAttempt.getBankAccountNumber(), true, "");
        } catch (InvalidBankAccountException e) {
            response = new MerchantRegisterResponse(registryAttempt.getBankAccountNumber(), false, e.getMessage());
        }

        Event responseEvent = new Event("CustomerRegisterFinishedMessage", new Object[] { response });
        this.queue.publish(responseEvent);
    }
}
