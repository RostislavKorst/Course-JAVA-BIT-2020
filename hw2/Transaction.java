package ru.sbt.mipt.hw2;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction implements Comparable<Transaction> {
    private final long id;
    private final double amount;
    private final Account originator;
    private final Account beneficiary;
    private final boolean executed;
    private final boolean rolledBack;
    private static long counter = 1;

    public Transaction(double amount, Account originator, Account beneficiary, boolean executed, boolean rolledBack) {
        this.id = counter++;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
        this.executed = executed;
        this.rolledBack = rolledBack;
    }

    public Transaction(long id, double amount, Account originator, Account beneficiary, boolean executed,
                       boolean rolledBack) {
        this.id = id;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
        this.executed = executed;
        this.rolledBack = rolledBack;
    }

    /**
     * Adding entries to both accounts
     *
     * @throws IllegalStateException when was already executed
     */
    public Transaction execute() {
        try {
            if (executed) {
                throw new IllegalStateException("Transaction was already executed");
            }
        } catch (IllegalStateException exception) {
            throw new RuntimeException(exception);
        }
        Transaction transaction = new Transaction(id, amount, originator, beneficiary, true, rolledBack);
        if (beneficiary != null) {
            Entry beneficiaryEntry = new Entry(beneficiary, transaction, amount, LocalDateTime.now());
            beneficiary.addEntry(beneficiaryEntry);
        }
        if (originator != null) {
            Entry originatorEntry = new Entry(originator, transaction, -amount, LocalDateTime.now());
            originator.addEntry(originatorEntry);
        }
        return transaction;
    }

    /**
     * Removes all entries of current transaction from originator and beneficiary
     *
     * @throws IllegalStateException when was already rolled back
     */
    public Transaction rollback() {
        try {
            if (rolledBack) {
                throw new IllegalStateException("Transaction was already executed");
            }
        } catch (IllegalStateException exception) {
            throw new RuntimeException(exception);
        }
        Transaction transaction = new Transaction(amount, originator, beneficiary, true, true);
        if (beneficiary != null) {
            Entry compensationEntry = new Entry(beneficiary, transaction, -amount, LocalDateTime.now());
            beneficiary.addEntry(compensationEntry);
        }
        if (originator != null) {
            Entry compensationEntry = new Entry(originator, transaction, amount, LocalDateTime.now());
            originator.addEntry(compensationEntry);
        }
        return transaction;
    }

    Account getOriginator() {
        return originator;
    }

    Account getBeneficiary() {
        return beneficiary;
    }

    @Override
    public int compareTo(Transaction otherTransaction) {
        return Long.compare(this.id, otherTransaction.id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Transaction that = (Transaction) object;
        return that.id == id && that.executed == executed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, executed);
    }

    long getId() {
        return id;
    }

    double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", executed=" + executed +
                ", amount=" + amount +
                '}';
    }
}
