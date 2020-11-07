package ru.sbt.mipt.hw2;

import java.util.*;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
        Collection<Transaction> elements = transactionManager.findAllTransactionsByAccount(account);
        Map<Account, Integer> frequencyMap = new HashMap<>();
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
        Queue<Transaction> queue = new PriorityQueue<>(Comparator.comparingDouble(Transaction::getAmount).reversed());
        queue.addAll(elements);
        List<Transaction> topTenTransactions = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Transaction transaction = queue.poll();
            if (transaction != null) {
                topTenTransactions.add(transaction);
            } else {
                break;
            }
        }
        return topTenTransactions;
    }
}
