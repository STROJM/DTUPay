package g15.customerapi.messages;

import java.io.Serializable;
import java.util.Objects;

public class ResponseMessage implements Serializable {
    private static final long serialVersionUID = 9023222984284806610L;

    private String bankAccountNumber;
    boolean valid;
    private String errorMessage;

    public ResponseMessage(String bankAccountNumber, boolean valid, String errorMessage) {
        this.bankAccountNumber = bankAccountNumber;
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseMessage that = (ResponseMessage) o;
        return valid == that.valid && bankAccountNumber.equals(that.bankAccountNumber) && errorMessage.equals(that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankAccountNumber, valid, errorMessage);
    }
}