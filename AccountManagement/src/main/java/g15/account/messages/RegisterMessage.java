package g15.account.messages;

import java.io.Serializable;

public class RegisterMessage implements Serializable {
    private static final long serialVersionUID = 9023222984284806610L;

    private String bankAccountNumber;

    public RegisterMessage(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
}