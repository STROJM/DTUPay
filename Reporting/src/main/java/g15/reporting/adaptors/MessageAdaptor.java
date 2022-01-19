package g15.reporting.adaptors;

import g15.reporting.services.ReportService;
import implementation.IMessagingClient;
import implementation.Message;
import messages.reporting.*;

/**
 * @author Roar Nind Steffensen
 * @author Johannes Hald s202784
 */
public class MessageAdaptor {
    private final ReportService reportingService;
    private final IMessagingClient client;

    public MessageAdaptor(IMessagingClient client, ReportService reportService){
        this.client = client;
        this.reportingService = reportService;
        this.client.register(this::handleTransactionReportMessage, TransactionReportMessage.class);
        this.client.register(this::handleCustomerReportEvent, CustomerReportMessage.class);
        this.client.register(this::handleMerchantReportEvent, MerchantReportMessage.class);
        this.client.register(this::handleManagerReportEvent, ManagerReportMessage.class);
    }

    public void handleTransactionReportMessage(Message<TransactionReportMessage> message) {
        this.reportingService.save(message.model.getTransactionEvent());
    }
    public void handleCustomerReportEvent(Message<CustomerReportMessage> message) {
        var reports = this.reportingService.getByCustomerId(message.model.getCustomerBankAccount());
        this.client.reply(message.update(reports));
    }
    public void handleMerchantReportEvent(Message<MerchantReportMessage> message) {
        var reports = this.reportingService.getByMerchantId(message.model.getMerchantBankAccount());
        this.client.reply(message.update(reports));
    }
    public void handleManagerReportEvent(Message<ManagerReportMessage> message) {
        var reports = this.reportingService.getAll();
        this.client.reply(message.update(reports));
    }
}