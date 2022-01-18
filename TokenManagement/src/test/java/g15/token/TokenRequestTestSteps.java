package g15.token;

import g15.token.services.TokenService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Roar Nind Steffensen
 */
public class TokenRequestTestSteps {
    TokenService sut = new TokenService();
    private String customerId;
    private String[] generatedTokens;
    private boolean success;
    private String errorMessage;

    @Given("a customer with id {string}")
    public void aCustomerWithId(String id) throws Exception {
        this.customerId = id;
    }

    @When("the customer requests {int} tokens")
    public void theCustomerRequestsTokens(int amountOfTokens) throws Exception {
        try {
            this.generatedTokens = sut.requestTokens(customerId, amountOfTokens);
            this.success = true;
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
            this.success = false;
        }
    }

    @Then("the {int} tokens are generated")
    public void theTokensAreGenerated(int amountOfTokens) throws Exception {
        assertEquals(amountOfTokens, this.generatedTokens.length);
    }

    @And("the customer has {int} tokens")
    public void theCustomerHasTokens(int amountOfTokens) throws Exception {
        var existingTokens = this.sut.requestTokens(customerId, amountOfTokens);
        assertEquals(amountOfTokens, existingTokens.length);
    }

    @Then("the request is denied with message {string}")
    public void theRequestIsDeniedWithMessage(String errorMessage) throws Exception {
        assertFalse(this.success);
        assertEquals(errorMessage, this.errorMessage);
    }
}
