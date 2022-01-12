package g15.token.exceptions;

public class InvalidTokenRequestException extends Exception {
    public InvalidTokenRequestException(String errorMessage){
        super(errorMessage);
    }
}
