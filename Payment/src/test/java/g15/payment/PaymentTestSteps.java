package g15.payment;

//import dtu.ws.fastmoney.*;
import g15.payment.adaptors.BankAdaptor;
import g15.payment.adaptors.TokenManagementAdaptor;
import g15.payment.exceptions.BankException;
import g15.payment.messages.*;
import g15.payment.repositories.PaymentRepository;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import org.junit.Assert;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PaymentTestSteps {
    BankAdaptor bankAdaptor = mock(BankAdaptor.class);
    MessageQueue queue = mock(MessageQueue.class);
    PaymentRepository paymentRepository = new PaymentRepository();
    PaymentService service = new PaymentService(paymentRepository, bankAdaptor);
    TokenManagementAdaptor tokenManagementAdaptor = new TokenManagementAdaptor(queue, service);
    EnrichedMessage payment;
    StoredMessage expectedStoredPayment;

    @Given("a valid {string} event for a payment of {int} kr is received")
    public void aEventForAPaymentIsReceived(String eventName, int amount) {
        payment = new EnrichedPaymentMessage("customer", "merchant", "token", new BigDecimal(amount), "desc", true, "");
        expectedStoredPayment = StoredMessage.from(payment);
        Event event = new Event(eventName, new Object[]{payment});
        tokenManagementAdaptor.handleEnrichedPaymentEvent(event);
    }

    @When("the payment amount is transferred in the bank")
    public void thePaymentAmountIsTransferredInTheBank() throws BankException {
        verify(bankAdaptor).performPayment((EnrichedPaymentMessage) payment);
    }

    @When("the refund amount is transferred in the bank")
    public void theRefundAmountIsTransferredInTheBank() throws BankException {
        verify(bankAdaptor).performRefund((EnrichedRefundMessage) payment);
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

    @Given("an invalid {string} payment event")
    public void aInvalidPaymentEvent(String eventName) {
        payment = new EnrichedPaymentMessage("customer", "merchant", "token", new BigDecimal(100), "desc", false, "");
        Event event = new Event(eventName, new Object[]{payment});
        tokenManagementAdaptor.handleEnrichedPaymentEvent(event);
    }

    @Given("an invalid {string} refund event")
    public void aInvalidRefundEvent(String eventName) {
        payment = new EnrichedRefundMessage("customer", "merchant", "token", new BigDecimal(100), "desc", false, "");
        Event event = new Event(eventName, new Object[]{payment});
        tokenManagementAdaptor.handleEnrichedRefundEvent(event);
    }

    @Given("a valid {string} event for a refund of {int} kr is received")
    public void aValidEventForARefundOfKrIsReceived(String eventName, int amount) {
        payment = new EnrichedRefundMessage("customer", "merchant", "token", new BigDecimal(amount), "desc", true, "");
        expectedStoredPayment = StoredMessage.from(payment);
        Event event = new Event(eventName, new Object[]{payment});
        tokenManagementAdaptor.handleEnrichedRefundEvent(event);
    }
}