package g15.account.messages;

public class CustomerRegisterMessage extends RegisterMessage {
    public CustomerRegisterMessage(String bankAccountNumber) {
        super(bankAccountNumber);
    }
}