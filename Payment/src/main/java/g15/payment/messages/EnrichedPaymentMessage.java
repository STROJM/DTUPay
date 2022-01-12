package g15.payment.messages;

import java.io.Serializable;
import java.math.BigDecimal;

public class EnrichedPaymentMessage implements Serializable {
    private static final long serialVersionUID = 9023222984284806610L;

    private String customerBankAccount;
    private String merchantBankAccount;
    private String token;
    private BigDecimal amount;
    private String description;
    private boolean valid;
    private String errorMessage;

    public EnrichedPaymentMessage(String customerBankAccount, String merchantBankAccount, String token, BigDecimal amount, String description, boolean valid, String errorMessage) {
        this.customerBankAccount = customerBankAccount;
        this.merchantBankAccount = merchantBankAccount;
        this.amount = amount;
        this.description = description;
        this.valid = valid;
        this.errorMessage = errorMessage;
        this.token = token;
    }

    public String getCustomerBankAccount() {
        return customerBankAccount;
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
        if (!(object instanceof EnrichedPaymentMessage)) {
            return false;
        }
        var other = (EnrichedPaymentMessage) object;

        return customerBankAccount.equals(other.getCustomerBankAccount()) &&
                merchantBankAccount.equals(other.getMerchantBankAccount()) &&
                token.equals(other.getToken()) &&
                amount.equals(other.getAmount()) &&
                description.equals(other.getDescription());
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
