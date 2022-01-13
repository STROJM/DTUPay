package g15.merchantapi.Mapper;

import g15.merchantapi.Models.PaymentModel;
import g15.merchantapi.Service.messages.PaymentMessage;

public class PaymentMapper {
    public static PaymentMessage map(PaymentModel paymentModel) {
        return new PaymentMessage(paymentModel.getMerchantId(),
                paymentModel.getCustomerToken(),
                paymentModel.getAmount(),
                paymentModel.getDescription());
    }
}
