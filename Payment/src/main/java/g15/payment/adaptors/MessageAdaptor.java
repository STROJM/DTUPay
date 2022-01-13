package g15.payment.adaptors;

import g15.payment.PaymentService;
import g15.payment.exceptions.InvalidPaymentException;
import g15.payment.messages.*;
import messaging.Event;
import messaging.MessageQueue;

public class MessageAdaptor {
    private MessageQueue queue;
    private PaymentService paymentService;

    public MessageAdaptor(MessageQueue queue, PaymentService paymentService) {
        this.queue = queue;
        this.paymentService = paymentService;
        this.queue.addHandler("EnrichedPaymentRequest", this::handleEnrichedPaymentEvent);
        this.queue.addHandler("EnrichedRefundRequest", this::handleEnrichedRefundEvent);
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

        PaymentReportStoreMessage reportMessage = new PaymentReportStoreMessage(payment, response);
        Event reportEvent = new Event("PaymentReportStoreMessage", new Object[] { reportMessage });
        this.queue.publish(reportEvent);
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

        RefundReportStoreMessage reportMessage = new RefundReportStoreMessage(refund, response);
        Event reportEvent = new Event("RefundReportStoreMessage", new Object[] { reportMessage });
        this.queue.publish(reportEvent);
    }
}
