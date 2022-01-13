package g15.account.services;

import g15.account.UserAccount;
import g15.account.adaptors.BankAdaptor;
import g15.account.exceptions.InvalidBankAccountException;
import g15.account.messages.RegisterMessage;
import g15.account.repositories.AccountRepository;

import java.util.List;
import java.util.UUID;

public class AccountService {
    private AccountRepository accountRepository;
    private BankAdaptor bankAdaptor;

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
}
