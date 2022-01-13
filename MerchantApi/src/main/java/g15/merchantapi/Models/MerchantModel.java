package g15.merchantapi.Models;

public class MerchantModel {
    public String customerBankAccount;

    public MerchantModel(){

    }
    public MerchantModel(String customerBankAccount) {
        this.customerBankAccount = customerBankAccount;
    }

    public String getCustomerBankAccount() {
        return customerBankAccount;
    }
}
