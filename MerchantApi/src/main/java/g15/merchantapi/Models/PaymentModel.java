package g15.merchantapi.Models;

import java.math.BigDecimal;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
public class PaymentModel {
    public String merchantId;
    public String customerToken;
    public BigDecimal amount;
    public String description;

    public PaymentModel() {

    }

    public PaymentModel(String merchantId, String customerToken, BigDecimal amount, String description) {
        this.merchantId = merchantId;
        this.customerToken = customerToken;
        this.amount = amount;
        this.description = description;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getCustomerToken() {
        return customerToken;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

}
