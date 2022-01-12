package g15.customerapi.Api;

import g15.customerapi.Factory.TokenRequestFactory;
import g15.customerapi.Models.TypedResponseModel;
import g15.customerapi.Service.TokenService;
import g15.customerapi.messages.TokensRequestMessage;
import g15.customerapi.messages.TokensResponseMessage;
import messaging.Event;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    public TypedResponseModel<String[]> getTokens(TokensRequestMessage tokensRequestMessage) {
        TokensResponseMessage tokensResponseMessage = service.requestTokens(tokensRequestMessage);

        var result = new TypedResponseModel<String[]>();
        result.completed = tokensResponseMessage.isSuccess();
        result.model = tokensResponseMessage.getTokens();
        if(!result.completed) {
            result.message = tokensResponseMessage.getErrorMessage();
        }
        return result;
    }
}
