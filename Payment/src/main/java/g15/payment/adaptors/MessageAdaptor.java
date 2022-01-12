package g15.payment.adaptors;

import g15.payment.PaymentService;
import g15.payment.exceptions.InvalidPaymentException;
import g15.payment.messages.EnrichedPaymentMessage;
import g15.payment.messages.EnrichedRefundMessage;
import g15.payment.messages.PaymentResponseMessage;
import messaging.Event;
import messaging.MessageQueue;

public class MessageAdaptor {
    private MessageQueue queue;
    private PaymentService paymentService;

    public MessageAdaptor(MessageQueue queue, PaymentService paymentService) {
        this.queue = queue;
        this.paymentService = paymentService;
        this.queue.addHandler("EnrichedPaymentMessage", this::handleEnrichedPaymentEvent);
        this.queue.addHandler("EnrichedRefundMessage", this::handleEnrichedRefundEvent);
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

    public void handleEnrichedRefundEvent(Event event) {
        var refund = event.getArgument(0, EnrichedRefundMessage.class);
        PaymentResponseMessage response = null;

        try {
            this.paymentService.performRefund(refund);
            response = new PaymentResponseMessage();
        } catch (InvalidPaymentException e) {
            response = new PaymentResponseMessage(e.getMessage());
        }

        Event responseEvent = new Event("RefundFinishedMessage", new Object[] { response });
        this.queue.publish(responseEvent);
    }
}