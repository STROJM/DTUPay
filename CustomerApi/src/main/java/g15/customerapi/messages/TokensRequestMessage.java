package g15.customerapi.messages;

import java.io.Serializable;

public class TokensRequestMessage implements Serializable {
    private static final long serialVersionUID = 9023222984284806610L;

    private final String CustomerBankAccount;
    private final int TokensAmount;

    public TokensRequestMessage(String customerBankAccount, int tokensAmount) {
        CustomerBankAccount = customerBankAccount;
        TokensAmount = tokensAmount;
    }

    public String getCustomerBankAccount() {
        return CustomerBankAccount;
    }

    public int getTokensAmount() {
        return TokensAmount;
    }
}
