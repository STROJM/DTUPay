package g15.account.messages;

public class CustomerRegisterResponse extends ResponseMessage {

    public CustomerRegisterResponse(String bankAccountNumber, boolean valid, String errorMessage) {
        super(bankAccountNumber, valid, errorMessage);
    }
}