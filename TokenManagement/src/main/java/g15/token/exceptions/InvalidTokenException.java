package g15.token.exceptions;

/**
 * @author Roar Nind Steffensen
 */
public class InvalidTokenException extends Exception {
    public InvalidTokenException(String errorMessage){
        super(errorMessage);
    }
}
