package g15.merchantapi.Models.Response;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
public class ErrorType {
    private final String errorMessage;

    public ErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}