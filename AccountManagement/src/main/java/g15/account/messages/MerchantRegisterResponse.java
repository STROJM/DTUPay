package g15.account.messages;

import java.io.Serializable;

public class MerchantRegisterResponse implements Serializable {
    private static final long serialVersionUID = 9023222984284806610L;

    private String bankAccountNumber;
    boolean valid;
    private String errorMessage;

    public MerchantRegisterResponse(String bankAccountNumber, boolean valid, String errorMessage) {
        this.bankAccountNumber = bankAccountNumber;
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
}