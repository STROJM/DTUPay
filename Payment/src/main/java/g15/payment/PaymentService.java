package g15.payment;

import g15.payment.adaptors.BankAdaptor;
import g15.payment.exceptions.BankException;
import g15.payment.messages.EnrichedPaymentMessage;
import g15.payment.messages.PaymentResponseMessage;
import g15.payment.messages.StoredPaymentMessage;
import g15.payment.repositories.PaymentRepository;
import messaging.Event;
import messaging.MessageQueue;

import java.util.List;

public class PaymentService {
    private BankAdaptor bankAdaptor;
    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository, BankAdaptor bankAdaptor) {
        this.bankAdaptor = bankAdaptor;
        this.paymentRepository = paymentRepository;
    }

    public PaymentResponseMessage performPayment(EnrichedPaymentMessage payment) {
        var response = new PaymentResponseMessage();

        try {
            this.bankAdaptor.performPayment(payment);
        } catch (BankException e) {
            e.printStackTrace();
        }

        this.paymentRepository.storePayment(payment);
        return response;
    }

    public List<StoredPaymentMessage> listPayments() {
        return this.paymentRepository.getPayments();
    }
}
