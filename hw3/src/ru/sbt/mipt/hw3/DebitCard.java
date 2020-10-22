package ru.sbt.mipt.hw3;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

public class DebitCard implements Account {
    private final long id;
    private final TransactionManager transactionManager;
    private final Entries entries;
    private static long counter = 1;
    private final double bonusAccountPercent;
    private final BonusAccount bonusAccount;

    public DebitCard(TransactionManager transactionManager, int bonusAccountPercent) {
        this.bonusAccountPercent = bonusAccountPercent;
        this.id = counter++;
        this.transactionManager = transactionManager;
        this.entries = new Entries();
        bonusAccount = new BonusAccount(bonusAccountPercent);
    }

    private void addPointsToBonusAccount(double amountOfTransaction, Transaction transaction) {
        Entry entry =  new Entry(bonusAccount, transaction,
                amountOfTransaction * bonusAccountPercent / 100., LocalDateTime.now());
        bonusAccount.addEntry(entry);
    }

    /**
     * Withdraws money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdraw(double amount, DebitCard beneficiary) {
        double currentBalance = currentBalance();
        if (amount > 0 && (currentBalance - amount) >= 0) {
            Transaction transaction = transactionManager.createTransaction(amount, this, beneficiary);
            transactionManager.executeTransaction(transaction);
            addPointsToBonusAccount(amount, transaction);
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
    @Deprecated
    public boolean add(double amount, DebitCard originator) {
        if (amount > 0) {
            Transaction transaction = transactionManager.createTransaction(amount, originator, this);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    /**
     * Finds the last transaction of the account and rollbacks it
     */
    public void rollbackLastTransaction() {
        Transaction lastTransaction = entries.last().getTransaction();
        transactionManager.rollbackTransaction(lastTransaction);
    }

    @Override
    public double currentBalance() {
        return balanceOn(LocalDateTime.now());
    }

    public Entry lastEntry() {
        return entries.last();
    }

    public Collection<Entry> history(LocalDateTime from, LocalDateTime to) throws IllegalArgumentException {
        return entries.betweenDates(from, to);
    }

    @Override
    public void addEntry(Entry entry) {
        entries.addEntry(entry);
    }

    /**
     * Calculates balance on the accounting entries basis
     *
     * @param date
     * @return balance
     */
    @Override
    public double balanceOn(LocalDateTime date) {
        double balance = 0;
        for (Entry entry : entries.upTo(date)) {
            balance += entry.getAmount();
        }
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DebitCard debitCard = (DebitCard) o;
        return id == debitCard.id &&
                Double.compare(debitCard.bonusAccountPercent, bonusAccountPercent) == 0 &&
                Objects.equals(transactionManager, debitCard.transactionManager) &&
                Objects.equals(entries, debitCard.entries) &&
                Objects.equals(bonusAccount, debitCard.bonusAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionManager, entries, bonusAccountPercent, bonusAccount);
    }
}
