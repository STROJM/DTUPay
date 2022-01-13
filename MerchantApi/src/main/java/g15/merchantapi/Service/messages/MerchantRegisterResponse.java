package g15.merchantapi.Service.messages;

public class MerchantRegisterResponse extends ResponseMessage {

    public MerchantRegisterResponse(String bankAccountNumber, boolean valid, String errorMessage) {
        super(bankAccountNumber, valid, errorMessage);
    }
}