package g15.customerapi.Service;

import implementation.IMessagingClient;
import implementation.MessagingClientFactory;
import messages.reporting.CustomerReportMessage;
import messages.reporting.CustomerReportResponse;

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
