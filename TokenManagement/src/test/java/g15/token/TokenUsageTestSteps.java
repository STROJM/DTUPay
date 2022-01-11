package g15.token;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class TokenUsageTestSteps {
    TokenService sut = new TokenService();
    private String token;
    private String customerId;
    private boolean success;
    private String errorMessage;

    @Given("an empty token")
    public void anEmptyToken() {
        this.token = null;
    }

    @Given("token {string}")
    public void token(String token) {
        this.token = token;
    }

    @When("token is used")
    public void tokenIsUsed() throws InvalidTokenRequestException {
        try {
            this.customerId = sut.useToken(token);
            this.success = true;
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
            this.success = false;
        }
    }

    @Then("the lookup fails with message {string}")
    public void theLookupFailsWithMessage(String errorMessage) {
        assertFalse(this.success);
        assertEquals(errorMessage, this.errorMessage);
    }

    @Given("a token for customerId {string}")
    public void aTokenForCustomerId(String cid1) throws InvalidTokenRequestException {
        this.token = sut.requestTokens(cid1, 1)[0];
    }

    @Then("the lookup has succeeded")
    public void theLookupHasSucceeded() {
        assertTrue(success);
    }

    @And("the customer id {string} is returned")
    public void theCustomerIdIsReturned(String cid) {
        assertEquals(cid, this.customerId);
    }
}
