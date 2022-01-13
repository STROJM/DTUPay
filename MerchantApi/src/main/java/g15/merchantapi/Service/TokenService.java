package g15.merchantapi.Service;

import g15.merchantapi.Service.messages.PaymentMessage;
import g15.merchantapi.Service.messages.PaymentResponseMessage;
import messaging.Event;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class TokenService {
    private final MessageQueue queue;
    private CompletableFuture<PaymentResponseMessage> response;

    public TokenService() {
        queue = new RabbitMqQueue("rabbitMq");
        queue.addHandler("PaymentFinishedMessage", this::handleTokenResponse);
    }

    public PaymentResponseMessage pay(PaymentMessage s) {
        response = new CompletableFuture<>();
        Event event = new Event("PaymentRequest", new Object[]{s});
        queue.publish(event);
        return response.join();
    }

    public void handleTokenResponse(Event e) {
        var s = e.getArgument(0, PaymentResponseMessage.class);
        response.complete(s);
    }
}
