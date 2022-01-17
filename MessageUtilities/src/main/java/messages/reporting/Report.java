package messages.reporting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Report implements Serializable {

    private boolean valid;
    private String errorMessage;

    private String customerBankAccountNumber;
    private String merchantBankAccountNumber;

    private String token;

    private BigDecimal amount;
    private String description;


    public Report() {}

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCustomerBankAccountNumber() {
        return customerBankAccountNumber;
    }

    public void setCustomerBankAccountNumber(String customerBankAccountNumber) {
        this.customerBankAccountNumber = customerBankAccountNumber;
    }

    public String getMerchantBankAccountNumber() {
        return merchantBankAccountNumber;
    }

    public void setMerchantBankAccountNumber(String merchantBankAccountNumber) {
        this.merchantBankAccountNumber = merchantBankAccountNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return valid == report.valid && errorMessage.equals(report.errorMessage) && customerBankAccountNumber.equals(report.customerBankAccountNumber) && merchantBankAccountNumber.equals(report.merchantBankAccountNumber) && token.equals(report.token) && amount.equals(report.amount) && description.equals(report.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valid, errorMessage, customerBankAccountNumber, merchantBankAccountNumber, token, amount, description);
    }
}
