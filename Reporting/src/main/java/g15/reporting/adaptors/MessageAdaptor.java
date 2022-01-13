package g15.reporting.adaptors;

import g15.reporting.services.ReportService;
import g15.reporting.messages.PaymentReport;
import g15.reporting.messages.RefundReport;
import g15.reporting.messages.ReportStoreResponse;
import messaging.Event;
import messaging.MessageQueue;

public class MessageAdaptor {
    private MessageQueue queue;
    private ReportService reportingService;

    public MessageAdaptor(MessageQueue queue, ReportService reportingService) {
        this.queue = queue;
        this.reportingService = reportingService;
        this.queue.addHandler("PaymentReportStoreMessage", this::handlePaymentReportEvent);
        this.queue.addHandler("RefundReportStoreMessage", this::handleRefundReportEvent);
        this.queue.addHandler("ManagerReportMessage", this::handleManagerReportEvent);
        this.queue.addHandler("CustomerReportMessage", this::handleCustomerReportEvent);
        this.queue.addHandler("MerchantReportMessage", this::handleMerchantReportEvent);
    }

    public void handlePaymentReportEvent(Event event) {
        var report = event.getArgument(0, PaymentReport.class);
        reportingService.saveReport(report);

        ReportStoreResponse response = new ReportStoreResponse(true, "");
        Event responseEvent = new Event("PaymentReportFinished", new Object[] { response });
        this.queue.publish(responseEvent);
    }

    public void handleRefundReportEvent(Event event) {
        var report = event.getArgument(0, RefundReport.class);
        reportingService.saveReport(report);

        ReportStoreResponse response = new ReportStoreResponse(true, "");
        Event responseEvent = new Event("RefundReportFinished", new Object[] { response });
        this.queue.publish(responseEvent);
    }

    public void handleManagerReportEvent(Event event) {}

    public void handleCustomerReportEvent(Event event) {}

    public void handleMerchantReportEvent(Event event) {}

}