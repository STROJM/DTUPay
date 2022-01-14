package g15.customerapi.Service.messages;

import java.io.Serializable;

public class CustomerReportMessage implements Serializable {

    private String customerBankAccount;

    public CustomerReportMessage(String customerBankAccount) {
        this.customerBankAccount = customerBankAccount;
    }

    public String getCustomerBankAccount() { return this.customerBankAccount; }
}
