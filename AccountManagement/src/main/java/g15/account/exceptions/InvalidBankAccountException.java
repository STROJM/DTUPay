package g15.account.exceptions;

public class InvalidBankAccountException extends RuntimeException {
    public InvalidBankAccountException(String errorMessage){
        super(errorMessage);
    }
}