package messages.reporting;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode
public class MerchantTransactionReport {
    public String token;
    public String description;
    public String errorMessage;
    public BigDecimal amount;

    public MerchantTransactionReport(){}
    public MerchantTransactionReport(String token, String description, String errorMessage, BigDecimal amount) {
        this.token = token;
        this.description = description;
        this.errorMessage = errorMessage;
        this.amount = amount;
    }

    public static MerchantTransactionReport from(Report report) {
        return new MerchantTransactionReport(
                report.getToken(),
                report.getDescription(),
                report.getErrorMessage(),
                report.getAmount()
        );
    }
}
