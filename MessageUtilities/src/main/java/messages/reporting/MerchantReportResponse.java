package messages.reporting;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MerchantReportResponse {
    private final List<MerchantTransactionReport> transactionReports;
    public MerchantReportResponse() {
        this(new LinkedList<>());
    }
    public MerchantReportResponse(List<MerchantTransactionReport> transactionReports){
        this.transactionReports = transactionReports;
    }

    public static MerchantReportResponse from(List<Report> reports) {
        var transactionReports = reports.stream()
                .map(MerchantTransactionReport::from)
                .collect(Collectors.toList());

        return new MerchantReportResponse(transactionReports);
    }

    public List<MerchantTransactionReport> getTransactionReports() { return transactionReports; }
}
