package g15.e2e;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
public class MerchantTransactionReport {
    public String token;
    public String description;
    public String errorMessage;
    public BigDecimal amount;
}
