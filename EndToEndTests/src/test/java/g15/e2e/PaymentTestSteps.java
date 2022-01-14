package g15.e2e;

import dtu.ws.fastmoney.*;
import g15.e2e.Response.TypedResponseModel;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentTestSteps {

    AccountService accountService = new AccountService();
    TokenService tokenService = new TokenService();
    PaymentService paymentService = new PaymentService();
    AccountInfo accountCustomer, accountMerchant;
    private final BankService testingService = new BankServiceService().getBankServicePort();

    private TypedResponseModel<String> paymentResponse;
    private TypedResponseModel<String[]> tokenRequestResponse;
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


    @Given("a customer and a merchant with a valid bank account number and balance {int}")
    public void aCustomerAndAMerchantWithAValidBankAccountNumber(int balance) throws Exception {
        accountCustomer = registerTestUserInBank(balance);
        accountMerchant = registerTestUserInBank(balance);
    }


    @And("the customer and the merchant is registered in DTUPay")
    public void theCustomerAndTheMerchantIsRegisteredInDTUPay() throws Exception {
        var registerCustomerRequestResponse = accountService.registerCustomer(new AccountModel(accountCustomer.getAccountId()));
        var registerMerchantRequestResponse = accountService.registerMerchant(new AccountModel(accountMerchant.getAccountId()));

        if(!registerCustomerRequestResponse.completed || !registerMerchantRequestResponse.completed){
            throw new Exception("Customer eller Merchant kunne ikke blive registeret");
        }
    }

    @And("the customer has a valid token")
    public void theCustomerHasAValidToken() throws Exception {
        this.tokenRequestResponse = tokenService.requestTokens(new TokenModel(accountCustomer.getAccountId(), 1));
        if(!this.tokenRequestResponse.completed || this.tokenRequestResponse.model.length != 1){
            throw new Exception("Kunne ikke hente token for customer id: " + accountCustomer.getAccountId());
        }
    }

    @When("the merchant initiates a payment for the customer of {int} kr")
    public void theMerchantInitiatesAPaymentForTheCustomerOfKr(int amount) {
        PaymentModel paymentModel = new PaymentModel(accountMerchant.getAccountId(), this.tokenRequestResponse.model[0], new BigDecimal(amount), "The customer pays the merchant something");
        this.paymentResponse = paymentService.pay(paymentModel);
    }

    @When("the merchant initiates a refund for the customer of {int} kr")
    public void theMerchantInitiatesARefundForTheCustomerOfKr(int amount) {
        PaymentModel paymentModel = new PaymentModel(accountMerchant.getAccountId(), this.tokenRequestResponse.model[0], new BigDecimal(amount), "The merchant refunds something to the customer");
        this.paymentResponse = paymentService.refund(paymentModel);
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(this.paymentResponse.completed);
    }

    private AccountInfo registerTestUserInBank(int balance) throws Exception {
        User customer = new User();
        customer.setFirstName(UUID.randomUUID().toString());
        customer.setLastName(UUID.randomUUID().toString());
        customer.setCprNumber(UUID.randomUUID().toString());

        try {
            var bankId = testingService.createAccountWithBalance(customer, new BigDecimal(balance));
            AccountInfo userNew = new AccountInfo();
            userNew.setUser(customer);
            userNew.setAccountId(bankId);
            return userNew;
        } catch (Exception e) {
            throw new Exception("Could not create test bank account");
        }
    }

    @And("the customer has balance {int}")
    public void theCustomerHasBalance(int balance) throws BankServiceException_Exception {
        assertEquals(new BigDecimal(balance), testingService.getAccount(accountCustomer.getAccountId()).getBalance());
    }

    @And("the merchant has balance {int}")
    public void theMerchantHasBalance(int balance) throws BankServiceException_Exception {
        assertEquals(new BigDecimal(balance), testingService.getAccount(accountMerchant.getAccountId()).getBalance());
    }
}
