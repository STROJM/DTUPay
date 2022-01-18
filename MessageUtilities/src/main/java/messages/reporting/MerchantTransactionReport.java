package messages.reporting;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode
public class MerchantTransactionReport {
    public String token;
    public String description;
    public String errorMessage;
    public BigDecimal amount;
    public boolean refund;

    public MerchantTransactionReport(){}
    public MerchantTransactionReport(String token, String description, String errorMessage, BigDecimal amount, boolean refund) {
        this.token = token;
        this.description = description;
        this.errorMessage = errorMessage;
        this.amount = amount;
        this.refund = refund;
    }

    public static MerchantTransactionReport from(Report report) {
        return new MerchantTransactionReport(
                report.getToken(),
                report.getDescription(),
                report.getErrorMessage(),
                report.getAmount(),
                report.isRefund()
        );
    }
}
