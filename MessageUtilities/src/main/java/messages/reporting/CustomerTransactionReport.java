package messages.reporting;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
public class CustomerTransactionReport implements Serializable {
    public String token;
    public String description;
    public String errorMessage;
    public String merchantBankAccountNumber;
    public BigDecimal amount;
    public boolean refund;

    public CustomerTransactionReport(){}
    public CustomerTransactionReport(String token, String description, String errorMessage, String merchantBankAccountNumber, BigDecimal amount, boolean refund) {
        this.token = token;
        this.description = description;
        this.errorMessage = errorMessage;
        this.merchantBankAccountNumber = merchantBankAccountNumber;
        this.amount = amount;
        this.refund = refund;
    }

    public static CustomerTransactionReport from(Report report) {
        return new CustomerTransactionReport(
                report.getToken(),
                report.getDescription(),
                report.getErrorMessage(),
                report.getMerchantBankAccountNumber(),
                report.getAmount(),
                report.isRefund()
        );
    }
}
