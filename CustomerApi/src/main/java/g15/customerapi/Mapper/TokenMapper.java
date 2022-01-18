package g15.customerapi.Mapper;

import g15.customerapi.Models.TokenModel;
import messages.tokens.TokensRequestMessage;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
public class TokenMapper {
    public static TokensRequestMessage map(TokenModel tokenModel){
        return new TokensRequestMessage(tokenModel.getCustomerBankAccount(), tokenModel.getTokensAmount());
    }
}