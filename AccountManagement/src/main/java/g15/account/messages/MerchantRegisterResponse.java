package g15.account.messages;

public class MerchantRegisterResponse extends ResponseMessage {

    public MerchantRegisterResponse(String bankAccountNumber, boolean valid, String errorMessage) {
        super(bankAccountNumber, valid, errorMessage);
    }
}