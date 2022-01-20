package g15.customerapi.Service;

import messages.register.CustomerRegisterMessage;
import messages.register.CustomerRegisterResponse;
import implementation.IMessagingClient;
import implementation.MessagingClientFactory;

import javax.inject.Singleton;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 * @author Tobias Olrik Birck Kristensen
 */
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
