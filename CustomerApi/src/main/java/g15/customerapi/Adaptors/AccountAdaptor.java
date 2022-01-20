package g15.customerapi.Adaptors;

import messages.register.CustomerRegisterMessage;
import messages.register.CustomerRegisterResponse;
import implementation.MessagingClient;
import implementation.MessagingClientFactory;

import javax.inject.Singleton;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 * @author Tobias Olrik Birck Kristensen
 */
@Singleton
public class AccountAdaptor {
    private final MessagingClient client;

    public AccountAdaptor(){
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
