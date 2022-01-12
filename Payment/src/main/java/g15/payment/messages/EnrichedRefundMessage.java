package g15.payment.messages;

import java.math.BigDecimal;

public class EnrichedRefundMessage extends EnrichedMessage {
    public EnrichedRefundMessage(String customerBankAccount, String merchantBankAccount, String token, BigDecimal amount, String description, boolean valid, String errorMessage) {
        super(customerBankAccount, merchantBankAccount, token, amount, description, valid, errorMessage);
    }
}
