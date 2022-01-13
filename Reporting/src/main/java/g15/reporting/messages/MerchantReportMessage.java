package g15.reporting.messages;

import java.io.Serializable;

public class MerchantReportMessage implements Serializable {

    private String merchantBankAccount;

    public MerchantReportMessage(String merchantBankAccount) {
        this.merchantBankAccount = merchantBankAccount;
    }

    public String getMerchantBankAccount() { return this.merchantBankAccount; }
}