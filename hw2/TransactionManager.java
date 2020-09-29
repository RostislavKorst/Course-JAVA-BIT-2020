package ru.sbt.mipt.hw2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
    private final HashMap<Account, ArrayList<Transaction>> accountsHistory = new HashMap<>();

    public Transaction createTransaction(double amount, Account originator, Account beneficiary) {
        return new Transaction(amount, originator, beneficiary, false, false);
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        return accountsHistory.get(account);
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
        Transaction new_transaction = transaction.rollback();
        addToAccountsHistory(new_transaction);
    }

    public void executeTransaction(Transaction transaction) {
        Transaction new_transaction = transaction.execute();
        addToAccountsHistory(new_transaction);
    }

    private void addToAccountsHistory(Transaction transaction) {
        ArrayList<Transaction> listOfTransactionsOriginator = accountsHistory.get(transaction.getOriginator());
        if (listOfTransactionsOriginator == null) {
            listOfTransactionsOriginator = new ArrayList<Transaction>();
        }
        listOfTransactionsOriginator.add(transaction);
        accountsHistory.put(transaction.getOriginator(), listOfTransactionsOriginator);
        ArrayList<Transaction> listOfTransactionsBeneficiary = accountsHistory.get(transaction.getBeneficiary());
        if (listOfTransactionsBeneficiary == null) {
            listOfTransactionsBeneficiary = new ArrayList<Transaction>();
        }
        listOfTransactionsBeneficiary.add(transaction);
        accountsHistory.put(transaction.getBeneficiary(), listOfTransactionsBeneficiary);
    }
}
