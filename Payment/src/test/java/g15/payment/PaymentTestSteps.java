package g15.payment;

import com.rabbitmq.client.Delivery;
import g15.payment.adaptors.BankAdaptor;
import g15.payment.adaptors.MessageAdaptor;
import g15.payment.exceptions.BankException;
import messages.payment.*;
import g15.payment.repositories.PaymentRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import implementation.IMessagingClient;
import implementation.Message;
import org.junit.Assert;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PaymentTestSteps {
    Delivery fakeDelivery = mock(Delivery.class);
    BankAdaptor bankAdaptor = mock(BankAdaptor.class);
    IMessagingClient client = mock(IMessagingClient.class);
    PaymentRepository paymentRepository = new PaymentRepository();
    PaymentService service = new PaymentService(paymentRepository, bankAdaptor);
    MessageAdaptor messageAdaptor = new MessageAdaptor(client, service);
    EnrichedMessage payment;
    StoredMessage expectedStoredPayment;

    @Given("a valid {string} event for a payment of {int} kr is received")
    public void aEventForAPaymentIsReceived(String eventName, int amount) {
        payment = new EnrichedPaymentMessage("customer", "merchant", "token", new BigDecimal(amount), "desc", true, "");
        expectedStoredPayment = StoredMessage.from(payment);
        var message = Message.from(fakeDelivery, (EnrichedPaymentMessage)payment);
        //Event event = new Event(eventName, new Object[]{payment});
        messageAdaptor.handleEnrichedPaymentEvent(message);
    }

    @When("the payment amount is transferred in the bank")
    public void thePaymentAmountIsTransferredInTheBank() throws BankException {
        verify(bankAdaptor).performPayment((EnrichedPaymentMessage) payment);
    }

    @When("the refund amount is transferred in the bank")
    public void theRefundAmountIsTransferredInTheBank() throws BankException {
        verify(bankAdaptor).performRefund((EnrichedRefundMessage) payment);
    }

    @Then("the transaction has been stored")
    public void theTransactionHasBeenStored() {
        Assert.assertTrue(service.listPayments().contains(expectedStoredPayment));
    }

    @Then("the transaction has not been stored")
    public void theTransactionHasNotBeenStored() {
        Assert.assertFalse(service.listPayments().contains(expectedStoredPayment));
    }


    @And("a valid {string} event is sent")
    public void aEventIsSent(String eventName) {
        var captor = ArgumentCaptor.forClass(Message.class);
        verify(client).reply(captor.capture());
        var response = (PaymentResponseMessage)captor.getValue().model;
        assertTrue(response.isValid());
    }

    @And("an invalid payment response event is sent with message {string}")
    public void anInvalidPaymentFinishedEventIsSentWithMessage(String errorMessage) {
        var captor = ArgumentCaptor.forClass(Message.class);
        verify(client).reply(captor.capture());
        var response = (PaymentResponseMessage)captor.getValue().model;
        assertFalse(response.isValid());
        assertEquals(errorMessage, response.getErrorMessage());
    }


    @Given("an invalid {string} payment event")
    public void aInvalidPaymentEvent(String eventName) {
        payment = new EnrichedPaymentMessage("customer", "merchant", "token", new BigDecimal(100), "desc", false, "");
        var message = Message.from(fakeDelivery, (EnrichedPaymentMessage)payment);
        messageAdaptor.handleEnrichedPaymentEvent(message);
    }

    @Given("an invalid {string} refund event")
    public void aInvalidRefundEvent(String eventName) {
        payment = new EnrichedRefundMessage("customer", "merchant", "token", new BigDecimal(100), "desc", false, "");
        var message = Message.from(fakeDelivery, (EnrichedRefundMessage)payment);
        messageAdaptor.handleEnrichedRefundEvent(message);
    }

    @Given("a valid {string} event for a refund of {int} kr is received")
    public void aValidEventForARefundOfKrIsReceived(String eventName, int amount) {
        payment = new EnrichedRefundMessage("customer", "merchant", "token", new BigDecimal(amount), "desc", true, "");
        expectedStoredPayment = StoredMessage.from(payment);
        var message = Message.from(fakeDelivery, (EnrichedRefundMessage)payment);
        messageAdaptor.handleEnrichedRefundEvent(message);
    }
}