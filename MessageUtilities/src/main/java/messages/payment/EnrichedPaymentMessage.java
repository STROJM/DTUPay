package messages.payment;

import java.math.BigDecimal;

public class EnrichedPaymentMessage extends EnrichedMessage {
    public EnrichedPaymentMessage(String customerBankAccount, String merchantBankAccount, String token, BigDecimal amount, String description, boolean valid, String errorMessage) {
        super(customerBankAccount, merchantBankAccount, token, amount, description, valid, errorMessage);
    }
}
