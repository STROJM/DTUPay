package messages.reporting;

import java.math.BigDecimal;

public class ManagerTransactionReport extends Report {

    public ManagerTransactionReport() {}

    public ManagerTransactionReport(boolean valid, String errorMessage, String customerBankAccountNumber, String merchantBankAccountNumber,
                                    String token, BigDecimal amount, String description)
    {
        super();
        this.setValid(valid);
        this.setErrorMessage(errorMessage);
        this.setCustomerBankAccountNumber(customerBankAccountNumber);
        this.setMerchantBankAccountNumber(merchantBankAccountNumber);
        this.setToken(token);
        this.setAmount(amount);
        this.setDescription(description);
    }

    public static ManagerTransactionReport from(Report report) {
        return new ManagerTransactionReport(
                report.isValid(),
                report.getErrorMessage(),
                report.getCustomerBankAccountNumber(),
                report.getMerchantBankAccountNumber(),
                report.getToken(),
                report.getAmount(),
                report.getDescription()
        );
    }
}
