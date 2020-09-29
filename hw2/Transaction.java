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

    /**
     * Adding entries to both accounts
     *
     * @throws IllegalStateException when was already executed
     */
    public Transaction execute() throws IllegalStateException {
        Transaction transaction = new Transaction(amount, originator, beneficiary, true, rolledBack);
        if (beneficiary != null) {
            Entry beneficiaryEntry = new Entry(beneficiary, transaction, amount, LocalDateTime.now());
            beneficiary.addEntry(beneficiaryEntry);
        }
        if (originator != null) {
            Entry originatorEntry = new Entry(originator, transaction, -amount, LocalDateTime.now());
            originator.addEntry(originatorEntry);
        }
        if (executed) {
            throw new IllegalStateException("Transaction was already executed");
        }
        return transaction;
    }

    /**
     * Removes all entries of current transaction from originator and beneficiary
     *
     * @throws IllegalStateException when was already rolled back
     */
    public Transaction rollback() throws IllegalStateException {
        Transaction transaction = new Transaction(amount, originator, beneficiary, true, true);
        if (beneficiary != null) {
            Entry compensationEntry = new Entry(beneficiary, transaction, -amount, LocalDateTime.now());
            beneficiary.addEntry(compensationEntry);
        }
        if (originator != null) {
            Entry compensationEntry = new Entry(originator, transaction, amount, LocalDateTime.now());
            originator.addEntry(compensationEntry);
        }
        if (rolledBack) {
            throw new IllegalStateException("Transaction was already executed");
        }
        return transaction;
    }


    Account getOriginator() {
        return originator;
    }

    Account getBeneficiary() {
        return beneficiary;
    }

    long getId() {
        return id;
    }

    boolean isExecuted() {
        return executed;
    }

    @Override
    public int compareTo(Transaction o) {
        if (this.amount == o.amount) return 0;
        return (this.amount > o.amount) ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 && that.executed == executed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, executed);
    }
}
