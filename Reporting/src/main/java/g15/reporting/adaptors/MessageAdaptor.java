package g15.reporting.adaptors;

import g15.reporting.messages.*;
import g15.reporting.reports.CustomerTransactionReport;
import g15.reporting.reports.ManagerTransactionReport;
import g15.reporting.reports.MerchantTransactionReport;
import g15.reporting.services.ReportService;
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
        var report = event.getArgument(0, PaymentReportStoreMessage.class);
        reportingService.saveReport(report);

        ReportStoreResponse response = new ReportStoreResponse(true, "");
        Event responseEvent = new Event("PaymentReportFinished", new Object[] { response });
        this.queue.publish(responseEvent);
    }

    public void handleRefundReportEvent(Event event) {
        var report = event.getArgument(0, RefundReportStoreMessage.class);
        reportingService.saveReport(report);

        ReportStoreResponse response = new ReportStoreResponse(true, "");
        Event responseEvent = new Event("RefundReportFinished", new Object[] { response });
        this.queue.publish(responseEvent);
    }

    public void handleManagerReportEvent(Event event) {
        // NOTE: We assume only one manager exists, so we don't need a specific ID for this dude.

        ManagerReportResponse fullManagerReport = new ManagerReportResponse();

        for (Report report : this.reportingService.getReports()) {
            fullManagerReport.addTransactionReport(new ManagerTransactionReport(
                    report.isValid(),
                    report.getErrorMessage(),
                    report.getCustomerBankAccountNumber(),
                    report.getMerchantBankAccountNumber(),
                    report.getToken(),
                    report.getAmount(),
                    report.getDescription())
                );
        }

        Event responseEvent = new Event("ManagerReportFinished", new Object[] { fullManagerReport });
        this.queue.publish(responseEvent);
    }

    public void handleCustomerReportEvent(Event event) {
        var customerReportMessage = event.getArgument(0, CustomerReportMessage.class);

        CustomerReportResponse fullCustomerReport = new CustomerReportResponse();

        for (Report report : this.reportingService.getReports()) {
            if (report.getCustomerBankAccountNumber().equals(customerReportMessage.getCustomerBankAccount())) {
                fullCustomerReport.addTransactionReport(new CustomerTransactionReport(
                        report.getToken(),
                        report.getDescription(),
                        report.getErrorMessage(),
                        report.getMerchantBankAccountNumber(),
                        report.getAmount())
                );
            }
        }

        Event responseEvent = new Event("CustomerReportFinished", new Object[] { fullCustomerReport });
        this.queue.publish(responseEvent);
    }

    public void handleMerchantReportEvent(Event event) {
        var merchantReportMessage = event.getArgument(0, MerchantReportMessage.class);

        MerchantReportResponse fullMerchantReport = new MerchantReportResponse();

        for (Report report : this.reportingService.getReports()) {
            if (report.getMerchantBankAccountNumber().equals(merchantReportMessage.getMerchantBankAccount())) {
                fullMerchantReport.addTransactionReport(new MerchantTransactionReport(
                        report.getToken(),
                        report.getDescription(),
                        report.getErrorMessage(),
                        report.getAmount())
                );
            }
        }

        Event responseEvent = new Event("MerchantReportFinished", new Object[] { fullMerchantReport });
        this.queue.publish(responseEvent);
    }
}