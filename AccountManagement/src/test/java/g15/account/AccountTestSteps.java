package g15.account;

import dtu.ws.fastmoney.*;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertThrows;

public class AccountTestSteps {
    List<AccountInfo> testAccountsRegistered = new ArrayList<>();
    private final BankService testingService = new BankServiceService().getBankServicePort();

    AccountService accountService = new AccountService();
    String latestBankAccountNumber = "";

    boolean expectedSuccess = false;

    @After
    public void cleanupAccounts() {
        for (AccountInfo acc : testAccountsRegistered) {
            try {
                testingService.retireAccount(acc.getAccountId());
            } catch (Exception e) {
                // We don't care if the account doesn't exist
            }
        }
    }

    private void registerTestUserInBank() {
        User customer = new User();
        customer.setFirstName(UUID.randomUUID().toString());
        customer.setLastName(UUID.randomUUID().toString());
        customer.setCprNumber("test-cpr-number");

        try {
            var bankId = testingService.createAccountWithBalance(customer, new BigDecimal(1000));
            AccountInfo userNew = new AccountInfo();
            userNew.setUser(customer);
            userNew.setAccountId(bankId);
            this.testAccountsRegistered.add(userNew);
            latestBankAccountNumber = bankId;
        } catch (Exception e) {
            throw new InvalidBankAccountException("Could not create test bank account");
        }
    }

    @When("the user registers in dtu pay")
    public void theUserRegistersInDtuPay() {
        try {
            accountService.registerUserAccount(latestBankAccountNumber);
        } catch (Exception e) {
            if (expectedSuccess)
                throw e;
        }
    }

    @Then("the user successfully registers")
    public void theUserSuccessfullyRegisters() {
        Assert.assertTrue(accountService.getUserAccounts()
                        .stream()
                        .anyMatch(acc -> acc.bankAccount.getId().equals(latestBankAccountNumber)));
    }

    @Then("the user fails to register")
    public void theUserFailsToRegister() {
        Throwable exception = assertThrows(InvalidBankAccountException.class, () -> {
            accountService.registerUserAccount(latestBankAccountNumber);
        });
        // TODO: Check which error message is coming.
        Assert.assertFalse(exception.getMessage().isEmpty());
        Assert.assertTrue(
                accountService.getUserAccounts()
                .stream()
                .filter(acc -> acc.bankAccount.getId().equals(latestBankAccountNumber)).findAny().isEmpty());
    }

    @Given("a customer with a valid bank account number {string}")
    public void aCustomerWithAValidBankAccountNumber(String arg0) {
        if (accountService.isBankAccountValid(arg0)) {
            latestBankAccountNumber = arg0;
        } else {
            registerTestUserInBank();
            Assert.assertTrue(accountService.isBankAccountValid(latestBankAccountNumber));
        }

        expectedSuccess = true;
    }

    @Given("a merchant with a valid bank account number {string}")
    public void aMerchantWithAValidBankAccountNumber(String arg0) {
        if (accountService.isBankAccountValid(arg0)) {
            latestBankAccountNumber = arg0;
        } else {
            registerTestUserInBank();
            Assert.assertTrue(accountService.isBankAccountValid(latestBankAccountNumber));
        }

        expectedSuccess = true;
    }

    @Given("a customer with a invalid bank account number {string}")
    public void aCustomerWithAInvalidBankAccountNumber(String arg0) {
        latestBankAccountNumber = arg0;
        Assert.assertFalse(accountService.isBankAccountValid(latestBankAccountNumber));
    }

    @Given("a merchant with a invalid bank account number {string}")
    public void aMerchantWithAInvalidBankAccountNumber(String arg0) {
        latestBankAccountNumber = arg0;
        Assert.assertFalse(accountService.isBankAccountValid(latestBankAccountNumber));
    }
}
