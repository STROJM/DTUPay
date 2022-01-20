package g15.merchantapi.Adaptors;

import implementation.MessagingClient;
import implementation.MessagingClientFactory;
import messages.register.MerchantRegisterMessage;
import messages.register.MerchantRegisterResponse;

import javax.inject.Singleton;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
@Singleton
public class AccountAdaptor {
    private final MessagingClient client;

    public AccountAdaptor() {
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
