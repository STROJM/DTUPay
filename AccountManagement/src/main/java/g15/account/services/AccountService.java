package g15.account.services;

import g15.account.UserAccount;
import g15.account.adaptors.BankAdaptor;
import g15.account.exceptions.InvalidBankAccountException;
import g15.account.repositories.AccountRepository;
import messages.register.RegisterMessage;

import java.util.List;
import java.util.UUID;

/**
 * @author Johannes Hald s202784
 * @author Søren Andersen s182881
 */

public class AccountService {
    private final AccountRepository accountRepository;
    private final BankAdaptor bankAdaptor;

    public AccountService(AccountRepository accountRepository, BankAdaptor bankAdaptor) {
        this.accountRepository = accountRepository;
        this.bankAdaptor = bankAdaptor;
    }

    public boolean isBankAccountValid(RegisterMessage account) {
        return bankAdaptor.checkIfUserHasBankAccount(account);
    }

    public void registerUserAccount(RegisterMessage account) {
        if (this.isBankAccountValid(account))
            accountRepository.saveAccount(new UserAccount(account.getBankAccountNumber(), UUID.randomUUID().toString()));
        else {
            throw new InvalidBankAccountException(account.getBankAccountNumber() + " is not a valid bank account number");
        }
    }

    public List<UserAccount> getUserAccounts() {
        return accountRepository.getAccounts();
    }

    public boolean isAccountRegistered(String bankAccountNumber) {
        return this.accountRepository.isAccountRegistered(bankAccountNumber);
    }
}
