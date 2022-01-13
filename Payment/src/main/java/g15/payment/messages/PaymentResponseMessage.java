package g15.payment.messages;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class PaymentResponseMessage implements Serializable {
    private final boolean valid;
    private final String errorMessage;

    public PaymentResponseMessage() {
        this.valid = true;
        this.errorMessage = "";
    }

    public PaymentResponseMessage(String errorMessage) {
        this.valid = false;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return valid;
    }
}
