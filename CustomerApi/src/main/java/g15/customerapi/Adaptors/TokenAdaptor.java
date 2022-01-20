package g15.customerapi.Adaptors;

import implementation.MessagingClient;
import implementation.MessagingClientFactory;
import messages.tokens.TokensRequestMessage;
import messages.tokens.TokensResponseMessage;

import javax.inject.Singleton;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 * @author Tobias Olrik Birck Kristensen
 */
@Singleton
public class TokenAdaptor {
    private final MessagingClient messagingClient;
    public TokenAdaptor(){
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
