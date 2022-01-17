package messages.register;

public class CustomerRegisterMessage extends RegisterMessage {
    public CustomerRegisterMessage(String bankAccountNumber) {
        super(bankAccountNumber);
    }
}