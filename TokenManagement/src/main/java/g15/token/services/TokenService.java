package g15.token.services;

import g15.token.exceptions.InvalidTokenException;
import g15.token.exceptions.InvalidTokenRequestException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TokenService {
    private final int MinTokenRequestAmount = 1;
    private final int MaxTokenRequestAmount = 5;
    private final int MaxOwnedTokensWhenRequesting = 1;

    private final HashMap<String, List<String>> tokensForCustomerStore = new HashMap<>();
    private final HashMap<String, String> customerForTokenStore = new HashMap<>();

    private String[] generateTokens(String customerId, int amountOfTokens) {
        String[] tokens = new String[amountOfTokens];
        for (int i = 0; i < amountOfTokens; i++) {
            tokens[i] = UUID.randomUUID().toString();
        }

        if(!tokensForCustomerStore.containsKey(customerId)){
            tokensForCustomerStore.put(customerId, new LinkedList<>());
        }

        for (int i = 0; i < amountOfTokens; i++) {
            customerForTokenStore.put(tokens[i],customerId);
        }

        tokensForCustomerStore.get(customerId).addAll(List.of(tokens));
        return tokens;
    }

    public String useToken(String customerToken) throws InvalidTokenException {
        if(customerToken == null){
            throw new InvalidTokenException("token is empty");
        }

        if(!customerForTokenStore.containsKey(customerToken)){
            throw new InvalidTokenException("token is invalid");
        }

        String cid = customerForTokenStore.get(customerToken);
        customerForTokenStore.remove(customerToken);
        tokensForCustomerStore.get(cid).remove(customerToken);

        if (tokensForCustomerStore.get(cid).size() == 0){
            tokensForCustomerStore.remove(cid);
        }

        return cid;
    }

    private void validateTokenGenerationRequest(String customerId, int requestedAmountOfTokens) throws InvalidTokenRequestException {
        if(requestedAmountOfTokens < MinTokenRequestAmount || requestedAmountOfTokens > MaxTokenRequestAmount)
            throw new InvalidTokenRequestException("invalid amount of requested tokens");

        int currentAmountOfTokens;
        if(tokensForCustomerStore.containsKey(customerId)){
            currentAmountOfTokens = tokensForCustomerStore.get(customerId).size();
        } else{
            currentAmountOfTokens = 0;
        }

        if (currentAmountOfTokens > MaxOwnedTokensWhenRequesting)
            throw new InvalidTokenRequestException(String.format("the customer already has %d tokens", currentAmountOfTokens));
    }

    public String[] requestTokens(String customerId, int amountOfTokens) throws InvalidTokenRequestException {
        validateTokenGenerationRequest(customerId, amountOfTokens);
        return generateTokens(customerId, amountOfTokens);
    }
}
