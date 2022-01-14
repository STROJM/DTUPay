package g15.customerapi.Service;

import g15.customerapi.Service.messages.CustomerRegisterMessage;
import g15.customerapi.Service.messages.CustomerRegisterResponse;
import messaging.v2.IMessagingClient;
import messaging.v2.MessagingClientFactory;

import javax.inject.Singleton;

@Singleton
public class AccountService {
    private final IMessagingClient client;

    public AccountService(){
        this.client = MessagingClientFactory.createAwaitableClient();
    }
    public CustomerRegisterResponse customerRegister(CustomerRegisterMessage request){
        try {
            return client.call(request, CustomerRegisterResponse.class);
        } catch (Exception e) {
            return new CustomerRegisterResponse(null, false, e.getMessage());
        }
    }
}
