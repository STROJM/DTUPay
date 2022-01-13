package g15.customerapi.Api;

import g15.customerapi.Factory.TokenRequestFactory;
import g15.customerapi.Mapper.TokenMapper;
import g15.customerapi.Models.Response.TypedResponseModel;
import g15.customerapi.Models.TokenModel;
import g15.customerapi.Service.TokenService;
import g15.customerapi.messages.TokensRequestMessage;
import g15.customerapi.messages.TokensResponseMessage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Consumes({"application/json"})
@Path("/tokens")
public class Tokens {
    TokenService service;

    public Tokens(){
        service = new TokenRequestFactory().getService();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TypedResponseModel<String[]> getTokens(TokenModel tokenModel) {
        TokensResponseMessage tokensResponseMessage = service.requestTokens(TokenMapper.map(tokenModel));

        var result = new TypedResponseModel<String[]>();
        result.completed = tokensResponseMessage.isSuccess();
        result.model = tokensResponseMessage.getTokens();
        if(!result.completed) {
            result.message = tokensResponseMessage.getErrorMessage();
        }
        return result;
    }
}
