package messages.reporting;

import java.util.ArrayList;
import java.util.List;

public class ManagerReportResponse {
    private List<ManagerTransactionReport> transactionList = new ArrayList<>();

    public ManagerReportResponse() {}

    public void addTransactionReport(ManagerTransactionReport transactionReport) {
        this.transactionList.add(transactionReport);
    }

    public List<ManagerTransactionReport> getAllTransactions() { return transactionList; }
}
