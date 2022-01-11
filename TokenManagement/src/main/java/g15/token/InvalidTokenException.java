package g15.token;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String errorMessage){
        super(errorMessage);
    }
}
