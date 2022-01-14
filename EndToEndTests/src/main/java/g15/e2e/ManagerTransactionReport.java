package g15.e2e;

import java.math.BigDecimal;

public class ManagerTransactionReport extends Report {


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
}
