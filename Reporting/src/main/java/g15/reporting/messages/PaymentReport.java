package g15.reporting.messages;

import g15.payment.messages.EnrichedPaymentMessage;
import g15.payment.messages.PaymentResponseMessage;

public class PaymentReport extends Report {

    public PaymentReport(EnrichedPaymentMessage paymentRequest, PaymentResponseMessage paymentResponse) {
        super();

        this.setValid(paymentResponse.isValid());
        this.setErrorMessage(paymentResponse.getErrorMessage());
        this.setAmount(paymentRequest.getAmount());
        this.setDescription(paymentRequest.getDescription());
        this.setToken(paymentRequest.getToken());
        this.setCustomerBankAccountNumber(paymentRequest.getCustomerBankAccount());
        this.setMerchantBankAccountNumber(paymentRequest.getMerchantBankAccount());
    }
}
