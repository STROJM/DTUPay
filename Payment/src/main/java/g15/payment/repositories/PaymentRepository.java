package g15.payment.repositories;

import g15.payment.messages.EnrichedPayment;
import g15.payment.messages.PaymentResponse;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    // TODO: we should probably create a StoredPayment class as customer bank account should not be saved
    private ArrayList<EnrichedPayment> payments = new ArrayList();

    public final void storePayment(EnrichedPayment payment) {
        payments.add(payment);
    }

    public final List<EnrichedPayment> getPayments() {
        return payments;
    }
}
