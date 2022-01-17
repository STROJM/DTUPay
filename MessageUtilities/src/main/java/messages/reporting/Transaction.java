package messages.reporting;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode
public class Transaction implements Serializable {
    private BigDecimal amount;
    private String description;
    private String token;
    private String customerBankAccount;
    private String merchantBankAccount;
    private boolean completed;
    private String errorMessage;

    public Report getReport() {
        var report = new Report();
        report.setAmount(amount);
        report.setDescription(description);
        report.setToken(token);
        report.setCustomerBankAccountNumber(customerBankAccount);
        report.setMerchantBankAccountNumber(merchantBankAccount);
        report.setErrorMessage(errorMessage);
        report.setValid(completed);
        return report;
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

    public String getCustomerBankAccount() {
        return customerBankAccount;
    }

    public String getMerchantBankAccount() {
        return merchantBankAccount;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCustomerBankAccount(String customerBankAccount) {
        this.customerBankAccount = customerBankAccount;
    }

    public void setMerchantBankAccount(String merchantBankAccount) {
        this.merchantBankAccount = merchantBankAccount;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
