package g15.payment.adaptors;

import g15.payment.PaymentService;
import g15.payment.exceptions.InvalidPaymentException;
import implementation.IMessagingClient;
import implementation.Message;
import messages.payment.*;
import messages.reporting.PaymentReportStoreMessage;
import messages.reporting.RefundReportStoreMessage;

public class MessageAdaptor {
    private final IMessagingClient client;
    private final PaymentService paymentService;

    public MessageAdaptor(IMessagingClient client, PaymentService paymentService){
        this.client = client;
        this.paymentService = paymentService;
        this.client.register(this::handleEnrichedPaymentEvent, EnrichedPaymentMessage.class);
        this.client.register(this::handleEnrichedRefundEvent, EnrichedRefundMessage.class);
    }

    public void handleEnrichedPaymentEvent(Message<EnrichedPaymentMessage> message) {
        var payment = message.model;
        PaymentResponseMessage response = null;

        try {
            this.paymentService.performPayment(payment);
            response = new PaymentResponseMessage();
        } catch (InvalidPaymentException e) {
            response = new PaymentResponseMessage(e.getMessage());
        }
        this.client.reply(message.update(response));
        var report = new PaymentReportStoreMessage(payment, response);
        this.client.forward(message.update(report), PaymentReportStoreMessage.class);
    }

    public void handleEnrichedRefundEvent(Message<EnrichedRefundMessage> message) {
        var refund = message.model;
        PaymentResponseMessage response = null;

        try {
            this.paymentService.performRefund(refund);
            response = new PaymentResponseMessage();
        } catch (InvalidPaymentException e) {
            response = new PaymentResponseMessage(e.getMessage());
        }
        this.client.reply(message.update(response));
        var report = new RefundReportStoreMessage(refund, response);
        this.client.forward(message.update(report), RefundReportStoreMessage.class);
    }
}
