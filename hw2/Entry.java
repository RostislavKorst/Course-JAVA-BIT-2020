package ru.sbt.mipt.hw2;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The record of allocating the amount to the account
 * Amount can be either positive or negative depending on originator or beneficiary
 */
public class Entry implements Comparable<Entry> {
    private final Account account;
    private final Transaction transaction;
    private final double amount;
    private final LocalDateTime time;

    public Entry(Account account, Transaction transaction, double amount, LocalDateTime time) {
        this.account = account;
        this.transaction = transaction;
        this.amount = amount;
        this.time = time;
    }

    Transaction getTransaction() {
        return transaction;
    }

    double getAmount() {
        return amount;
    }

    LocalDateTime getTime() {
        return time;
    }

    @Override
    public int compareTo(Entry o) {
        if (this.time.compareTo(o.time) == 0) {
            return (this.transaction.getId() > o.transaction.getId()) ? -1 : 1; //the lower id - the bigger entry
        }
        return this.time.compareTo(o.time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Double.compare(entry.amount, amount) == 0 &&
                account.equals(entry.account) &&
                transaction.equals(entry.transaction) &&
                time.equals(entry.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, transaction, amount, time);
    }
}
