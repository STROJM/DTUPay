package g15.reporting;

import com.rabbitmq.client.Delivery;
import g15.reporting.adaptors.MessageAdaptor;
import g15.reporting.repositories.ReportRepository;
import g15.reporting.services.ReportService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import implementation.IMessagingClient;
import implementation.Message;
import messages.payment.*;
import messages.reporting.PaymentReportStoreMessage;
import messages.reporting.RefundReportStoreMessage;
import messages.reporting.Report;
import org.junit.Assert;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;

public class ReportingTestSteps {

    ReportRepository reportRepository = new ReportRepository();
    ReportService reportService = new ReportService(reportRepository);
    Delivery fakeDelivery = mock(Delivery.class);
    IMessagingClient client = mock(IMessagingClient.class);
    MessageAdaptor messageAdaptor = new MessageAdaptor(client, reportService);
    Report mostRecentReportForStore = new Report();

    @Given("a valid {string} event for a payment of {int} kr is received")
    public void aValidEventForAPaymentOfKrIsReceived(String eventName, int amount) {
        var paymentMessage = new EnrichedPaymentMessage("customer", "merchant", "token", new BigDecimal(amount), "desc", true, "");
        var paymentResponse = new PaymentResponseMessage();
        mostRecentReportForStore = new PaymentReportStoreMessage(paymentMessage, paymentResponse);

        var message = Message.from(fakeDelivery, (PaymentReportStoreMessage)mostRecentReportForStore);
        messageAdaptor.handlePaymentReportEvent(message);
    }

    @Given("a valid {string} event for a refund of {int} kr is received")
    public void aValidEventForARefundOfKrIsReceived(String eventName, int amount) {
        var refundMessage = new EnrichedRefundMessage("customer", "merchant", "token", new BigDecimal(amount), "desc", true, "");
        var refundResponse = new PaymentResponseMessage();
        mostRecentReportForStore = new RefundReportStoreMessage(refundMessage, refundResponse);

        var message = Message.from(fakeDelivery, (RefundReportStoreMessage)mostRecentReportForStore);
        messageAdaptor.handleRefundReportEvent(message);
    }

    @Then("the report is stored")
    public void theReportIsStored() {
        Assert.assertTrue(reportService.getReports().contains(mostRecentReportForStore));
    }

//    @And("a valid {string} event is sent")
//    public void aValidEventIsSent(String eventName) {
//
//        var response = new ReportStoreResponse(true, "");
//        Event event = new Event(eventName, new Object[]{response});
//        verify(client).publish(event);
//    }

    @Given("a valid {string} event for a failed payment of {int} kr is received")
    public void aValidEventForAFailedPaymentOfKrIsReceived(String eventName, int amount) {
        var paymentMessage = new EnrichedPaymentMessage("customer", "merchant", "token", new BigDecimal(amount), "desc", false, "Insufficient funds");
        var paymentResponse = new PaymentResponseMessage();
        mostRecentReportForStore = new PaymentReportStoreMessage(paymentMessage, paymentResponse);

        var message = Message.from(fakeDelivery, (PaymentReportStoreMessage)mostRecentReportForStore);
        messageAdaptor.handlePaymentReportEvent(message);
    }

    @Given("a valid {string} event for a failed refund of {int} kr is received")
    public void aValidEventForAFailedRefundOfKrIsReceived(String eventName, int amount) {
        var refundMessage = new EnrichedRefundMessage("customer", "merchant", "token", new BigDecimal(amount), "desc", false, "Insufficient funds");
        var refundResponse = new PaymentResponseMessage();
        mostRecentReportForStore = new RefundReportStoreMessage(refundMessage, refundResponse);

        var message = Message.from(fakeDelivery, (RefundReportStoreMessage)mostRecentReportForStore);
        messageAdaptor.handleRefundReportEvent(message);
    }
}
