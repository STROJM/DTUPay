package g15.payment.messages;

import java.io.Serializable;

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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PaymentResponseMessage))
            return false;

        var other = (PaymentResponseMessage) object;

        return this.valid == other.isValid() &&
                this.errorMessage.equals(other.errorMessage);
    }

    public boolean isValid() {
        return valid;
    }
}
