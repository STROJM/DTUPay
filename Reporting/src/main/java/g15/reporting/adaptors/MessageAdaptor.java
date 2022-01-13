package g15.reporting.adaptors;

import g15.reporting.services.ReportService;
import g15.reporting.messages.PaymentReport;
import g15.reporting.messages.RefundReport;
import g15.reporting.messages.ReportResponse;
import messaging.Event;
import messaging.MessageQueue;

public class MessageAdaptor {
    private MessageQueue queue;
    private ReportService reportingService;

    public MessageAdaptor(MessageQueue queue, ReportService reportingService) {
        this.queue = queue;
        this.reportingService = reportingService;
        this.queue.addHandler("PaymentReportMessage", this::handlePaymentReportEvent);
        this.queue.addHandler("RefundReportMessage", this::handleRefundReportEvent);
    }

    public void handlePaymentReportEvent(Event event) {
        var report = event.getArgument(0, PaymentReport.class);
        reportingService.saveReport(report);

        ReportResponse response = new ReportResponse(true, "");
        Event responseEvent = new Event("PaymentReportFinished", new Object[] { response });
        this.queue.publish(responseEvent);
    }

    public void handleRefundReportEvent(Event event) {
        var report = event.getArgument(0, RefundReport.class);
        reportingService.saveReport(report);

        ReportResponse response = new ReportResponse(true, "");
        Event responseEvent = new Event("RefundReportFinished", new Object[] { response });
        this.queue.publish(responseEvent);
    }
}