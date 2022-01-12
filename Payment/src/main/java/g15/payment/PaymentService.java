package g15.payment;

import g15.payment.adaptors.BankAdaptor;
import g15.payment.exceptions.BankException;
import g15.payment.exceptions.InvalidPaymentException;
import g15.payment.messages.EnrichedPaymentMessage;
import g15.payment.messages.PaymentResponseMessage;
import g15.payment.messages.StoredPaymentMessage;
import g15.payment.repositories.PaymentRepository;
import messaging.Event;
import messaging.MessageQueue;

import java.math.BigDecimal;
import java.util.List;

public class PaymentService {
    private BankAdaptor bankAdaptor;
    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository, BankAdaptor bankAdaptor) {
        this.bankAdaptor = bankAdaptor;
        this.paymentRepository = paymentRepository;
    }

    public void performPayment(EnrichedPaymentMessage payment) throws InvalidPaymentException {
        if (!payment.isValid())
            throw new InvalidPaymentException("Invalid token");

        if (payment.getAmount().compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidPaymentException("Cannot transfer negative amounts");

        try {
            this.bankAdaptor.performPayment(payment);
        } catch (BankException e) {
            throw new InvalidPaymentException(e.getMessage());
        }

        this.paymentRepository.storePayment(payment);
    }

    public List<StoredPaymentMessage> listPayments() {
        return this.paymentRepository.getPayments();
    }
}
