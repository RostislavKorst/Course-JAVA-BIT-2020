package ru.sbt.mipt.hw2;

import java.util.*;

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
    private final Map<Account, List<Transaction>> accountsHistory = new HashMap<>();

    public Transaction createTransaction(double amount, Account originator, Account beneficiary) {
        return new Transaction(amount, originator, beneficiary, false, false);
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        return accountsHistory.get(account);
    }

    Collection<Transaction> findAllPurchasesByAccount(Account account) {
        //find all transactions where account was originator
        Collection<Transaction> allTransactionsByAccount = findAllTransactionsByAccount(account);
        Collection<Transaction> allPurchases = new ArrayList<>();
        for (Transaction tr : allTransactionsByAccount) {
            if (tr.getOriginator() == account) {
                allPurchases.add(tr);
            }
        }
        return allPurchases;
    }

    public void rollbackTransaction(Transaction transaction) {
        Transaction newTransaction = transaction.rollback();
        addToAccountsHistory(newTransaction);
    }

    public void executeTransaction(Transaction transaction) {
        Transaction newTransaction = transaction.execute();
        addToAccountsHistory(newTransaction);
    }

    private void addToAccountsHistory(Transaction transaction) {
        List<Transaction> listOfTransactionsOriginator = accountsHistory.get(transaction.getOriginator());
        if (listOfTransactionsOriginator == null) {
            listOfTransactionsOriginator = new ArrayList<>();
        }
        listOfTransactionsOriginator.add(transaction);
        accountsHistory.put(transaction.getOriginator(), listOfTransactionsOriginator);
        List<Transaction> listOfTransactionsBeneficiary = accountsHistory.get(transaction.getBeneficiary());
        if (listOfTransactionsBeneficiary == null) {
            listOfTransactionsBeneficiary = new ArrayList<>();
        }
        listOfTransactionsBeneficiary.add(transaction);
        accountsHistory.put(transaction.getBeneficiary(), listOfTransactionsBeneficiary);
    }
}
