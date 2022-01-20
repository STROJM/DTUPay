package g15.customerapi.Adaptors;

import implementation.MessagingClient;
import implementation.MessagingClientFactory;
import messages.reporting.CustomerReportMessage;
import messages.reporting.CustomerReportResponse;

import javax.inject.Singleton;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
@Singleton
public class ReportingAdaptor {
    private final MessagingClient client;

    public ReportingAdaptor() {
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
