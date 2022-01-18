package g15.token;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rabbitmq.client.Delivery;
import g15.token.services.MessageService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import implementation.IMessagingClient;
import implementation.Message;
import messages.payment.EnrichedPaymentMessage;
import messages.payment.PaymentMessage;
import messages.tokens.TokensRequestMessage;
import messages.tokens.TokensResponseMessage;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

/**
 * @author Roar Nind Steffensen
 */
public class MessageTestSteps {
    private final Delivery fakeDelivery = mock(Delivery.class);
    IMessagingClient client = mock(IMessagingClient.class);
    MessageService service = new MessageService(client);
    private PaymentMessage paymentRequest;
    private String bankAccountNumber;
    private String tokenToUse;

    @Given("a customer with bank account number {string}")
    public void aCustomerWithBankAccountNumber(String bankAccountNr) {
        this.bankAccountNumber = bankAccountNr;
    }

    @And("the customer owns an unused token")
    public void theCustomerOwnsTokens() {
        var request = new TokensRequestMessage(bankAccountNumber, 1);
        service.handleTokensRequest(Message.from(fakeDelivery, request));
        var captor = ArgumentCaptor.forClass(Message.class);
        verify(client).reply(captor.capture());
        var tokenResponse = (TokensResponseMessage)captor.getValue().model;
        this.tokenToUse = tokenResponse.getTokens()[0];
    }

    @When("the event for {int} tokens is received")
    public void theEventForTokensIsReceived(int amount) {
        var request = new TokensRequestMessage(this.bankAccountNumber, amount);
        service.handleTokensRequest(Message.from(fakeDelivery, request));
    }

    @Then("the valid token response event is sent")
    public void theEventForTokensIsSent() {
        var captor = ArgumentCaptor.forClass(Message.class);
        verify(client).reply(captor.capture());
        var result = (TokensResponseMessage)captor.getValue().model;
        assertTrue(result.isSuccess());
        assertNotNull(result.getTokens());
    }

    @Then("an invalid payment request event is sent with error {string}")
    public void theEventForTokensIsSent(String error) {
        var captor = ArgumentCaptor.forClass(Message.class);
        verify(client).forward(captor.capture(), eq(EnrichedPaymentMessage.class));
        var result = (EnrichedPaymentMessage)captor.getValue().model;
        assertFalse(result.isValid());
        assertEquals(error, result.getErrorMessage());
    }

    @When("the event for a payment request is received")
    public void theEventIsReceived() {
        this.paymentRequest = new PaymentMessage(
          "merchantBankAccountNumber",
                tokenToUse,
                BigDecimal.valueOf(10),
          "description"
        );

        service.handleNonValidatedPaymentRequest(Message.from(fakeDelivery, paymentRequest));
    }

    @Then("a valid event for an enriched payment request is sent")
    public void theEventIsSent() {
        var captor = ArgumentCaptor.forClass(Message.class);
        verify(client).forward(captor.capture(), eq(EnrichedPaymentMessage.class));
        var result = (EnrichedPaymentMessage)captor.getValue().model;
        assertEquals(bankAccountNumber, result.getCustomerBankAccount());
        assertEquals(paymentRequest.getMerchantBankAccount(), result.getMerchantBankAccount());
        assertEquals(paymentRequest.getAmount(), result.getAmount());
        assertEquals(paymentRequest.getDescription(), result.getDescription());
        assertEquals(tokenToUse, result.getToken());
        assertTrue(result.isValid());
        assertNull(result.getErrorMessage());
    }
}
