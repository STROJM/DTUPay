package g15.customerapi.Models;

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
