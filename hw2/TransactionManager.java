package ru.sbt.mipt.hw2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Manages all transactions within the application
 */
public class TransactionManager {
    /**
     * Creates and stores transactions
     *
     * @param amount
     * @param originator
     * @param beneficiary
     * @return created Transaction
     */
    public Transaction createTransaction(double amount, Account originator, Account beneficiary) {
        return new Transaction(amount, originator, beneficiary, false, false);
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        ArrayList<Transaction> toReturn = new ArrayList<Transaction>();
        for (Entry entry : account.history(LocalDateTime.MIN, LocalDateTime.MAX)) {
            toReturn.add(entry.getTransaction());
        }
        return toReturn;
    }

    Collection<Transaction> findAllPurchasesByAccount(Account account) {
        //find all transactions where account was originator
        Collection<Transaction> allTransactionsByAccount = findAllTransactionsByAccount(account);
        Collection<Transaction> allPurchases = new ArrayList<Transaction>();
        for (Transaction tr : allTransactionsByAccount) {
            if (tr.getOriginator() == account) {
                allPurchases.add(tr);
            }
        }
        return allPurchases;
    }

    public void rollbackTransaction(Transaction transaction) {
        transaction.rollback();
    }

    public void executeTransaction(Transaction transaction) {
        transaction.execute();
    }
}
