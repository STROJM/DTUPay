package g15.token.repositories;

import g15.token.exceptions.InvalidTokenException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TokenRepository {

    private final HashMap<String, List<String>> tokensForCustomerStore = new HashMap<>();
    private final HashMap<String, String> customerForTokenStore = new HashMap<>();

    public void addTokens(String customerId, String[] tokens) {
        if(!tokensForCustomerStore.containsKey(customerId)){
            tokensForCustomerStore.put(customerId, new LinkedList<>());
        }

        for (var token : tokens) {
            customerForTokenStore.put(token, customerId);
        }

        tokensForCustomerStore.get(customerId).addAll(List.of(tokens));
    }

    public void clearToken(String customerToken, String cid) {
        customerForTokenStore.remove(customerToken);
        tokensForCustomerStore.get(cid).remove(customerToken);

        if (tokensForCustomerStore.get(cid).size() == 0){
            tokensForCustomerStore.remove(cid);
        }
    }

    public String getCustomerByToken(String customerToken) throws InvalidTokenException {
        if(!customerForTokenStore.containsKey(customerToken)){
            throw new InvalidTokenException("token is invalid");
        }

        return customerForTokenStore.get(customerToken);
    }

    public int getCurrentAmountOfTokens(String customerId) {
        int currentAmountOfTokens;
        if(tokensForCustomerStore.containsKey(customerId)){
            currentAmountOfTokens = tokensForCustomerStore.get(customerId).size();
        } else{
            currentAmountOfTokens = 0;
        }
        return currentAmountOfTokens;
    }
}
