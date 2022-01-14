package g15.customerapi.Service;

import g15.customerapi.Service.messages.TokensRequestMessage;
import g15.customerapi.Service.messages.TokensResponseMessage;
import messaging.v2.IMessagingClient;
import messaging.v2.MessagingClientFactory;

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
