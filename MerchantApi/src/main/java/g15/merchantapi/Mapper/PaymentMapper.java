package g15.merchantapi.Mapper;

import g15.merchantapi.Models.PaymentModel;
import g15.merchantapi.Service.messages.PaymentMessage;
import g15.merchantapi.Service.messages.RefundMessage;

public class PaymentMapper {

    public static PaymentMessage mapPayment(PaymentModel paymentModel) {
        return new PaymentMessage(paymentModel.getMerchantId(),
                paymentModel.getCustomerToken(),
                paymentModel.getAmount(),
                paymentModel.getDescription());
    }

    public static RefundMessage mapRefund(PaymentModel paymentModel) {
        return new RefundMessage(paymentModel.getMerchantId(),
                paymentModel.getCustomerToken(),
                paymentModel.getAmount(),
                paymentModel.getDescription());
    }
}
