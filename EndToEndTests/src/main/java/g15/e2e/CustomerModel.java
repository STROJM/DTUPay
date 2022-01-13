package g15.e2e;

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