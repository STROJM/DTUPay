package g15.payment;

//import dtu.ws.fastmoney.*;
import g15.payment.adaptors.BankAdaptor;
import g15.payment.adaptors.TokenManagementAdaptor;
import g15.payment.exceptions.BankException;
import g15.payment.messages.EnrichedPaymentMessage;
import g15.payment.messages.PaymentResponseMessage;
import g15.payment.messages.StoredPaymentMessage;
import g15.payment.repositories.PaymentRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import org.junit.Assert;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PaymentTestSteps {
    BankAdaptor bankAdaptor = mock(BankAdaptor.class);
    MessageQueue queue = mock(MessageQueue.class);
    PaymentRepository paymentRepository = new PaymentRepository();
    PaymentService service = new PaymentService(paymentRepository, bankAdaptor);
    TokenManagementAdaptor tokenManagementAdaptor = new TokenManagementAdaptor(queue, service);
    EnrichedPaymentMessage payment;
    StoredPaymentMessage expectedStoredPayment;

    @Given("a valid {string} event for a payment of {int} kr is received")
    public void aEventForAPaymentIsReceived(String eventName, int amount) {
        payment = new EnrichedPaymentMessage("customer", "merchant", "token", new BigDecimal(amount), "desc", true, "");
        expectedStoredPayment = StoredPaymentMessage.from(payment);
        Event event = new Event(eventName, new Object[]{payment});
        tokenManagementAdaptor.handleEnrichedPaymentEvent(event);
    }

    @When("the amount is transferred in the bank")
    public void theAmountIsTransferredInTheBank() throws BankException {
        verify(bankAdaptor).performPayment(payment);
    }

    @Then("the payment has been stored")
    public void thePaymentHasBeenStored() {
        Assert.assertTrue(service.listPayments().contains(expectedStoredPayment));
    }

    @Then("the payment has not been stored")
    public void thePaymentHasNotBeenStored() {
        Assert.assertFalse(service.listPayments().contains(expectedStoredPayment));
    }


    @And("a valid {string} event is sent")
    public void aEventIsSent(String eventName) {
        var response = new PaymentResponseMessage();
        Event event = new Event(eventName, new Object[]{response});
        verify(queue).publish(event);
    }

    @And("an invalid {string} event is sent with message {string}")
    public void anInvalidEventIsSent(String eventName, String errorMessage) {
        var argument = ArgumentCaptor.forClass(Event.class);
        verify(queue).publish(argument.capture());

        var response = new PaymentResponseMessage(errorMessage);
        Event expectedEvent = new Event(eventName, new Object[]{response});

        Assert.assertEquals(expectedEvent, argument.getValue());
    }
}