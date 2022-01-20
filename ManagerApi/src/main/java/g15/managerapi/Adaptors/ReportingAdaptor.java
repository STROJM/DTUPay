package g15.managerapi.Adaptors;


import implementation.MessagingClient;
import implementation.MessagingClientFactory;
import messages.reporting.ManagerReportMessage;
import messages.reporting.ManagerReportResponse;

import javax.inject.Singleton;

@Singleton
public class ReportingAdaptor {
    private final MessagingClient client;

    public ReportingAdaptor() {
        this.client = MessagingClientFactory.createAwaitableClient();
    }

    public ManagerReportResponse getManagerReport(ManagerReportMessage request){
        try {
            return client.call(request, ManagerReportResponse.class);
        } catch (Exception e) {
            return new ManagerReportResponse();
        }
    }
}
