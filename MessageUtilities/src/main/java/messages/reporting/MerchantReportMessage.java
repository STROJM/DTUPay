package messages.reporting;

import java.io.Serializable;

public class MerchantReportMessage implements Serializable {

    private String merchantBankAccount;

    public MerchantReportMessage(String merchantBankAccount) {
        this.merchantBankAccount = merchantBankAccount;
    }

    public String getMerchantBankAccount() { return this.merchantBankAccount; }
}
