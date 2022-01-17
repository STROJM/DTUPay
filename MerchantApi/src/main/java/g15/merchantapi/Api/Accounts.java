package g15.merchantapi.Api;

import g15.merchantapi.Mapper.MerchantMapper;
import g15.merchantapi.Models.AccountModel;
import g15.merchantapi.Models.Response.TypedResponseModel;
import g15.merchantapi.Service.AccountService;
import messages.register.MerchantRegisterResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/accounts")
public class Accounts {
    AccountService service;

    public Accounts(AccountService service) {
        this.service = service;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TypedResponseModel<String> register(AccountModel accountModel) {
        MerchantRegisterResponse merchantRegisterResponse = service.merchantRegister(MerchantMapper.map(accountModel));

        var result = new TypedResponseModel<String>();
        result.completed = merchantRegisterResponse.isValid();
        result.model = merchantRegisterResponse.getBankAccountNumber();
        if(!result.completed) {
            result.message = merchantRegisterResponse.getErrorMessage();
        }
        return result;
    }
}
