package ru.sbt.mipt.hw2;

import java.time.LocalDateTime;
import java.util.*;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
        Collection<Transaction> elements = transactionManager.findAllTransactionsByAccount(account);
        HashMap<Account, Integer> frequencyMap = new HashMap<Account, Integer>();
        for (Transaction element : elements) {
            Account beneficiary = element.getBeneficiary();
            if (beneficiary != null && beneficiary != account) {
                Integer frequency = frequencyMap.get(beneficiary);
                frequencyMap.put(beneficiary, frequency == null ? 1 : frequency + 1);
            }
        }
        return Collections.max(frequencyMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) {
        Collection<Transaction> elements = transactionManager.findAllPurchasesByAccount(account);
        PriorityQueue<Transaction> queue = new PriorityQueue<Transaction>(Collections.reverseOrder());
        queue.addAll(elements);
        int i = 0;
        ArrayList<Transaction> toReturn = new ArrayList<Transaction>(10);
        while (i++ < 10) {
            Transaction transaction = queue.poll();
            if (transaction != null) {
                toReturn.add(transaction);
            } else {
                break;
            }
        }
        return toReturn;
    }
}
