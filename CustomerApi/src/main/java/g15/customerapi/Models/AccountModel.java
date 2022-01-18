package g15.customerapi.Models;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
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
