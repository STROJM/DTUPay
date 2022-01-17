package messages.reporting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerReportResponse implements Serializable {

    private final List<CustomerTransactionReport> transactionList = new ArrayList<>();

    public CustomerReportResponse() {}

    public void addTransactionReport(CustomerTransactionReport transactionReport) {
        this.transactionList.add(transactionReport);
    }

    public List<CustomerTransactionReport> getAllTransactions() { return transactionList; }
}
