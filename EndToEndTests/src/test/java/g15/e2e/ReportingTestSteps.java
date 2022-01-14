package g15.e2e;

import dtu.ws.fastmoney.AccountInfo;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReportingTestSteps {
    AccountInfo accountCustomer, accountMerchant;
    private final BankService bankService = new BankServiceService().getBankServicePort();
    ReportingService reportingService = new ReportingService();
    List<CustomerTransactionReport> customerPayments;
    List<MerchantTransactionReport> merchantPayments;
    List<ManagerTransactionReport> managerPayments;
    String token;

    @After
    public void cleanupAccounts() {
        List<String> cleanUpErrors = new ArrayList<>();

        if(accountCustomer != null) {
            try {
                bankService.retireAccount(accountCustomer.getAccountId());
            } catch (Exception e) {
                cleanUpErrors.add(accountCustomer.getAccountId());
            }
        }

        if(accountMerchant != null) {
            try {
                bankService.retireAccount(accountMerchant.getAccountId());
            } catch (Exception e) {
                cleanUpErrors.add(accountMerchant.getAccountId());
            }
        }

        if (!cleanUpErrors.isEmpty())
            throw new Error("Failed to cleanup test bank accounts: " + String.join(", ", cleanUpErrors));
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

    @Given("a merchant and customer with a valid bank account number")
    public void aMerchantAndCustomerWithAValidBankAccountNumber() throws Exception {
        accountCustomer = registerTestUserInBank(1000);
        accountMerchant = registerTestUserInBank(1000);
    }

    @And("the merchant and customer is registered in DTUPay")
    public void theMerchantAndCustomerIsRegisteredInDTUPay() throws Exception {
        AccountService accountService = new AccountService();
        var registerCustomerRequestResponse = accountService.registerCustomer(new AccountModel(accountCustomer.getAccountId()));
        var registerMerchantRequestResponse = accountService.registerMerchant(new AccountModel(accountMerchant.getAccountId()));

        if(!registerCustomerRequestResponse.completed || !registerMerchantRequestResponse.completed){
            throw new Exception("Customer or merchant could not be registered in DTU pay");
        }
    }

    @And("the customer has a previous payment to the merchant of {int} kr")
    public void theCustomerHasAPreviousPaymentToTheMerchantOfKr(int amount) throws Exception {
        PaymentService paymentService = new PaymentService();
        TokenService tokenService = new TokenService();

        var tokenRequestResponse = tokenService.requestTokens(new TokenModel(accountCustomer.getAccountId(), 1));
        if(!tokenRequestResponse.completed || tokenRequestResponse.model.length != 1){
            throw new Exception("Could not get tokens for customer id: " + accountCustomer.getAccountId());
        }

        this.token = tokenRequestResponse.model[0];
        PaymentModel paymentModel = new PaymentModel(accountMerchant.getAccountId(), this.token, new BigDecimal(amount), "The customer pays the merchant something");
        paymentService.pay(paymentModel);
    }

    @When("the customer requests a list of previous payments")
    public void theCustomerRequestsAListOfPreviousPayments() {
        customerPayments = reportingService.getCustomerPayments(accountCustomer.getAccountId()).model;
    }

    @Then("the list contains a payment to the merchant from the customer for {int} kr")
    public void theListContainsAPaymentToTheMerchantForKr(int amount) {
        CustomerTransactionReport expected = new CustomerTransactionReport();

        expected.token = this.token;
        expected.description = "The customer pays the merchant something";
        expected.errorMessage = "";
        expected.merchantBankAccountNumber = accountMerchant.getAccountId();
        expected.amount = new BigDecimal(amount);

        Assert.assertTrue(customerPayments.contains(expected));
    }

    @Then("the customer report list is empty")
    public void theReportListIsEmpty() {
        Assert.assertTrue(customerPayments.isEmpty());
    }

    @When("the merchant requests a list of previous payments")
    public void theMerchantRequestsAListOfPreviousPayments() {
        merchantPayments = reportingService.getMerchantPayments(accountMerchant.getAccountId()).model;
    }

    @When("the manager requests a list of previous payments")
    public void theManagerRequestsAListOfPreviousPayments() {
        managerPayments = reportingService.getManagerPayments().model;
    }

    @Then("the merchant list contains a payment to the merchant for {int} kr")
    public void theMerchantListContainsAPaymentToTheMerchantForKr(int amount) {
        MerchantTransactionReport expected = new MerchantTransactionReport();

        expected.token = this.token;
        expected.description = "The customer pays the merchant something";
        expected.errorMessage = "";
        expected.amount = new BigDecimal(amount);

        Assert.assertTrue(merchantPayments.contains(expected));
    }

    @Then("the merchant report list is empty")
    public void theMerchantReportListIsEmpty() {
        Assert.assertTrue(merchantPayments.isEmpty());
    }

    @Then("the manager list contains a payment to the merchant from the customer for {int} kr")
    public void theManagerListContainsAPaymentToTheMerchantFromTheCustomerForKr(int amount) {
        ManagerTransactionReport expected = new ManagerTransactionReport(
                true,
                "",
                accountCustomer.getAccountId(),
                accountMerchant.getAccountId(),
                this.token,
                new BigDecimal(amount),
                "The customer pays the merchant something");

        Assert.assertTrue(managerPayments.contains(expected));
    }

    @Then("the manager report list is empty")
    public void theManagerReportListIsEmpty() {
        Assert.assertTrue(managerPayments.isEmpty());
    }
}
