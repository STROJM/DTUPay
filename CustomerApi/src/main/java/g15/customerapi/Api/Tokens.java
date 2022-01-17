package g15.customerapi.Api;

import g15.customerapi.Mapper.TokenMapper;
import g15.customerapi.Models.Response.TypedResponseModel;
import g15.customerapi.Models.TokenModel;
import g15.customerapi.Service.TokenService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes({"application/json"})
@Path("/tokens")
public class Tokens {
    TokenService service;

    public Tokens(TokenService service){
        this.service = service;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TypedResponseModel<String[]> getTokens(TokenModel tokenModel) {
        var tokensResponseMessage = service.requestTokens(TokenMapper.map(tokenModel));

        var result = new TypedResponseModel<String[]>();
        result.completed = tokensResponseMessage.isSuccess();
        result.model = tokensResponseMessage.getTokens();
        if(!result.completed) {
            result.message = tokensResponseMessage.getErrorMessage();
        }
        return result;
    }
}
