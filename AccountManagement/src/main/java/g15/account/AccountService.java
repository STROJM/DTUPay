package g15.account;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountService {

    private final BankService service = new BankServiceService().getBankServicePort();
    private List<UserAccount> accountsRegistered = new ArrayList<>();

    public boolean isBankAccountValid(String bankAccountNumber) {
        try {
            return !service.getAccount(bankAccountNumber).getId().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void registerUserAccount(String bankAccountNumber) {
        try {
            Account bankAccount = service.getAccount(bankAccountNumber);
            accountsRegistered.add(new UserAccount(bankAccount, UUID.randomUUID().toString()));
        } catch (Exception e) {
            throw new InvalidBankAccountException(e.getMessage());
        }
    }

    public List<UserAccount> getUserAccounts() {
        return accountsRegistered;
    }
}
