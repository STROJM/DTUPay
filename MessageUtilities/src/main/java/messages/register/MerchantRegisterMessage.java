package messages.register;

public class MerchantRegisterMessage extends RegisterMessage {
    public MerchantRegisterMessage(String bankAccountNumber) {
        super(bankAccountNumber);
    }
}