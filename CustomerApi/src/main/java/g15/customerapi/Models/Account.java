package g15.customerapi.Models;

import java.util.Objects;
import java.math.BigDecimal;

public class Account {
    public String id;
    public String bankAccountNr;
    public String firstName;
    public String lastName;
    public String cpr;
    public BigDecimal balance;

    public Account() {}

    public Account(String id, String bankAccountNr, String firstName, String lastName, String cpr) {
        this.id = id;
        this.bankAccountNr = bankAccountNr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpr = cpr;
    }

    public String getId() {
        return id;
    }

    public String getBankAccountNr() {
        return bankAccountNr;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCpr() {
        return cpr;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Account account = (Account) object;
        return getId().equals(account.getId()) && getBankAccountNr().equals(account.getBankAccountNr()) && getFirstName().equals(account.getFirstName()) && getLastName().equals(account.getLastName()) && getCpr().equals(account.getCpr());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getBankAccountNr(), getFirstName(), getLastName(), getCpr());
    }
}