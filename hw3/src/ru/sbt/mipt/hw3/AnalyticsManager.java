package ru.sbt.mipt.hw3;

import java.util.*;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(DebitCard account) {
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

    public Collection<Transaction> topTenExpensivePurchases(DebitCard account) {
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

    public double overallBalanceOfAccounts(List<? extends Account> accounts) {
        double sumBalance = 0;
        for (Account account : accounts) {
            sumBalance += account.currentBalance();
        }
        return sumBalance;
    }

    public <T extends Comparable<? super T>> Set<T> uniqueKeysOf(List<? extends Account> accounts,
                                                                 KeyExtractor<? super Account, ? extends T> extractor) {
        Set<T> uniqueKeys = new TreeSet<>();
        accounts.forEach(account -> uniqueKeys.add(extractor.extract(account)));
        return uniqueKeys;
    }

    public List<Account> accountsRangeFrom(List<? extends Account> accounts, Account minAccount,
                                           Comparator<? super Account> comparator) {
        List<Account> accountsCopy = new ArrayList<>(accounts);
        accountsCopy.sort(comparator);
        return accountsCopy.subList(accountsCopy.indexOf(minAccount), accountsCopy.size());
    }
}
