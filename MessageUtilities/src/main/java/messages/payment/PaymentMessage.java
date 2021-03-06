package messages.payment;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode
public class PaymentMessage implements Serializable {
    private static final long serialVersionUID = 9023222984284806610L;

    private String merchantBankAccount;
    private String token;
    private BigDecimal amount;
    private String description;

    public PaymentMessage(String merchantBankAccount, String token, BigDecimal amount, String description) {
        this.merchantBankAccount = merchantBankAccount;
        this.token = token;
        this.amount = amount;
        this.description = description;
    }

    public String getMerchantBankAccount() {
        return merchantBankAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getToken() {
        return token;
    }


    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PaymentMessage)) {
            return false;
        }
        var other = (PaymentMessage) object;

        return merchantBankAccount.equals(other.getMerchantBankAccount()) &&
                token.equals(other.getToken()) &&
                amount.equals(other.getAmount()) &&
                description.equals(other.getDescription());
    }
}
