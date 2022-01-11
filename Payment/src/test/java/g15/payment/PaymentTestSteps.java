package g15.payment;

//import dtu.ws.fastmoney.*;
import g15.payment.adaptors.Bank;
import g15.payment.exceptions.BankException;
import g15.payment.messages.EnrichedPayment;
import g15.payment.messages.PaymentResponse;
import g15.payment.repositories.PaymentRepository;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import messaging.Event;
import messaging.MessageQueue;
import org.junit.Assert;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PaymentTestSteps {
    Bank bank = mock(Bank.class);
    MessageQueue queue = mock(MessageQueue.class);
    PaymentRepository paymentRepository = new PaymentRepository();
    PaymentService service = new PaymentService(queue, paymentRepository, bank);
    EnrichedPayment payment;
    EnrichedPayment expected = new EnrichedPayment("customer", "merchant", new BigDecimal(100), "desc", true, "");

    @When("a valid {string} event for a payment is received")
    public void aEventForAPaymentIsReceived(String eventName) {
        payment = new EnrichedPayment("customer", "merchant", new BigDecimal(100), "desc", true, "");
        Event event = new Event(eventName, new Object[]{payment});
        service.handleEnrichedPaymentEvent(event);
    }

    @Then("the amount is transferred in the bank")
    public void theAmountIsTransferredInTheBank() throws BankException {
        verify(bank).performPayment(expected);
    }

    @And("the payment has been stored")
    public void thePaymentHasBeenStored() {
        Assert.assertTrue(service.listPayments().contains(expected));
    }

    @And("a {string} event is sent")
    public void aEventIsSent(String eventName) {
        var response = new PaymentResponse();
        Event event = new Event(eventName, new Object[]{response});
        verify(queue).publish(event);
    }
}