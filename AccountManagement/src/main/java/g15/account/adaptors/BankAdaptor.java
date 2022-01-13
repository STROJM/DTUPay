package g15.account.adaptors;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import g15.account.messages.RegisterMessage;

public class BankAdaptor {
    private final BankService bankService = new BankServiceService().getBankServicePort();

    public boolean checkIfUserHasBankAccount(RegisterMessage accountRegistry) {
        try {
            return !bankService.getAccount(accountRegistry.getBankAccountNumber()).getId().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
