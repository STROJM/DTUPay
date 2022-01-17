package messages.reporting;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerTransactionReport implements Serializable {
    public String token;
    public String description;
    public String errorMessage;
    public String merchantBankAccountNumber;
    public BigDecimal amount;


    public CustomerTransactionReport(String token, String description, String errorMessage, String merchantBankAccountNumber, BigDecimal amount) {
        this.token = token;
        this.description = description;
        this.errorMessage = errorMessage;
        this.merchantBankAccountNumber = merchantBankAccountNumber;
        this.amount = amount;
    }
}
