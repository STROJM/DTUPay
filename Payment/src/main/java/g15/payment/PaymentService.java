package g15.payment;

import g15.payment.adaptors.BankAdaptor;
import g15.payment.exceptions.BankException;
import g15.payment.messages.EnrichedPaymentMessage;
import g15.payment.messages.PaymentResponseMessage;
import g15.payment.repositories.PaymentRepository;
import messaging.Event;
import messaging.MessageQueue;

import java.util.List;

public class PaymentService {
    private BankAdaptor bankAdaptor;
    private MessageQueue queue;
    private PaymentRepository paymentRepository;

    public PaymentService(MessageQueue queue, PaymentRepository paymentRepository, BankAdaptor bankAdaptor) {
        this.bankAdaptor = bankAdaptor;
        this.queue = queue;
        this.paymentRepository = paymentRepository;
        this.queue.addHandler("EnrichedPaymentMessage", this::handleEnrichedPaymentEvent);
    }

    public void handleEnrichedPaymentEvent(Event event) {
        var payment = event.getArgument(0, EnrichedPaymentMessage.class);
        var response = new PaymentResponseMessage();

        try {
            this.bankAdaptor.performPayment(payment);
        } catch (BankException e) {
            e.printStackTrace();
        }

        Event responseEvent = new Event("PaymentFinishedMessage", new Object[] { response });
        this.paymentRepository.storePayment(payment);
        this.queue.publish(responseEvent);
    }

    public List<EnrichedPaymentMessage> listPayments() {
        return this.paymentRepository.getPayments();
    }
}
