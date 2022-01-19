package g15.account.adaptors;

import g15.account.exceptions.InvalidBankAccountException;
import g15.account.services.AccountService;
import implementation.IMessagingClient;
import implementation.Message;
import messages.register.CustomerRegisterMessage;
import messages.register.CustomerRegisterResponse;
import messages.tokens.TokensRequestMessage;
import messages.tokens.TokensResponseMessage;
import messages.tokens.ValidatedTokensRequestMessage;

/**
 * @author Johannes Hald s202784
 */

public class CustomerApiAdaptor {
    private final IMessagingClient client;
    private final AccountService accountService;

    public CustomerApiAdaptor(IMessagingClient client, AccountService accountService){
        this.client = client;
        this.accountService = accountService;
        this.client.register(this::handleCustomerRegisterEvent, CustomerRegisterMessage.class);
        this.client.register(this::handleValidateTokensRequestEvent, TokensRequestMessage.class);
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

    public void handleValidateTokensRequestEvent(Message<TokensRequestMessage> message) {
        if (this.accountService.isAccountRegistered(message.model.getCustomerBankAccount())) {
            var validatedRequest = new ValidatedTokensRequestMessage(message.model.getCustomerBankAccount(), message.model.getTokensAmount());
            this.client.forward(message.update(validatedRequest), ValidatedTokensRequestMessage.class);
        } else {
            var response = new TokensResponseMessage(false, "unknown customer", new String[0]);
            this.client.reply(message.update(response));
        }
    }
}
