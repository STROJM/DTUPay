package messages.reporting;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerReportResponse implements Serializable {

    private final List<CustomerTransactionReport> transactionReports;
    public CustomerReportResponse(){
        this(new LinkedList<>());
    }
    public CustomerReportResponse(List<CustomerTransactionReport> transactionReports){
        this.transactionReports = transactionReports;
    }

    public static CustomerReportResponse from(List<Report> reports) {
        var transactionReports = reports.stream()
                .map(CustomerTransactionReport::from)
                .collect(Collectors.toList());

        return new CustomerReportResponse(transactionReports);
    }

    public List<CustomerTransactionReport> getTransactionReports(){
        return transactionReports;
    }
}
