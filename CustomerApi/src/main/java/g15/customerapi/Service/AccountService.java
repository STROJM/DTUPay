package g15.customerapi.Service;

import g15.customerapi.messages.CustomerRegisterMessage;
import g15.customerapi.messages.CustomerRegisterResponse;
import g15.customerapi.messages.TokensRequestMessage;
import g15.customerapi.messages.TokensResponseMessage;
import messaging.Event;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Singleton
public class AccountService {
    private final MessageQueue queue;
    private CompletableFuture<CustomerRegisterResponse> customerRegisterResponse;

    public AccountService() {
        queue = new RabbitMqQueue("rabbitMq");
        queue.addHandler("CustomerRegisterFinishedMessage", this::handleCustomerRegisterResponse);

    }

    public CustomerRegisterResponse customerRegister(CustomerRegisterMessage s) {
        customerRegisterResponse = new CompletableFuture<>();
        Event event = new Event("CustomerRegisterMessage", new Object[] { s });
        queue.publish(event);

        return customerRegisterResponse.join();
    }

    public void handleCustomerRegisterResponse(Event e) {
        var s = e.getArgument(0, CustomerRegisterResponse.class);
        customerRegisterResponse.complete(s);
    }
}
