package g15.token;

import static org.mockito.Mockito.*;

import g15.token.messages.EnrichedPaymentMessage;
import g15.token.messages.PaymentMessage;
import g15.token.messages.TokensRequestMessage;
import g15.token.messages.TokensResponseMessage;
import g15.token.services.MessageService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;


public class MessageTestSteps {
    MessageQueue queue = mock(MessageQueue.class);
    MessageService service = new MessageService(queue);
    private String bankAccountNumber;

    ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
    private String tokenToUse;

    @Given("a customer with bank account number {string}")
    public void aCustomerWithBankAccountNumber(String bankAccountNr) {
        this.bankAccountNumber = bankAccountNr;
    }

    @And("the customer owns an unused token")
    public void theCustomerOwnsTokens() {
        var request = new TokensRequestMessage(bankAccountNumber, 1);
        service.handleTokensRequest(new Event("TokensRequest", new Object[]{request}));
        verify(queue).publish(eventCaptor.capture());
        var tokenResponse = eventCaptor.getValue().getArgument(0, TokensResponseMessage.class);
        this.tokenToUse = tokenResponse.getTokens()[0];
    }

    @When("the {string} event for {int} tokens is received")
    public void theEventForTokensIsReceived(String eventName, int amount) {
        var request = new TokensRequestMessage(this.bankAccountNumber, amount);
        service.handleTokensRequest(new Event(eventName,new Object[] {request}));
    }

    @Then("the {string} valid token response event is sent")
    public void theEventForTokensIsSent(String eventName) {
        var expected = new TokensResponseMessage(true, null, null);
        verify(queue).publish(argThat(new ValidTokensResponseEventMatcher(expected)));
    }

    @Then("the {string} invalid token response event is sent with error {string}")
    public void theEventForTokensIsSent(String eventName, String error) {
        var expected = new TokensResponseMessage(false, error, null);
        verify(queue).publish(argThat(new ValidTokensResponseEventMatcher(expected)));
    }

    @When("the {string} event for a payment request is received")
    public void theEventIsReceived(String eventName) {
        var request = new PaymentMessage(
          "merchantBankAccountNumber",
                tokenToUse,
                BigDecimal.valueOf(10),
          "description"
        );

        service.handleNonValidatedPaymentRequest(new Event(eventName,new Object[] {request}));
    }

    @Then("the {string} valid event for an enriched payment request is sent")
    public void theEventIsSent(String eventName) {
        var expected = new EnrichedPaymentMessage(
                bankAccountNumber,
                "merchantBankAccountNumber",
                tokenToUse,
                BigDecimal.valueOf(10),
                "description",
                true,
                null
        );
        var event = new Event(eventName, new Object[] {expected});
        verify(queue).publish(event);
    }
}
