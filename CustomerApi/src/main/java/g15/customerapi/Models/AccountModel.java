package g15.customerapi.Models;

public class AccountModel {
    public String bankAccount;

    public AccountModel(){

    }
    public AccountModel(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAccount() {
        return bankAccount;
    }
}
