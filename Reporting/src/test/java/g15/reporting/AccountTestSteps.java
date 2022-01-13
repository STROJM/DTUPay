package g15.reporting;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AccountTestSteps {

//    BankAdaptor bankAdaptor = mock(BankAdaptor.class);
//    MessageQueue queue = mock(MessageQueue.class);
//    PaymentRepository paymentRepository = new PaymentRepository();
//    PaymentService service = new PaymentService(paymentRepository, bankAdaptor);
//    MessageAdaptor messageAdaptor = new MessageAdaptor(queue, service);
//    EnrichedMessage payment;
//    StoredMessage expectedStoredPayment;

    @Given("a customer with bank account number {string}")
    public void aCustomerWithBankAccountNumber(String arg0) {
    }

    @When("the user registers in dtu pay")
    public void theUserRegistersInDtuPay() {
    }

    @Then("the user successfully registers")
    public void theUserSuccessfullyRegisters() {
    }

    @Given("a valid {string} event for a payment of {int} kr is received")
    public void aValidEventForAPaymentOfKrIsReceived(String arg0, int arg1) {
        
        
    }

    @When("the payment amount is transferred in the bank")
    public void thePaymentAmountIsTransferredInTheBank() {
        
    }

    @Then("the transaction has been stored")
    public void theTransactionHasBeenStored() {
    }

    @And("a valid {string} event is sent")
    public void aValidEventIsSent(String arg0) {
        
    }

    @Given("a valid {string} event for a refund of {int} kr is received")
    public void aValidEventForARefundOfKrIsReceived(String arg0, int arg1) {
        
    }

    @When("the refund amount is transferred in the bank")
    public void theRefundAmountIsTransferredInTheBank() {
    }
}
