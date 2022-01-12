package g15.account;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AccountTestSteps {
    @Given("a customer with bank account number {string}")
    public void aCustomerWithBankAccountNumber(String bankAccountNumber) {
    }

    @When("the user registers in dtu pay")
    public void theUserRegistersInDtuPay() {
    }

    @Then("the user successfully registers")
    public void theUserSuccessfullyRegisters() {
    }

    @Given("a merchant with bank account number {string}")
    public void aMerchantWithBankAccountNumber(String bankAccountNumber) {
    }

    @Then("the user fails to register registers")
    public void theUserFailsToRegisterRegisters() {
    }

    @And("the bank account number is valid")
    public void theBankAccountNumberIsValid() {
    }

    @And("bank account did not exist")
    public void theBankAccountNumberIsInvalid() {
    }
}
