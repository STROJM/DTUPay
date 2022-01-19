package messages.tokens;

import java.io.Serializable;

public class ValidatedTokensRequestMessage implements Serializable {
    private final String CustomerBankAccount;
    private final int TokensAmount;

    public ValidatedTokensRequestMessage(String customerBankAccount, int tokensAmount) {
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
