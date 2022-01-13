package g15.e2e;

public class TokenModel {
    public String customerBankAccount;
    public int tokensAmount;

    public TokenModel(){

    }
    public TokenModel(String customerBankAccount, int tokensAmount) {
        this.customerBankAccount = customerBankAccount;
        this.tokensAmount = tokensAmount;
    }

    public String getCustomerBankAccount() {
        return customerBankAccount;
    }
    public int getTokensAmount() {
        return tokensAmount;
    }
}
