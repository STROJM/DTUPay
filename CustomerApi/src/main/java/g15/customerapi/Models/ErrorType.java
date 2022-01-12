package g15.customerapi.Models;

public class ErrorType {
    private final String errorMessage;
    public ErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}