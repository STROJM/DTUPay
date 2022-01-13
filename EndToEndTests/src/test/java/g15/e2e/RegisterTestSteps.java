package g15.e2e;

import dtu.ws.fastmoney.*;
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
    AccountInfo accountCustomer, accountMerchant;
    private final BankService testingService = new BankServiceService().getBankServicePort();
    private TypedResponseModel<String> registerCustomerRequestResponse, registerMerchantRequestResponse;
    List<String> cleanUpErrors = new ArrayList<>();

    @After
    public void cleanupAccounts() {
        if(accountCustomer != null) {
            try {
                testingService.retireAccount(accountCustomer.getAccountId());
            } catch (Exception e) {
                cleanUpErrors.add(accountCustomer.getAccountId());
            }
        }

        if(accountMerchant != null) {
            try {
                testingService.retireAccount(accountMerchant.getAccountId());
            } catch (Exception e) {
                cleanUpErrors.add(accountMerchant.getAccountId());
            }
        }

        if (!cleanUpErrors.isEmpty())
            throw new Error("Failed to cleanup test bank accounts: " + String.join(", ", cleanUpErrors));
    }

    @Given("a customer with a valid bank account number")
    public void aCustomerWithAValidBankAccountNumber() throws Exception {
        accountCustomer = registerTestUserInBank();
    }

    @When("the customer register at DTUPay")
    public void theCustomerRegisterAtDTUPay() {
        AccountModel accountModel = new AccountModel(accountCustomer.getAccountId());
        this.registerCustomerRequestResponse = service.registerCustomer(accountModel);
    }

    @Then("the customer register request is successful")
    public void theCustomerRegisterRequestIsSuccessful() {
        assertTrue(this.registerCustomerRequestResponse.completed);
        assertFalse(this.registerCustomerRequestResponse.model.isEmpty());
    }

    @Given("a merchant with a valid bank account number")
    public void aMerchantWithAValidBankAccountNumber() throws Exception{
        accountMerchant = registerTestUserInBank();
    }

    @When("the merchant register at DTUPay")
    public void theMerchantRegisterAtDTUPay() {
        AccountModel accountModel = new AccountModel(accountMerchant.getAccountId());
        this.registerMerchantRequestResponse = service.registerMerchant(accountModel);
    }

    @Then("the merchant register request is successful")
    public void theMerchantRegisterRequestIsSuccessful() {
        assertTrue(this.registerMerchantRequestResponse.completed);
        assertFalse(this.registerMerchantRequestResponse.model.isEmpty());
    }

    private AccountInfo registerTestUserInBank() throws Exception {
        User customer = new User();
        customer.setFirstName(UUID.randomUUID().toString());
        customer.setLastName(UUID.randomUUID().toString());
        customer.setCprNumber(UUID.randomUUID().toString());

        try {
            var bankId = testingService.createAccountWithBalance(customer, new BigDecimal(1000));
            AccountInfo userNew = new AccountInfo();
            userNew.setUser(customer);
            userNew.setAccountId(bankId);
            return userNew;
        } catch (Exception e) {
            throw new Exception("Could not create test bank account");
        }
    }

}
