package g15.e2e;

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