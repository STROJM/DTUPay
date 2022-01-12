package g15.payment.repositories;

import g15.payment.messages.EnrichedMessage;
import g15.payment.messages.StoredMessage;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    private ArrayList<StoredMessage> payments = new ArrayList();

    public final void storePayment(EnrichedMessage payment) {
        var storedPayment = StoredMessage.from(payment);
        payments.add(storedPayment);
    }

    public final List<StoredMessage> getPayments() {
        return payments;
    }
}