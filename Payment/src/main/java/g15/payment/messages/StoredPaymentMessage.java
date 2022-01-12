package g15.payment.messages;

import java.io.Serializable;
import java.math.BigDecimal;

public class StoredPaymentMessage implements Serializable {
    private static final long serialVersionUID = 9023222984284806610L;

    public String merchantBankAccount;
    public String token;
    public BigDecimal amount;
    public String description;
    public boolean valid;
    public String errorMessage;

    public static StoredPaymentMessage from(EnrichedPaymentMessage enrichedPayment) {
        var storedPayment = new StoredPaymentMessage();

        storedPayment.merchantBankAccount = enrichedPayment.getMerchantBankAccount();
        storedPayment.token = enrichedPayment.getToken();
        storedPayment.description = enrichedPayment.getDescription();
        storedPayment.amount = enrichedPayment.getAmount();
        storedPayment.valid = enrichedPayment.isValid();
        storedPayment.errorMessage = enrichedPayment.getErrorMessage();

        return storedPayment;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StoredPaymentMessage)) {
            return false;
        }
        var other = (StoredPaymentMessage) object;

        return merchantBankAccount.equals(other.merchantBankAccount) &&
                token.equals(other.token) &&
                amount.equals(other.amount) &&
                description.equals(other.description);
    }
}
