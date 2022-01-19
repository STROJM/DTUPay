package g15.payment.exceptions;

/**
 * @author Mikkel Denker (s184193)
 */
public class InvalidPaymentException extends Exception {
    public InvalidPaymentException(String message) {
        super(message);
    }
}
