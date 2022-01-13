package g15.customerapi.Service;

import g15.customerapi.messages.TokensRequestMessage;
import g15.customerapi.messages.TokensResponseMessage;
import messaging.v2.IMessagingClient;

@Singleton
public class TokenService {
    private final IMessagingClient messagingClient;
    public TokenService(IMessagingClient messagingClient){
        this.messagingClient = messagingClient;
    }

    public TokensResponseMessage requestTokens(TokensRequestMessage s) {
        try {
            return messagingClient.call(s, TokensResponseMessage.class);
        } catch (Exception e) {
            return new TokensResponseMessage(false, e.getMessage(), null);
        }
    }
}
