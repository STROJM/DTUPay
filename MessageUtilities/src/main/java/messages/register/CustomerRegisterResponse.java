package messages.register;

public class CustomerRegisterResponse extends ResponseMessage {

    public CustomerRegisterResponse(String bankAccountNumber, boolean valid, String errorMessage) {
        super(bankAccountNumber, valid, errorMessage);
    }
}