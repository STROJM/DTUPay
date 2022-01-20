package g15.merchantapi.Service;

import implementation.IMessagingClient;
import implementation.MessagingClientFactory;
import messages.payment.PaymentMessage;
import messages.payment.PaymentResponseMessage;
import messages.payment.RefundMessage;

import javax.inject.Singleton;

/**
 * @author Tobias Olrik Birck Kristensen
 * @author Oliver Ro Møltoft Christensen s176352
 */
@Singleton
public class TokenService {
    private final IMessagingClient client;

    public TokenService() {
        this.client = MessagingClientFactory.createAwaitableClient();
    }

    public PaymentResponseMessage pay(PaymentMessage request){
        try {
            return client.call(request, PaymentResponseMessage.class);
        } catch (Exception e) {
            return new PaymentResponseMessage(e.getMessage());
        }
    }

    public PaymentResponseMessage refund(RefundMessage request){
        try {
            //Måske skal vi drøfte "PaymentResponseMessage", da det i dette tilfælde omhandler "Refund"
            return client.call(request, PaymentResponseMessage.class);
        } catch (Exception e) {
            return new PaymentResponseMessage(e.getMessage());
        }
    }
}
