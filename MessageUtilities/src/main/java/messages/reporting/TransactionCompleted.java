package messages.reporting;

import lombok.EqualsAndHashCode;
import messages.payment.EnrichedMessage;

@EqualsAndHashCode(callSuper = true)
public class TransactionCompleted extends Transaction {
    public TransactionCompleted(EnrichedMessage payment) {
        this(payment, false);
    }

    public TransactionCompleted(EnrichedMessage payment, boolean refund) {
        this.setCompleted(true);
        this.setRefund(refund);
        this.setAmount(payment.getAmount());
        this.setDescription(payment.getDescription());
        this.setToken(payment.getToken());
        this.setCustomerBankAccount(payment.getCustomerBankAccount());
        this.setMerchantBankAccount(payment.getMerchantBankAccount());
    }
}
