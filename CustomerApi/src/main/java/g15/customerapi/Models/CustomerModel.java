package g15.customerapi.Models;

public class CustomerModel {
    public String customerBankAccount;

    public CustomerModel(){

    }
    public CustomerModel(String customerBankAccount) {
        this.customerBankAccount = customerBankAccount;
    }

    public String getCustomerBankAccount() {
        return customerBankAccount;
    }
}
