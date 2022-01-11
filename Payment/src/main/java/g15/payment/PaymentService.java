package g15.payment;

import g15.payment.adaptors.Bank;
import g15.payment.exceptions.BankException;
import g15.payment.messages.EnrichedPayment;
import g15.payment.messages.PaymentResponse;
import g15.payment.repositories.PaymentRepository;
import messaging.Event;
import messaging.MessageQueue;

import java.util.List;

public class PaymentService {
    private Bank bank;
    private MessageQueue queue;
    private PaymentRepository paymentRepository;

    public PaymentService(MessageQueue queue, PaymentRepository paymentRepository, Bank bank) {
        this.bank = bank;
        this.queue = queue;
        this.paymentRepository = paymentRepository;
        this.queue.addHandler("EnrichedPaymentMessage", this::handleEnrichedPaymentEvent);
    }

    public void handleEnrichedPaymentEvent(Event event) {
        var payment = event.getArgument(0, EnrichedPayment.class);
        var response = new PaymentResponse();

        try {
            this.bank.performPayment(payment);
        } catch (BankException e) {
            e.printStackTrace();
        }

        Event responseEvent = new Event("PaymentFinishedMessage", new Object[] { response });
        this.paymentRepository.storePayment(payment);
        this.queue.publish(responseEvent);
    }

    public List<EnrichedPayment> listPayments() {
        return this.paymentRepository.getPayments();
    }
}
