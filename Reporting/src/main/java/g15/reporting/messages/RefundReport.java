package g15.reporting.messages;

import g15.payment.messages.EnrichedRefundMessage;
import g15.payment.messages.PaymentResponseMessage;

public class RefundReport extends Report {

    public RefundReport(EnrichedRefundMessage refundRequest, PaymentResponseMessage paymentResponse) {
        super();

        this.setValid(paymentResponse.isValid());
        this.setErrorMessage(paymentResponse.getErrorMessage());
        this.setAmount(refundRequest.getAmount());
        this.setDescription(refundRequest.getDescription());
        this.setToken(refundRequest.getToken());
        this.setCustomerBankAccountNumber(refundRequest.getCustomerBankAccount());
        this.setMerchantBankAccountNumber(refundRequest.getMerchantBankAccount());
    }
}
