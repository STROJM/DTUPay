package g15.customerapi.Mapper;

import g15.customerapi.Models.TokenModel;
import g15.customerapi.Service.messages.TokensRequestMessage;

public class TokenMapper {
    public static TokensRequestMessage map(TokenModel tokenModel){
        return new TokensRequestMessage(tokenModel.getCustomerBankAccount(), tokenModel.getTokensAmount());
    }
}