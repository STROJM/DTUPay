package messages.reporting;

import java.io.Serializable;

public class CustomerReportMessage implements Serializable {

    private final String customerBankAccount;

    public CustomerReportMessage(String customerBankAccount) {
        this.customerBankAccount = customerBankAccount;
    }

    public String getCustomerBankAccount() { return this.customerBankAccount; }
}
