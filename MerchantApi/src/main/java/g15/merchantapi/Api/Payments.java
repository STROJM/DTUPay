package g15.merchantapi.Api;

import g15.merchantapi.Mapper.MerchantMapper;
import g15.merchantapi.Mapper.PaymentMapper;
import g15.merchantapi.Models.AccountModel;
import g15.merchantapi.Models.PaymentModel;
import g15.merchantapi.Models.Response.TypedResponseModel;
import g15.merchantapi.Service.TokenService;
import g15.merchantapi.Service.messages.MerchantRegisterResponse;
import g15.merchantapi.Service.messages.PaymentResponseMessage;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/payments")
public class Payments {
    TokenService service;

    public Payments(TokenService service) {
        this.service = service;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/pay")
    public TypedResponseModel<String> pay(PaymentModel paymentModel) {
        PaymentResponseMessage paymentResponseMessage = service.pay(PaymentMapper.map(paymentModel));

        var result = new TypedResponseModel<String>();
        result.completed = paymentResponseMessage.isValid();
        result.model = "";
        if(!result.completed) {
            result.message = paymentResponseMessage.getErrorMessage();
        }
        return result;
    }
}
