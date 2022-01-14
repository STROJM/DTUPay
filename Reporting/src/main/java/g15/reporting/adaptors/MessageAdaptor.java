package g15.reporting.adaptors;

import g15.reporting.messages.*;
import g15.reporting.reports.CustomerTransactionReport;
import g15.reporting.reports.ManagerTransactionReport;
import g15.reporting.reports.MerchantTransactionReport;
import g15.reporting.services.ReportService;
import messaging.v2.IMessagingClient;
import messaging.v2.Message;

public class MessageAdaptor {
    private ReportService reportingService;
    private final IMessagingClient client;

    public MessageAdaptor(IMessagingClient client, ReportService reportService){
        this.client = client;
        this.reportingService = reportService;
        this.client.register(this::handlePaymentReportEvent, PaymentReportStoreMessage.class);
        this.client.register(this::handleRefundReportEvent, RefundReportStoreMessage.class);
        this.client.register(this::handleManagerReportEvent, ManagerReportMessage.class);
        this.client.register(this::handleCustomerReportEvent, CustomerReportMessage.class);
        this.client.register(this::handleMerchantReportEvent, MerchantReportMessage.class);
    }

    public void handlePaymentReportEvent(Message<PaymentReportStoreMessage> message) {
        var report = message.model;
        reportingService.saveReport(report);
    }

    public void handleRefundReportEvent(Message<RefundReportStoreMessage> message) {
        var report = message.model;
        reportingService.saveReport(report);
    }

    public void handleManagerReportEvent(Message<ManagerReportMessage> message) {

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

        this.client.reply(message.update(fullManagerReport));
    }

    public void handleCustomerReportEvent(Message<CustomerReportMessage> message) {
        var customerReportMessage = message.model;

        CustomerReportResponse fullCustomerReport = new CustomerReportResponse();

        for (Report report : this.reportingService.getReports()) {
            if (report.getCustomerBankAccountNumber().equals(customerReportMessage.getCustomerBankAccount()) &&
                    report.isValid()) {
                fullCustomerReport.addTransactionReport(new CustomerTransactionReport(
                        report.getToken(),
                        report.getDescription(),
                        report.getErrorMessage(),
                        report.getMerchantBankAccountNumber(),
                        report.getAmount())
                );
            }
        }

        this.client.reply(message.update(fullCustomerReport));
    }

    public void handleMerchantReportEvent(Message<MerchantReportMessage> message) {
        var merchantReportMessage = message.model;

        MerchantReportResponse fullMerchantReport = new MerchantReportResponse();

        for (Report report : this.reportingService.getReports()) {
            if (report.getMerchantBankAccountNumber().equals(merchantReportMessage.getMerchantBankAccount()) &&
                report.isValid()) {
                fullMerchantReport.addTransactionReport(new MerchantTransactionReport(
                        report.getToken(),
                        report.getDescription(),
                        report.getErrorMessage(),
                        report.getAmount())
                );
            }
        }

        this.client.reply(message.update(fullMerchantReport));
    }
}