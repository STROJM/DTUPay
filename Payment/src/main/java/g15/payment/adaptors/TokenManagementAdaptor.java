package g15.payment.adaptors;

import g15.payment.PaymentService;
import g15.payment.exceptions.InvalidPaymentException;
import g15.payment.messages.EnrichedPaymentMessage;
import g15.payment.messages.PaymentResponseMessage;
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
        PaymentResponseMessage response = null;

        try {
            this.paymentService.performPayment(payment);
            response = new PaymentResponseMessage();
        } catch (InvalidPaymentException e) {
            response = new PaymentResponseMessage(e.getMessage());
        }

        Event responseEvent = new Event("PaymentFinishedMessage", new Object[] { response });
        this.queue.publish(responseEvent);
    }
}
