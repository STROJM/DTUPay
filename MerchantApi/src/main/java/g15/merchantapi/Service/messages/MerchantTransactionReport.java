package g15.merchantapi.Service.messages;

import java.math.BigDecimal;

public class MerchantTransactionReport {
    public String token;
    public String description;
    public String errorMessage;
    public BigDecimal amount;

    public MerchantTransactionReport(String token, String description, String errorMessage, BigDecimal amount) {
        this.token = token;
        this.description = description;
        this.errorMessage = errorMessage;
        this.amount = amount;
    }
}
