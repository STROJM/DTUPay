package g15.customerapi.Service;

import implementation.IMessagingClient;
import implementation.MessagingClientFactory;
import messages.tokens.TokensRequestMessage;
import messages.tokens.TokensResponseMessage;

import javax.inject.Singleton;

@Singleton
public class TokenService {
    private final IMessagingClient messagingClient;
    public TokenService(){
        this.messagingClient = MessagingClientFactory.createAwaitableClient();
    }

    public TokensResponseMessage requestTokens(TokensRequestMessage s) {
        try {
            return messagingClient.call(s, TokensResponseMessage.class);
        } catch (Exception e) {
            return new TokensResponseMessage(false, e.getMessage(), null);
        }
    }
}
