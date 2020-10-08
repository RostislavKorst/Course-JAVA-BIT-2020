package ru.sbt.mipt.hw3;

import java.time.LocalDateTime;
import java.util.*;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public DebitCard mostFrequentBeneficiaryOfAccount(DebitCard debitCard) {
        Collection<Transaction> elements = transactionManager.findAllTransactionsByAccount(debitCard);
        HashMap<DebitCard, Integer> frequencyMap = new HashMap<>();
        for (Transaction element : elements) {
            DebitCard beneficiary = element.getBeneficiary();
            if (beneficiary != null && beneficiary != debitCard) {
                Integer frequency = frequencyMap.get(beneficiary);
                frequencyMap.put(beneficiary, frequency == null ? 1 : frequency + 1);
            }
        }
        return Collections.max(frequencyMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public Collection<Transaction> topTenExpensivePurchases(DebitCard debitCard) {
        Collection<Transaction> elements = transactionManager.findAllPurchasesByAccount(debitCard);
        PriorityQueue<Transaction> queue = new PriorityQueue<>(Collections.reverseOrder());
        queue.addAll(elements);
        int i = 0;
        ArrayList<Transaction> toReturn = new ArrayList<>(10);
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

    public double overallBalanceOfAccounts(List<? extends Account> accounts) {
        double sumBalance = 0;
        for (Account account : accounts) {
            sumBalance += account.balanceOn(LocalDateTime.MAX);
        }
        return sumBalance;
    }

    public <T extends Comparable<? super T>> Set<T> uniqueKeysOf(List<? extends Account> accounts,
                                                                 KeyExtractor<? super Account, ? extends T> extractor) {
        TreeSet<T> uniqueKeys = new TreeSet<>();
        accounts.forEach(account -> uniqueKeys.add(extractor.extract(account)));
        return uniqueKeys;
    }

    public List<Account> accountsRangeFrom(List<? extends Account> accounts, Account minAccount,
                                           Comparator<? super Account> comparator) {
        ArrayList<Account> accountsCopy = new ArrayList<>(accounts);
        accountsCopy.sort(comparator);
        return accountsCopy.subList(accountsCopy.indexOf(minAccount), accountsCopy.size());
    }
}
