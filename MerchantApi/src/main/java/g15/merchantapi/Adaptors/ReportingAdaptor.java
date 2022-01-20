package g15.merchantapi.Adaptors;

import implementation.MessagingClient;
import implementation.MessagingClientFactory;
import messages.reporting.MerchantReportMessage;
import messages.reporting.MerchantReportResponse;

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

    public MerchantReportResponse getPreviousPayments(MerchantReportMessage request) {
        try {
            return client.call(request, MerchantReportResponse.class);
        } catch (Exception e) {
            return new MerchantReportResponse();
        }
    }
}
