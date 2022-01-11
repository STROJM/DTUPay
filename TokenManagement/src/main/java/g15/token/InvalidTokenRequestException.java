package g15.token;

public class InvalidTokenRequestException extends Exception {
    public InvalidTokenRequestException(String errorMessage){
        super(errorMessage);
    }
}
