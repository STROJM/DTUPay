package g15.customerapi.Api;

import g15.customerapi.Mapper.CustomerMapper;
import g15.customerapi.Mapper.TokenMapper;
import g15.customerapi.Models.CustomerModel;
import g15.customerapi.Models.Response.TypedResponseModel;
import g15.customerapi.Models.TokenModel;
import g15.customerapi.Service.AccountService;
import g15.customerapi.messages.CustomerRegisterResponse;
import g15.customerapi.messages.TokensResponseMessage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/accounts")
public class Accounts {
    AccountService service;

    public Accounts(AccountService service) {
        this.service = service;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TypedResponseModel<String> register(CustomerModel customerModel) {
        CustomerRegisterResponse customerRegisterResponse = service.customerRegister(CustomerMapper.map(customerModel));

        var result = new TypedResponseModel<String>();
        result.completed = customerRegisterResponse.isValid();
        result.model = customerRegisterResponse.getBankAccountNumber();
        if(!result.completed) {
            result.message = customerRegisterResponse.getErrorMessage();
        }
        return result;
    }
}
