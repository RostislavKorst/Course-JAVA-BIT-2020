package resources;

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
    private final HashMap<DebitCard, ArrayList<Transaction>> accountsHistory = new HashMap<>();

    public Transaction createTransaction(double amount, DebitCard originator, DebitCard beneficiary) {
        return new Transaction(amount, originator, beneficiary, false, false);
    }

    public Collection<Transaction> findAllTransactionsByAccount(DebitCard debitCard) {
        return accountsHistory.get(debitCard);
    }

    Collection<Transaction> findAllPurchasesByAccount(DebitCard debitCard) {
        //find all transactions where account was originator
        Collection<Transaction> allTransactionsByAccount = findAllTransactionsByAccount(debitCard);
        Collection<Transaction> allPurchases = new ArrayList<>();
        for (Transaction tr : allTransactionsByAccount) {
            if (tr.getOriginator() == debitCard) {
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
            listOfTransactionsOriginator = new ArrayList<>();
        }
        listOfTransactionsOriginator.add(transaction);
        accountsHistory.put(transaction.getOriginator(), listOfTransactionsOriginator);
        ArrayList<Transaction> listOfTransactionsBeneficiary = accountsHistory.get(transaction.getBeneficiary());
        if (listOfTransactionsBeneficiary == null) {
            listOfTransactionsBeneficiary = new ArrayList<>();
        }
        listOfTransactionsBeneficiary.add(transaction);
        accountsHistory.put(transaction.getBeneficiary(), listOfTransactionsBeneficiary);
    }
}
