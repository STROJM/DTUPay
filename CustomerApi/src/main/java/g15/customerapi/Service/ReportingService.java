package g15.customerapi.Service;

import g15.customerapi.Service.messages.CustomerReportMessage;
import g15.customerapi.Service.messages.CustomerReportResponse;
import messaging.v2.IMessagingClient;
import messaging.v2.MessagingClientFactory;

import javax.inject.Singleton;

@Singleton
public class ReportingService {
    private final IMessagingClient client;

    public ReportingService() {
        this.client = MessagingClientFactory.create();
    }

    public CustomerReportResponse getPreviousPayments(CustomerReportMessage request) {
        try {
            return client.call(request, CustomerReportResponse.class);
        } catch (Exception e) {
            return new CustomerReportResponse();
        }
    }
}
