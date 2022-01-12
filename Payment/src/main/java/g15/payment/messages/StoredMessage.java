package g15.payment.messages;

import java.io.Serializable;
import java.math.BigDecimal;

public class StoredMessage implements Serializable {
    private static final long serialVersionUID = 9023222984284806610L;

    public String merchantBankAccount;
    public String token;
    public BigDecimal amount;
    public String description;
    public boolean valid;
    public String errorMessage;

    public static StoredMessage from(EnrichedMessage enrichedRefund) {
        var storedPayment = new StoredMessage();

        storedPayment.merchantBankAccount = enrichedRefund.getMerchantBankAccount();
        storedPayment.token = enrichedRefund.getToken();
        storedPayment.description = enrichedRefund.getDescription();
        storedPayment.amount = enrichedRefund.getAmount();
        storedPayment.valid = enrichedRefund.isValid();
        storedPayment.errorMessage = enrichedRefund.getErrorMessage();

        return storedPayment;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StoredMessage)) {
            return false;
        }
        var other = (StoredMessage) object;

        return merchantBankAccount.equals(other.merchantBankAccount) &&
                token.equals(other.token) &&
                amount.equals(other.amount) &&
                description.equals(other.description);
    }
}