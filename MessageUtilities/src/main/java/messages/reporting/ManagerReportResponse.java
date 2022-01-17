package messages.reporting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerReportResponse {
    private List<ManagerTransactionReport> transactionReports;

    public ManagerReportResponse() {
        this(new LinkedList<>());
    }
    public ManagerReportResponse(List<ManagerTransactionReport> transactionReports){
        this.transactionReports = transactionReports;
    }


    public static ManagerReportResponse from(List<Report> reports) {
        var transactionReports = reports.stream()
                .map(ManagerTransactionReport::from)
                .collect(Collectors.toList());

        return new ManagerReportResponse(transactionReports);
    }

    public List<ManagerTransactionReport> getTransactionReports() { return transactionReports; }
}
