package g15.reporting;

import com.rabbitmq.client.Delivery;
import messages.reporting.TransactionCompleted;
import messages.reporting.TransactionFailed;
import g15.reporting.adaptors.MessageAdaptor;
import g15.reporting.repositories.ReportRepository;
import g15.reporting.services.ReportService;
import implementation.MessagingClient;
import implementation.Message;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import messages.payment.EnrichedPaymentMessage;
import messages.reporting.*;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Johannes Hald s202784
 * @author Søren Andersen s182881
 */

public class ReportingTestSteps {

    ReportRepository reportRepository = new ReportRepository();
    ReportService reportService = new ReportService(reportRepository);
    Delivery fakeDelivery = mock(Delivery.class);
    MessagingClient client = mock(MessagingClient.class);
    MessageAdaptor messageAdaptor = new MessageAdaptor(client, reportService);
    Report paymentReport;
    private String customerId;
    private String merchantId;

    @Given("a completed transaction event for a payment of {float} kr is received")
    public void aCompletedTransactionEventForAPaymentOfKrIsReceived(float amount) {
        this.customerId = UUID.randomUUID().toString();
        this.merchantId = UUID.randomUUID().toString();
        var payment = new EnrichedPaymentMessage(customerId, merchantId, UUID.randomUUID().toString(), BigDecimal.valueOf(amount), "description", true, null);
        var event = new TransactionCompleted(payment);
        this.paymentReport = event.getReport();
        var report = new TransactionReportMessage(event);
        var message = Message.from(fakeDelivery, report);
        messageAdaptor.handleTransactionReportMessage(message);
    }

    @Given("a failed transaction event for a payment of {float} kr is received")
    public void aFailedTransactionEventForAPaymentOfKrIsReceived(float amount) {
        this.customerId = UUID.randomUUID().toString();
        this.merchantId = UUID.randomUUID().toString();
        var payment = new EnrichedPaymentMessage(customerId, merchantId, UUID.randomUUID().toString(), BigDecimal.valueOf(amount), "description", true, null);
        var event = new TransactionFailed(payment, "Failed payment");
        this.paymentReport = event.getReport();
        var report = new TransactionReportMessage(event);
        var message = Message.from(fakeDelivery, report);
        messageAdaptor.handleTransactionReportMessage(message);
    }

    @Then("the customer who performed the transaction can get the report")
    public void theCustomerWhoPerformedTheTransactionCanGetTheReport() {
        var request = new CustomerReportMessage(this.customerId);
        var message = Message.from(fakeDelivery, request);
        messageAdaptor.handleCustomerReportEvent(message);
        var captor = ArgumentCaptor.forClass(Message.class);
        verify(client).reply(captor.capture());
        var response = (CustomerReportResponse)captor.getValue().model;
        assertTrue(response.getTransactionReports().contains(CustomerTransactionReport.from(this.paymentReport)));
    }

    @Then("the merchant who received the transaction can get the report")
    public void theMerchantWhoReceivedTheTransactionCanGetTheReport() {
        var request = new MerchantReportMessage(this.merchantId);
        var message = Message.from(fakeDelivery, request);
        messageAdaptor.handleMerchantReportEvent(message);
        var captor = ArgumentCaptor.forClass(Message.class);
        verify(client).reply(captor.capture());
        var response = (MerchantReportResponse)captor.getValue().model;
        assertTrue(response.getTransactionReports().contains(MerchantTransactionReport.from(this.paymentReport)));
    }

    @Then("the manager can get the report")
    public void theManagerCanGetTheReport() {
        var request = new ManagerReportMessage();
        var message = Message.from(fakeDelivery, request);
        messageAdaptor.handleManagerReportEvent(message);
        var captor = ArgumentCaptor.forClass(Message.class);
        verify(client).reply(captor.capture());
        var response = (ManagerReportResponse)captor.getValue().model;
        assertTrue(response.getTransactionReports().contains(ManagerTransactionReport.from(this.paymentReport)));
    }
}
