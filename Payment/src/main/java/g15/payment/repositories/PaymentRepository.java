package g15.payment.repositories;

import g15.payment.messages.EnrichedPaymentMessage;
import g15.payment.messages.StoredPaymentMessage;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    private ArrayList<StoredPaymentMessage> payments = new ArrayList();

    public final void storePayment(EnrichedPaymentMessage payment) {
        var storedPayment = StoredPaymentMessage.from(payment);
        payments.add(storedPayment);
    }

    public final List<StoredPaymentMessage> getPayments() {
        return payments;
    }
}
