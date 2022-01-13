package g15.e2e;


import g15.e2e.Response.TypedResponseModel;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenTestSteps {
    TokenService service = new TokenService();
    private TypedResponseModel<String[]> tokenRequestResponse;


    @When("a new customer with bank number {string} requests {int} tokens")
    public void aNewCustomerWithBankNumberRequestsTokens(String bankAccountNumber, int amountOfTokens) {
        this.tokenRequestResponse = service.requestTokens(new TokenModel(bankAccountNumber, amountOfTokens));
    }

    @Then("the token request is successful")
    public void theTokenRequestIsSuccessful() {
        assertTrue(this.tokenRequestResponse.completed);
        assertTrue(this.tokenRequestResponse.model.length > 0);
    }
}
