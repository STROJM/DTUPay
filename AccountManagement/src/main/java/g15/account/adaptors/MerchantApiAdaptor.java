package g15.account.adaptors;

import g15.account.exceptions.InvalidBankAccountException;
import g15.account.messages.MerchantRegisterMessage;
import g15.account.messages.MerchantRegisterResponse;
import g15.account.services.AccountService;
import messaging.v2.IMessagingClient;
import messaging.v2.Message;

public class MerchantApiAdaptor {
    private final IMessagingClient client;
    private AccountService accountService;

    public MerchantApiAdaptor(IMessagingClient client, AccountService accountService){
        this.client = client;
        this.accountService = accountService;
        this.client.register(this::handleMerchantRegisterEvent, MerchantRegisterMessage.class);
    }

    public void handleMerchantRegisterEvent(Message<MerchantRegisterMessage> message) {
        var registryAttempt = message.model;
        MerchantRegisterResponse response = null;

        try {
            this.accountService.registerUserAccount(registryAttempt);
            response = new MerchantRegisterResponse(registryAttempt.getBankAccountNumber(), true, "");
        } catch (InvalidBankAccountException e) {
            response = new MerchantRegisterResponse(registryAttempt.getBankAccountNumber(), false, e.getMessage());
        }
        this.client.reply(message.update(response));
    }
}
