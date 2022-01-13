package g15.customerapi.messages;

public class CustomerRegisterMessage extends RegisterMessage {
    public CustomerRegisterMessage(String bankAccountNumber) {
        super(bankAccountNumber);
    }
}