package g15.merchantapi.Service;

import g15.merchantapi.Service.messages.MerchantRegisterMessage;
import g15.merchantapi.Service.messages.MerchantRegisterResponse;
import messaging.Event;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class AccountService {
    private final MessageQueue queue;
    private CompletableFuture<MerchantRegisterResponse> customerRegisterResponse;

    public AccountService() {
        queue = new RabbitMqQueue("rabbitMq");
        queue.addHandler("CustomerRegisterFinishedMessage", this::handleCustomerRegisterResponse);

    }

    public MerchantRegisterResponse merchantRegister(MerchantRegisterMessage s) {
        customerRegisterResponse = new CompletableFuture<>();
        Event event = new Event("CustomerRegisterMessage", new Object[]{s});
        queue.publish(event);

        return customerRegisterResponse.join();
    }

    public void handleCustomerRegisterResponse(Event e) {
        var s = e.getArgument(0, MerchantRegisterResponse.class);
        customerRegisterResponse.complete(s);
    }
}
