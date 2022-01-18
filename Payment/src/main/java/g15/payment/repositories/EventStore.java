package g15.payment.repositories;

import messages.reporting.Transaction;
import implementation.IMessagingClient;
import messages.reporting.TransactionReportMessage;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Roar Nind Steffensen
 */
public class EventStore {
    private final IMessagingClient client;

    public EventStore(IMessagingClient client){
        this.client = client;
    }

    private final List<Transaction> events = new LinkedList<>();

    public void add(Transaction event){
        events.add(event);
        this.client.send(new TransactionReportMessage(event));
    }

    public List<Transaction> get() {
        return events;
    }
}
