package g15.payment.adaptors;

import g15.payment.PaymentService;
import g15.payment.messages.EnrichedPaymentMessage;
import g15.payment.repositories.PaymentRepository;
import messaging.Event;
import messaging.MessageQueue;

public class TokenManagementAdaptor {
    private MessageQueue queue;
    private PaymentService paymentService;

    public TokenManagementAdaptor(MessageQueue queue, PaymentService paymentService) {
        this.queue = queue;
        this.paymentService = paymentService;
        this.queue.addHandler("EnrichedPaymentMessage", this::handleEnrichedPaymentEvent);
    }

    public void handleEnrichedPaymentEvent(Event event) {
        var payment = event.getArgument(0, EnrichedPaymentMessage.class);
        var response = this.paymentService.performPayment(payment);
        Event responseEvent = new Event("PaymentFinishedMessage", new Object[] { response });
        this.queue.publish(responseEvent);
    }
}
