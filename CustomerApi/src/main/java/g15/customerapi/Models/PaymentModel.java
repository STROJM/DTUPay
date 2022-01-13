package g15.customerapi.Models;

import java.util.Objects;

public class PaymentModel {
    public float amount;
    public String creditor;
    public String debtor;
    public String description;

    public PaymentModel() {
    }

    public PaymentModel(float amount, String creditor, String debtor, String description) {
        this.amount = amount;
        this.creditor = creditor;
        this.debtor = debtor;
        this.description = description;
    }


    // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PaymentModel)) {
            return false;
        }
        PaymentModel other = (PaymentModel) o;

        return other.amount == this.amount &&
                other.creditor.equals(this.creditor) &&
                other.debtor.equals(this.debtor) &&
                this.description.equals(other.description);
    }

    @Override
    public int hashCode() {
        // If we override equals, we also need hasCode(). Java spec defines a contract between them as:
        // if a == b then a.hashCode() == b.hashCode().
        return Objects.hash(amount, creditor, debtor, description);
    }
}
