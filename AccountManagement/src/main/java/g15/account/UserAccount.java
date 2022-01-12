package g15.account;

import dtu.ws.fastmoney.Account;

public class UserAccount {

    Account bankAccount;
    String userId;

    public UserAccount(Account bankAccount, String userId) {
        this.bankAccount = bankAccount;
        this.userId = userId;
    }
}
