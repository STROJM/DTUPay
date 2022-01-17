package messages.reporting;

import messages.payment.EnrichedPaymentMessage;
import messages.payment.PaymentResponseMessage;

public class PaymentReportStoreMessage extends Report {

    public PaymentReportStoreMessage(EnrichedPaymentMessage paymentRequest, PaymentResponseMessage paymentResponse) {
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
