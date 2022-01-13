package g15.customerapi.Service;

import g15.customerapi.Service.messages.CustomerRegisterMessage;
import g15.customerapi.Service.messages.CustomerRegisterResponse;
import messaging.v2.IMessagingClient;
import messaging.v2.RabbitMqClient;

import javax.inject.Singleton;

@Singleton
public class AccountService {
    private final IMessagingClient client;
//    private final MessageQueue queue;
//    private CompletableFuture<CustomerRegisterResponse> customerRegisterResponse;
//
//    public AccountService() {
//        queue = new RabbitMqQueue("rabbitMq");
//        queue.addHandler("CustomerRegisterFinishedMessage", this::handleCustomerRegisterResponse);
//    }

    public AccountService(){
        this.client = RabbitMqClient.create();
    }
    public CustomerRegisterResponse customerRegister(CustomerRegisterMessage request){
        try {
            return client.call(request, CustomerRegisterResponse.class);
        } catch (Exception e) {
            return new CustomerRegisterResponse(null, false, e.getMessage());
        }
    }
//    public CustomerRegisterResponse customerRegister(CustomerRegisterMessage s) {
//        customerRegisterResponse = new CompletableFuture<>();
//        Event event = new Event("CustomerRegisterMessage", new Object[] { s });
//        queue.publish(event);
//
//        return customerRegisterResponse.join();
//    }
//
//    public void handleCustomerRegisterResponse(Event e) {
//        var s = e.getArgument(0, CustomerRegisterResponse.class);
//        customerRegisterResponse.complete(s);
//    }
}
