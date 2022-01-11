package g15.token;

import java.awt.dnd.InvalidDnDOperationException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TokenService {
    private final int MinTokenRequestAmount = 1;
    private final int MaxTokenRequestAmount = 5;
    private final int MaxOwnedTokensWhenRequesting = 1;

    private final HashMap<String, List<Token>> tokenStore = new HashMap<>();

    private Token[] generateTokens(String customerId, int amountOfTokens) {
        Token[] tokens = new Token[amountOfTokens];
        for (int i = 0; i < amountOfTokens; i++) {
            tokens[i] = new Token();
        }

        if(!tokenStore.containsKey(customerId)){
            tokenStore.put(customerId, new LinkedList<>());
        }

        tokenStore.get(customerId).addAll(List.of(tokens));
        return tokens;
    }

    private void validateRequest(String customerId, int requestedAmountOfTokens) throws InvalidTokenRequestException {
        if(requestedAmountOfTokens < MinTokenRequestAmount || requestedAmountOfTokens > MaxTokenRequestAmount)
            throw new InvalidTokenRequestException("invalid amount of requested tokens");

        int currentAmountOfTokens;
        if(tokenStore.containsKey(customerId)){
            currentAmountOfTokens = tokenStore.get(customerId).size();
        } else{
            currentAmountOfTokens = 0;
        }

        if (currentAmountOfTokens > MaxOwnedTokensWhenRequesting)
            throw new InvalidTokenRequestException(String.format("the customer already has %d tokens", currentAmountOfTokens));
    }

    public Token[] requestTokens(String customerId, int amountOfTokens) throws InvalidTokenRequestException {
        validateRequest(customerId, amountOfTokens);
        return generateTokens(customerId, amountOfTokens);
    }
}
