package g15.payment.messages;

import java.io.Serializable;

public class PaymentResponseMessage implements Serializable {
    @Override
    public boolean equals(Object object) {
        return object instanceof PaymentResponseMessage;
    }
}
