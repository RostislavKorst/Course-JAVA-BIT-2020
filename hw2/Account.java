package ru.sbt.mipt.hw2;

import java.time.LocalDateTime;
import java.util.Collection;

public class Account {
    private final long id;
    private final TransactionManager transactionManager;
    private final Entries entries;
    private static long counter = 1;

    public Account(TransactionManager transactionManager) {
        this.id = counter++;
        this.transactionManager = transactionManager;
        this.entries = new Entries();
    }

    /**
     * Withdraws money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdraw(double amount, Account beneficiary) {
        double currentBalance = currentBalance();
        if (amount > 0 && (currentBalance - amount) >= 0) {
            Transaction transaction = transactionManager.createTransaction(amount, this, beneficiary);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    /**
     * Withdraws cash money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdrawCash(double amount) {
        double currentBalance = currentBalance();
        if (amount > 0 && (currentBalance - amount) >= 0) {
            // there are no beneficiary account
            Transaction transaction = transactionManager.createTransaction(amount, this, null);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    /**
     * Adds cash money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    public boolean addCash(double amount) {
        if (amount > 0) {
            // there is no originator
            Transaction transaction = transactionManager.createTransaction(amount, null, this);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    /**
     * Adds money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    public boolean add(double amount, Account originator) {
        if (amount > 0) {
            Transaction transaction = transactionManager.createTransaction(amount, originator, this);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    public void addEntry(Entry entry) {
        entries.addEntry(entry);
    }

    public Collection<Entry> history(LocalDateTime from, LocalDateTime to) {
        return entries.betweenDates(from, to);
    }

    /**
     * Calculates balance on the accounting entries basis
     *
     * @param date
     * @return balance
     */
    public double balanceOn(LocalDateTime date) {
        double balance = 0;
        for (Entry entry : entries.upTo(date)) {
            balance += entry.getAmount();
        }
        return balance;
    }

    /**
     * Finds the last transaction of the account and rollbacks it
     */
    public void rollbackLastTransaction() {
        Transaction lastTransaction = entries.last().getTransaction();
        transactionManager.rollbackTransaction(lastTransaction);
    }

    public double currentBalance() {
        return balanceOn(LocalDateTime.now());
    }

    public Entry lastEntry() {
        return entries.last();
    }
}
