package g15.customerapi.Service.messages;

public class CustomerRegisterMessage extends RegisterMessage {
    public CustomerRegisterMessage(String bankAccountNumber) {
        super(bankAccountNumber);
    }
}