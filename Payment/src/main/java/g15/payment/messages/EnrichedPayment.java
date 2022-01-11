package g15.payment.messages;

import java.io.Serializable;
import java.math.BigDecimal;

public class EnrichedPayment implements Serializable {
    private static final long serialVersionUID = 9023222984284806610L;

    private String customerBankAccount;
    private String merchantBankAccount;
    private BigDecimal amount;
    private String description;
    private boolean valid;
    private String errorMessage;

    public EnrichedPayment(String customerBankAccount, String merchantBankAccount, BigDecimal amount, String description, boolean valid, String errorMessage) {
        this.customerBankAccount = customerBankAccount;
        this.merchantBankAccount = merchantBankAccount;
        this.amount = amount;
        this.description = description;
        this.valid = valid;
        this.errorMessage = errorMessage;
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


    @Override
    public boolean equals(Object object) {
        if (!(object instanceof EnrichedPayment)) {
            return false;
        }
        var other = (EnrichedPayment) object;

        return customerBankAccount.equals(other.getCustomerBankAccount()) &&
                merchantBankAccount.equals(other.getMerchantBankAccount()) &&
                amount.equals(other.getAmount()) &&
                description.equals(other.getDescription());
    }
}
