package messages.register;

import java.io.Serializable;
import java.util.Objects;

public class RegisterMessage implements Serializable {
    private static final long serialVersionUID = 9023222984284806610L;

    private String bankAccountNumber;

    public RegisterMessage(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterMessage that = (RegisterMessage) o;
        return bankAccountNumber.equals(that.bankAccountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankAccountNumber);
    }
}