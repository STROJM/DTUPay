package g15.payment.messages;

import java.io.Serializable;

public class PaymentResponse implements Serializable {
    @Override
    public boolean equals(Object object) {
        return object instanceof PaymentResponse;
    }
}
