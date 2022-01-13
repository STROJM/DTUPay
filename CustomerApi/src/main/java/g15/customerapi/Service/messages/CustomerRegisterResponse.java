package g15.customerapi.Service.messages;

public class CustomerRegisterResponse extends ResponseMessage {

    public CustomerRegisterResponse(String bankAccountNumber, boolean valid, String errorMessage) {
        super(bankAccountNumber, valid, errorMessage);
    }
}