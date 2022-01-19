package g15.payment;

import g15.payment.adaptors.BankAdaptor;
import messages.reporting.Transaction;
import messages.reporting.TransactionCompleted;
import messages.reporting.TransactionFailed;
import g15.payment.exceptions.BankException;
import g15.payment.exceptions.InvalidPaymentException;
import g15.payment.repositories.EventStore;
import messages.payment.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Mikkel Denker (s184193)
 */
public class PaymentService {
    private final EventStore store;
    private final BankAdaptor bankAdaptor;

    public PaymentService(EventStore store, BankAdaptor bankAdaptor) {
        this.store = store;
        this.bankAdaptor = bankAdaptor;
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
            this.store.add(new TransactionCompleted(payment));
        } catch (BankException e) {
            this.store.add(new TransactionFailed(payment, e.getMessage()));
            throw new InvalidPaymentException(e.getMessage());
        }
    }

    public void performRefund(EnrichedRefundMessage refund) throws InvalidPaymentException {
        this.checkPayment(refund);

        try {
            this.bankAdaptor.performRefund(refund);
            this.store.add(new TransactionCompleted(refund, true));
        } catch (BankException e) {
            this.store.add(new TransactionFailed(refund, true, e.getMessage()));
            throw new InvalidPaymentException(e.getMessage());
        }
    }

    public List<Transaction> listPayments() {
        return this.store.get();
    }
}
