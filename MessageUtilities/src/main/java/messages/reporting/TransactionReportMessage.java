package messages.reporting;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class TransactionReportMessage {
    Transaction transactionEvent;
}
