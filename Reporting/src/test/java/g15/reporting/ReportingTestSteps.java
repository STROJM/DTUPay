package g15.reporting;

import g15.payment.messages.EnrichedPaymentMessage;
import g15.payment.messages.EnrichedRefundMessage;
import g15.payment.messages.PaymentResponseMessage;
import g15.reporting.adaptors.MessageAdaptor;
import g15.reporting.messages.PaymentReportStoreMessage;
import g15.reporting.messages.RefundReportStoreMessage;
import g15.reporting.messages.Report;
import g15.reporting.messages.ReportStoreResponse;
import g15.reporting.repositories.ReportRepository;
import g15.reporting.services.ReportService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import messaging.Event;
import messaging.MessageQueue;
import org.junit.Assert;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ReportingTestSteps {

    ReportRepository reportRepository = new ReportRepository();
    ReportService reportService = new ReportService(reportRepository);
    MessageQueue queue = mock(MessageQueue.class);
    MessageAdaptor messageAdaptor = new MessageAdaptor(queue, reportService);
    Report mostRecentReportForStore = new Report();

    @Given("a valid {string} event for a payment of {int} kr is received")
    public void aValidEventForAPaymentOfKrIsReceived(String eventName, int amount) {
        var paymentMessage = new EnrichedPaymentMessage("customer", "merchant", "token", new BigDecimal(amount), "desc", true, "");
        var paymentResponse = new PaymentResponseMessage();
        mostRecentReportForStore = new PaymentReportStoreMessage(paymentMessage, paymentResponse);

        Event event = new Event(eventName, new Object[]{mostRecentReportForStore});
        messageAdaptor.handlePaymentReportEvent(event);
    }

    @Given("a valid {string} event for a refund of {int} kr is received")
    public void aValidEventForARefundOfKrIsReceived(String eventName, int amount) {
        var refundMessage = new EnrichedRefundMessage("customer", "merchant", "token", new BigDecimal(amount), "desc", true, "");
        var refundResponse = new PaymentResponseMessage();
        mostRecentReportForStore = new RefundReportStoreMessage(refundMessage, refundResponse);

        Event event = new Event(eventName, new Object[]{mostRecentReportForStore});
        messageAdaptor.handleRefundReportEvent(event);
    }

    @Then("the report is stored")
    public void theReportIsStored() {
        Assert.assertTrue(reportService.getReports().contains(mostRecentReportForStore));
    }

    @And("a valid {string} event is sent")
    public void aValidEventIsSent(String eventName) {
        var response = new ReportStoreResponse(true, "");
        Event event = new Event(eventName, new Object[]{response});
        verify(queue).publish(event);
    }
}
