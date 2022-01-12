package g15.token.exceptions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String errorMessage){
        super(errorMessage);
    }
}
