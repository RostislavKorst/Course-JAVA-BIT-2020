package ru.sbt.mipt.hw3;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.function.Function.identity;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.*;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(DebitCard account) {
        Collection<Transaction> transactionsByAccount = transactionManager.findAllTransactionsByAccount(account);
        return transactionsByAccount.stream()
                .map(Transaction::getBeneficiary)
                .filter(beneficiary -> !beneficiary.equals(account))
                .collect(groupingBy(identity(), counting()))
                .entrySet().stream()
                .max(comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Collection<Transaction> topTenExpensivePurchases(DebitCard account) {
        Collection<Transaction> transactionsByAccount = transactionManager.findAllPurchasesByAccount(account);
        return transactionsByAccount.stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .limit(10)
                .collect(toList());
    }

    public double overallBalanceOfAccounts(List<? extends Account> accounts) {
        return accounts.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Account::currentBalance)
                .sum();
    }

    public <T extends Comparable<? super T>> Set<T> uniqueKeysOf(List<? extends Account> accounts,
                                                                 KeyExtractor<? super Account, ? extends T> extractor) {
        return accounts.stream()
                .map(extractor::extract)
                .collect(toSet());
    }

    public List<Account> accountsRangeFrom(List<? extends Account> accounts, Account minAccount,
                                           Comparator<? super Account> comparator) {
        return accounts.stream()
                .filter(Objects::nonNull)
                .filter(account -> comparator.compare(minAccount, account) <= 0)
                .sorted(comparator)
                .collect(toList());
    }

    public Optional<Entry> maxExpenseAmountEntryWithinInterval(List<Account> accounts, LocalDateTime from,
                                                               LocalDateTime to) {
        return accounts.stream()
                .filter(Objects::nonNull)
                .map(account -> (DebitCard) account)
                .map(account -> account.history(from, to))
                .flatMap(Collection::stream)
                .filter(entry -> entry.getAmount() < 0)
                .min(Comparator.comparing(Entry::getAmount));
    }
}
