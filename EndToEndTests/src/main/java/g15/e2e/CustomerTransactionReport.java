package g15.e2e;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode
public class CustomerTransactionReport implements Serializable {
    public String token;
    public String description;
    public String errorMessage;
    public String merchantBankAccountNumber;
    public BigDecimal amount;
}
