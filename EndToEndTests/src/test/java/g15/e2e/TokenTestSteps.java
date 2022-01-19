package g15.e2e;


import dtu.ws.fastmoney.*;
import g15.e2e.Response.TypedResponseModel;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenTestSteps {
    TokenService service = new TokenService();
    private TypedResponseModel<String[]> tokenRequestResponse;
    private final BankService bankService = new BankServiceService().getBankServicePort();
    AccountInfo accountCustomer;


    @After
    public void cleanupAccounts() throws BankServiceException_Exception {
        if(accountCustomer != null) {
            bankService.retireAccount(accountCustomer.getAccountId());
        }
    }

    @When("the customer requests {int} tokens")
    public void aNewCustomerWithBankNumberRequestsTokens(int amountOfTokens) {
        this.tokenRequestResponse = service.requestTokens(new TokenModel(accountCustomer.getAccountId(), amountOfTokens));
    }

    @Then("the token request is successful")
    public void theTokenRequestIsSuccessful() {
        assertTrue(this.tokenRequestResponse.completed);
        assertTrue(this.tokenRequestResponse.model.length > 0);
    }

    @Given("a registered customer")
    public void aRegisteredCustomer() throws Exception {
        accountCustomer = registerTestUserInBank(1000);
        AccountService accountService = new AccountService();
        var registerCustomerRequestResponse = accountService.registerCustomer(new AccountModel(accountCustomer.getAccountId()));
        assertTrue(registerCustomerRequestResponse.completed);
    }

    private AccountInfo registerTestUserInBank(int balance) throws Exception {
        User customer = new User();
        customer.setFirstName(UUID.randomUUID().toString());
        customer.setLastName(UUID.randomUUID().toString());
        customer.setCprNumber(UUID.randomUUID().toString());

        try {
            var bankId = bankService.createAccountWithBalance(customer, new BigDecimal(balance));
            AccountInfo userNew = new AccountInfo();
            userNew.setUser(customer);
            userNew.setAccountId(bankId);
            return userNew;
        } catch (Exception e) {
            throw new Exception("Could not create test bank account");
        }
    }

    @Given("a customer that is not registered")
    public void aCustomerThatIsNotRegistered() throws Exception {
        accountCustomer = registerTestUserInBank(1000);
    }

    @Then("the token request fails")
    public void theTokenRequestFails() {
        assertFalse(this.tokenRequestResponse.completed);
        assertEquals(0, this.tokenRequestResponse.model.length);
    }
}
