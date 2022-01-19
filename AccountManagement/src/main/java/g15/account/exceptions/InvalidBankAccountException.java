package g15.account.exceptions;

/**
 * @author Johannes Hald s202784
 */

public class InvalidBankAccountException extends RuntimeException {
    public InvalidBankAccountException(String errorMessage){
        super(errorMessage);
    }
}