package g15.reporting.messages;

import java.io.Serializable;

// TODO: equals and hashCode for all message objects

public class ReportResponse implements Serializable {
    boolean completed;
    String errorMessage;


    public ReportResponse(boolean completed, String errorMessage) {
        this.completed = completed;
        this.errorMessage = errorMessage;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
