package g15.merchantapi.Service;

import g15.merchantapi.Service.messages.PaymentMessage;
import g15.merchantapi.Service.messages.PaymentResponseMessage;
import g15.merchantapi.Service.messages.RefundMessage;
import messaging.v2.IMessagingClient;
import messaging.v2.MessagingClientFactory;

import javax.inject.Singleton;

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
