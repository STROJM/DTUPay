package g15.payment.repositories;

import g15.payment.messages.EnrichedPaymentMessage;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    // TODO: we should probably create a StoredPayment class as customer bank account should not be saved
    private ArrayList<EnrichedPaymentMessage> payments = new ArrayList();

    public final void storePayment(EnrichedPaymentMessage payment) {
        payments.add(payment);
    }

    public final List<EnrichedPaymentMessage> getPayments() {
        return payments;
    }
}
