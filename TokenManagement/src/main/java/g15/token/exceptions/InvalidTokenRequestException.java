package g15.token.exceptions;

/**
 * @author Roar Nind Steffensen
 */
public class InvalidTokenRequestException extends Exception {
    public InvalidTokenRequestException(String errorMessage){
        super(errorMessage);
    }
}
