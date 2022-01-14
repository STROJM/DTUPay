package g15.merchantapi.Service;

import g15.merchantapi.Service.messages.MerchantRegisterMessage;
import g15.merchantapi.Service.messages.MerchantRegisterResponse;
import messaging.v2.IMessagingClient;
import messaging.v2.MessagingClientFactory;

import javax.inject.Singleton;

@Singleton
public class AccountService {
    private final IMessagingClient client;

    public AccountService() {
        this.client = MessagingClientFactory.create();
    }

    public MerchantRegisterResponse merchantRegister(MerchantRegisterMessage request) {
        try {
            return client.call(request, MerchantRegisterResponse.class);
        } catch (Exception e) {
            return new MerchantRegisterResponse(null, false, e.getMessage());
        }
    }
}
