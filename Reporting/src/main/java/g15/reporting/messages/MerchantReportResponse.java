package g15.reporting.messages;

import g15.reporting.reports.MerchantTransactionReport;

import java.util.ArrayList;
import java.util.List;

public class MerchantReportResponse {
    private List<MerchantTransactionReport> transactionList = new ArrayList<>();

    public MerchantReportResponse() {}

    public void addTransactionReport(MerchantTransactionReport transactionReport) {
        this.transactionList.add(transactionReport);
    }

    public List<MerchantTransactionReport> getAllTransactions() { return transactionList; }
}
