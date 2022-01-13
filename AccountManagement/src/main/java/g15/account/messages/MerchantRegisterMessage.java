package g15.account.messages;

public class MerchantRegisterMessage extends RegisterMessage {
    public MerchantRegisterMessage(String bankAccountNumber) {
        super(bankAccountNumber);
    }
}