package messages.reporting;

import lombok.EqualsAndHashCode;
import messages.payment.EnrichedMessage;

@EqualsAndHashCode(callSuper = true)
public class TransactionFailed extends Transaction {
    public TransactionFailed(EnrichedMessage payment, String errorMessage) {
        this.setCompleted(false);
        this.setAmount(payment.getAmount());
        this.setDescription(payment.getDescription());
        this.setToken(payment.getToken());
        this.setCustomerBankAccount(payment.getCustomerBankAccount());
        this.setMerchantBankAccount(payment.getMerchantBankAccount());
        this.setErrorMessage(errorMessage);
    }
}
