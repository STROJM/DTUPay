package g15.merchantapi.Service;

import implementation.IMessagingClient;
import implementation.MessagingClientFactory;
import messages.reporting.MerchantReportMessage;
import messages.reporting.MerchantReportResponse;

import javax.inject.Singleton;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
@Singleton
public class ReportingService {
    private final IMessagingClient client;

    public ReportingService() {
        this.client = MessagingClientFactory.create();
    }

    public MerchantReportResponse getPreviousPayments(MerchantReportMessage request) {
        try {
            return client.call(request, MerchantReportResponse.class);
        } catch (Exception e) {
            return new MerchantReportResponse();
        }
    }
}
