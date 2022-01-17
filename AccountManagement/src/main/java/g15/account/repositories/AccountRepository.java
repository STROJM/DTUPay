package g15.account.repositories;

import g15.account.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private ArrayList<UserAccount> registeredAccounts = new ArrayList<>();

    public final void saveAccount(UserAccount account) {
        registeredAccounts.add(account);
    }

    public final List<UserAccount> getAccounts() {
        return registeredAccounts;
    }
}
