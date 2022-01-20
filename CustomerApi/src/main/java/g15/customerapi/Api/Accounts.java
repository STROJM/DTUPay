package g15.customerapi.Api;

import g15.customerapi.Mapper.CustomerMapper;
import g15.customerapi.Models.AccountModel;
import g15.customerapi.Models.Response.TypedResponseModel;
import g15.customerapi.Adaptors.AccountAdaptor;
import messages.register.CustomerRegisterResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
@Path("/accounts")
public class Accounts {
    AccountAdaptor service;

    public Accounts(AccountAdaptor service) {
        this.service = service;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TypedResponseModel<String> register(AccountModel accountModel) {
        CustomerRegisterResponse customerRegisterResponse = service.customerRegister(CustomerMapper.map(accountModel));

        var result = new TypedResponseModel<String>();
        result.completed = customerRegisterResponse.isValid();
        result.model = customerRegisterResponse.getBankAccountNumber();
        if(!result.completed) {
            result.message = customerRegisterResponse.getErrorMessage();
        }
        return result;
    }
}
