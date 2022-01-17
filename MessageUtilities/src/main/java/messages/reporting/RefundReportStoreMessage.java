package messages.reporting;

import messages.payment.EnrichedRefundMessage;
import messages.payment.PaymentResponseMessage;

public class RefundReportStoreMessage extends Report {

    public RefundReportStoreMessage(EnrichedRefundMessage refundRequest, PaymentResponseMessage paymentResponse) {
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
