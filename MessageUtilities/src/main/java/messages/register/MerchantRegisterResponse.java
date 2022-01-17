package messages.register;

public class MerchantRegisterResponse extends ResponseMessage {

    public MerchantRegisterResponse(String bankAccountNumber, boolean valid, String errorMessage) {
        super(bankAccountNumber, valid, errorMessage);
    }
}