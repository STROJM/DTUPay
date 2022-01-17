package g15.token.services;

import g15.token.exceptions.InvalidTokenException;
import g15.token.exceptions.InvalidTokenRequestException;
import g15.token.repositories.TokenRepository;

import java.util.UUID;

public class TokenService {
    private static final int MinTokenRequestAmount = 1;
    private static final int MaxTokenRequestAmount = 5;
    private static final int MaxOwnedTokensWhenRequesting = 1;

    private final TokenRepository repo = new TokenRepository();

    private String[] generateTokens(String customerId, int amountOfTokens) {
        String[] tokens = new String[amountOfTokens];
        for (int i = 0; i < amountOfTokens; i++) {
            tokens[i] = UUID.randomUUID().toString();
        }

        repo.addTokens(customerId, tokens);
        return tokens;
    }


    public String useToken(String customerToken) throws InvalidTokenException {
        if(customerToken == null){
            throw new InvalidTokenException("token is empty");
        }

        var cid = repo.getCustomerByToken(customerToken);
        repo.clearToken(customerToken, cid);
        return cid;
    }


    private void validateTokenGenerationRequest(String customerId, int requestedAmountOfTokens) throws InvalidTokenRequestException {
        if(requestedAmountOfTokens < MinTokenRequestAmount || requestedAmountOfTokens > MaxTokenRequestAmount)
            throw new InvalidTokenRequestException("invalid amount of requested tokens");

        int currentAmountOfTokens;
        currentAmountOfTokens = repo.getCurrentAmountOfTokens(customerId);

        if (currentAmountOfTokens > MaxOwnedTokensWhenRequesting)
            throw new InvalidTokenRequestException(String.format("the customer already has %d tokens", currentAmountOfTokens));
    }


    public String[] requestTokens(String customerId, int amountOfTokens) throws InvalidTokenRequestException {
        validateTokenGenerationRequest(customerId, amountOfTokens);
        return generateTokens(customerId, amountOfTokens);
    }
}
