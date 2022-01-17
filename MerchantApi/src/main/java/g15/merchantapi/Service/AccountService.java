package g15.merchantapi.Service;

import implementation.IMessagingClient;
import implementation.MessagingClientFactory;
import messages.register.MerchantRegisterMessage;
import messages.register.MerchantRegisterResponse;

import javax.inject.Singleton;

@Singleton
public class AccountService {
    private final IMessagingClient client;

    public AccountService() {
        this.client = MessagingClientFactory.createAwaitableClient();
    }

    public MerchantRegisterResponse merchantRegister(MerchantRegisterMessage request) {
        try {
            return client.call(request, MerchantRegisterResponse.class);
        } catch (Exception e) {
            return new MerchantRegisterResponse(null, false, e.getMessage());
        }
    }
}
