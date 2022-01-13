package g15.e2e;

import dtu.ws.fastmoney.AccountInfo;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import g15.e2e.Response.TypedResponseModel;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterTestSteps {
    AccountService service = new AccountService();
    AccountInfo accountUser;
    private final BankService testingService = new BankServiceService().getBankServicePort();
    private TypedResponseModel<String> registerRequestResponse;
    List<String> cleanUpErrors = new ArrayList<>();

    @After
    public void cleanupAccounts() {
        if(accountUser == null){
            return;
        }
        try {
            testingService.retireAccount(accountUser.getAccountId());
        } catch (Exception e) {
            cleanUpErrors.add(accountUser.getAccountId());
        }

        if (!cleanUpErrors.isEmpty())
            throw new Error("Failed to cleanup test bank accounts: " + String.join(", ", cleanUpErrors));
    }

    @Given("a customer with a valid bank account number")
    public void aCustomerWithAValidBankAccountNumber() throws Exception {
        registerTestUserInBank();
    }

    @When("the customer register at DTUPay")
    public void theCustomerRegisterAtDTUPay() {
        CustomerModel customerModel = new CustomerModel(accountUser.getAccountId());
        this.registerRequestResponse = service.registerCustomer(customerModel);
    }

    @Then("the register request is successful")
    public void theRegisterRequestIsSuccessful() {
        assertTrue(this.registerRequestResponse.completed);
        assertFalse(this.registerRequestResponse.model.isEmpty());
    }

    private void registerTestUserInBank() throws Exception {
        User customer = new User();
        customer.setFirstName(UUID.randomUUID().toString());
        customer.setLastName(UUID.randomUUID().toString());
        customer.setCprNumber(UUID.randomUUID().toString());

        try {
            var bankId = testingService.createAccountWithBalance(customer, new BigDecimal(1000));
            AccountInfo userNew = new AccountInfo();
            userNew.setUser(customer);
            userNew.setAccountId(bankId);
            accountUser = userNew;
        } catch (Exception e) {
            throw new Exception("Could not create test bank account");
        }
    }
}
