package g15.account.repositories;

import g15.account.UserAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Johannes Hald s202784
 * @author Søren Andersen s182881
 */

public class AccountRepository {
    private ArrayList<UserAccount> registeredAccounts = new ArrayList<>();

    public final void saveAccount(UserAccount account) {
        registeredAccounts.add(account);
    }

    public final List<UserAccount> getAccounts() {
        return registeredAccounts;
    }

    public boolean isAccountRegistered(String bankAccountNumber) {
        return registeredAccounts.stream()
                .filter(userAccount -> userAccount.getBankAccountNumber().equals(bankAccountNumber))
                .findAny()
                .orElse(null) != null;
    }
}
