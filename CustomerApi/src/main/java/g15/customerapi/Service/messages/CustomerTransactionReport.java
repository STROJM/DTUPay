package g15.customerapi.Service.messages;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerTransactionReport implements Serializable {
    public String token;
    public String description;
    public String errorMessage;
    public String merchantBankAccountNumber;
    public BigDecimal amount;
}
