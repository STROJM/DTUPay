package g15.account;

/**
 * @author Johannes Hald s202784
 * @author Søren Andersen s182881
 */

public class UserAccount {

    String bankAccountNumber;
    String userId;

    public UserAccount(String bankAccountNumber, String userId) {
        this.bankAccountNumber = bankAccountNumber;
        this.userId = userId;
    }

    public String getBankAccountNumber() {
        return this.bankAccountNumber;
    }
}
