package g15.account;

public class InvalidBankAccountException extends RuntimeException {
    public InvalidBankAccountException(String errorMessage){
        super(errorMessage);
    }
}