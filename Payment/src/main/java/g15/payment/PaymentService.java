package g15.payment;

import g15.payment.adaptors.BankAdaptor;
import g15.payment.exceptions.BankException;
import g15.payment.exceptions.InvalidPaymentException;
import messages.payment.*;
import g15.payment.repositories.PaymentRepository;

import java.math.BigDecimal;
import java.util.List;

public class PaymentService {
    private BankAdaptor bankAdaptor;
    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository, BankAdaptor bankAdaptor) {
        this.bankAdaptor = bankAdaptor;
        this.paymentRepository = paymentRepository;
    }

    private void checkPayment(EnrichedMessage payment) throws InvalidPaymentException {
        if (!payment.isValid())
            throw new InvalidPaymentException("Invalid token");

        if (payment.getAmount().compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidPaymentException("Cannot transfer negative amounts");
    }

    public void performPayment(EnrichedPaymentMessage payment) throws InvalidPaymentException {
        this.checkPayment(payment);

        try {
            this.bankAdaptor.performPayment(payment);
        } catch (BankException e) {
            throw new InvalidPaymentException(e.getMessage());
        }

        this.paymentRepository.storePayment(payment);
    }

    public void performRefund(EnrichedRefundMessage refund) throws InvalidPaymentException {
        this.checkPayment(refund);

        try {
            this.bankAdaptor.performRefund(refund);
        } catch (BankException e) {
            throw new InvalidPaymentException(e.getMessage());
        }

        this.paymentRepository.storePayment(refund);
    }

    public List<StoredMessage> listPayments() {
        return this.paymentRepository.getPayments();
    }
}
