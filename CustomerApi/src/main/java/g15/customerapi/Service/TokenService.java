package g15.customerapi.Service;

import g15.customerapi.messages.TokensRequestMessage;
import g15.customerapi.messages.TokensResponseMessage;
import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;

public class TokenService {
    private final MessageQueue queue;
    private CompletableFuture<TokensResponseMessage> tokens;

    public TokenService(MessageQueue q) {
        queue = q;
        queue.addHandler("StudentIdAssigned", this::handleTokenRespons);
    }

    public TokensResponseMessage requestTokens(TokensRequestMessage s) {
        tokens = new CompletableFuture<>();
        Event event = new Event("StudentRegistrationRequested", new Object[] { s });
        queue.publish(event);
        return tokens.join();
    }

    public void handleTokenRespons(Event e) {
        var s = e.getArgument(0, TokensResponseMessage.class);
        tokens.complete(s);
    }
}
