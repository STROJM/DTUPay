package g15.payment.exceptions;

/**
 * @author Mikkel Denker (s184193)
 * @author Oliver Ro Møltoft Christensen s176352
 */
public class InvalidPaymentException extends Exception {
    public InvalidPaymentException(String message) {
        super(message);
    }
}
