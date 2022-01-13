package g15.customerapi.Service;

import g15.customerapi.messages.TokensRequestMessage;
import g15.customerapi.messages.TokensResponseMessage;
import messaging.Event;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class TokenService {
    private final MessageQueue queue;
    private CompletableFuture<TokensResponseMessage> tokens;

    public TokenService() {
        queue = new RabbitMqQueue("rabbitMq");
        queue.addHandler("TokensResponse", this::handleTokenResponse);
    }

    public TokensResponseMessage requestTokens(TokensRequestMessage s) {
        tokens = new CompletableFuture<>();
        Event event = new Event("TokensRequest", new Object[] { s });
        queue.publish(event);
        return tokens.join();
    }

    public void handleTokenResponse(Event e) {
        var s = e.getArgument(0, TokensResponseMessage.class);
        tokens.complete(s);
    }
}
