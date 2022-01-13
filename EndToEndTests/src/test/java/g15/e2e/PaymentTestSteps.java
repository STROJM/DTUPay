package g15.e2e;

import dtu.ws.fastmoney.AccountInfo;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
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

public class PaymentTestSteps {

    AccountService service = new AccountService();
    AccountInfo accountCustomer, accountMerchant;
    private final BankService testingService = new BankServiceService().getBankServicePort();
    private TypedResponseModel<String> registerCustomerRequestResponse, registerMerchantRequestResponse;
    List<String> cleanUpErrors = new ArrayList<>();
//
//    @After
//    public void cleanupAccounts() {
////        if(accountCustomer == null){
////            return;
////        }
//        try {
//            testingService.retireAccount(accountCustomer.getAccountId());
//        } catch (Exception e) {
//            cleanUpErrors.add(accountCustomer.getAccountId());
//        }
//
//        try {
//            testingService.retireAccount(accountMerchant.getAccountId());
//        } catch (Exception e) {
//            cleanUpErrors.add(accountMerchant.getAccountId());
//        }
//
//        if (!cleanUpErrors.isEmpty())
//            throw new Error("Failed to cleanup test bank accounts: " + String.join(", ", cleanUpErrors));
//    }
//
//
//    @Given("a customer and a merchant with a valid bank account number")
//    public void aCustomerAndAMerchantWithAValidBankAccountNumber() throws Exception {
//        accountCustomer = registerTestUserInBank();
//        accountMerchant = registerTestUserInBank();
//    }
//
//
//    @And("the customer and the merchant is registered in DTUPay")
//    public void theCustomerAndTheMerchantIsRegisteredInDTUPay() {
//
//        AccountModel accountModel = new AccountModel(accountCustomer.getAccountId());
//        this.registerCustomerRequestResponse = service.registerCustomer(accountModel);
//
//        AccountModel accountModel2 = new AccountModel(accountMerchant.getAccountId());
//        this.registerMerchantRequestResponse = service.registerMerchant(accountModel2);
//    }
//
//    @When("the merchant initiates a payment for the customer of {int} kr")
//    public void theMerchantInitiatesAPaymentForTheCustomerOfKr(int arg0) {
//    }
//
//    @Then("the payment is successful")
//    public void thePaymentIsSuccessful() {
//    }
//
//    private AccountInfo registerTestUserInBank() throws Exception {
//        User customer = new User();
//        customer.setFirstName(UUID.randomUUID().toString());
//        customer.setLastName(UUID.randomUUID().toString());
//        customer.setCprNumber(UUID.randomUUID().toString());
//
//        try {
//            var bankId = testingService.createAccountWithBalance(customer, new BigDecimal(1000));
//            AccountInfo userNew = new AccountInfo();
//            userNew.setUser(customer);
//            userNew.setAccountId(bankId);
//            return userNew;
//        } catch (Exception e) {
//            throw new Exception("Could not create test bank account");
//        }
//    }

}
