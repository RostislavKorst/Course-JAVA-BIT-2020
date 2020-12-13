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
    private final Map<DebitCard, List<Transaction>> accountsHistory = new HashMap<>();

    public Transaction createTransaction(double amount, DebitCard originator, DebitCard beneficiary) {
        return new Transaction(amount, originator, beneficiary, false, false);
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account debitCard) {
        return accountsHistory.get(debitCard);
    }

    Collection<Transaction> findAllPurchasesByAccount(Account debitCard) {
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
