package g15.reporting.messages;

import java.io.Serializable;
import java.util.Objects;

public class ReportStoreResponse implements Serializable {
    boolean completed;
    String errorMessage;


    public ReportStoreResponse(boolean completed, String errorMessage) {
        this.completed = completed;
        this.errorMessage = errorMessage;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportStoreResponse that = (ReportStoreResponse) o;
        return completed == that.completed && errorMessage.equals(that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(completed, errorMessage);
    }
}
