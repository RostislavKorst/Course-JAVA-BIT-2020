package ru.sbt.mipt.hw3;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

public class AnalyticsManagerTest {
    //before
    private final int bonusAccountPercent = 0;

    @Test
    public void mostFrequentBeneficiaryOfAccount_ReturnsAccount() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        DebitCard mainDebitCard = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard beneficiary1 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard beneficiary2 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard originator1 = new DebitCard(transactionManager, bonusAccountPercent);
        mainDebitCard.addCash(2000);
        mainDebitCard.withdraw(200, beneficiary1);
        mainDebitCard.withdraw(100, beneficiary2);
        mainDebitCard.withdraw(200, beneficiary2);
        mainDebitCard.withdraw(100, beneficiary2);
        mainDebitCard.withdraw(200, beneficiary1);
        mainDebitCard.add(1000, originator1);
        mainDebitCard.add(1500, originator1);
        mainDebitCard.add(1500, originator1);
        mainDebitCard.add(1500, originator1);
        mainDebitCard.add(1000, beneficiary1);
        mainDebitCard.add(1500, beneficiary1);
        mainDebitCard.add(1500, beneficiary1);
        mainDebitCard.add(1500, beneficiary1);
        //when
        DebitCard returnedDebitCard = analyticsManager.mostFrequentBeneficiaryOfAccount(mainDebitCard);
        //then
        assertEquals(returnedDebitCard, beneficiary2);
    }

    @Test
    public void topTenExpensivePurchases_ReturnsCollectionOfTransactions() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        DebitCard mainDebitCard = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard beneficiary1 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard beneficiary2 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard beneficiary3 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard beneficiary4 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard originator1 = new DebitCard(transactionManager, bonusAccountPercent);
        mainDebitCard.addCash(100000);
        mainDebitCard.add(5000000, originator1);
        mainDebitCard.withdraw(25, beneficiary1);//10
        mainDebitCard.withdraw(49, beneficiary2);//9
        mainDebitCard.withdraw(800, beneficiary3);//3
        mainDebitCard.withdraw(50, beneficiary4);//8
        mainDebitCard.withdraw(5, beneficiary1);
        mainDebitCard.withdraw(200, beneficiary1);//6
        mainDebitCard.withdraw(100, beneficiary2);//7
        mainDebitCard.withdraw(4, beneficiary3);
        mainDebitCard.withdraw(500, beneficiary4);//5
        mainDebitCard.withdraw(3, beneficiary1);
        mainDebitCard.withdraw(2, beneficiary1);
        mainDebitCard.withdraw(1, beneficiary2);
        mainDebitCard.withdraw(700, beneficiary3);//4
        mainDebitCard.withdraw(1, beneficiary4);
        mainDebitCard.withdraw(1000, beneficiary1);//2
        mainDebitCard.withdrawCash(2000);//1
        Transaction transaction1 = new Transaction(2000, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction2 = new Transaction(1000, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction3 = new Transaction(800, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction4 = new Transaction(700, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction5 = new Transaction(500, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction6 = new Transaction(200, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction7 = new Transaction(100, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction8 = new Transaction(50, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction9 = new Transaction(49, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction10 = new Transaction(25, mainDebitCard, beneficiary1,
                true, false);
        Collection<Transaction> actualMostExpensivePurchases = Arrays.asList((new Transaction[]{transaction1,
                transaction2, transaction3, transaction4, transaction5, transaction6, transaction7, transaction8,
                transaction9, transaction10}).clone());
        //when
        Collection<Transaction> returnedMostExpensivePurchases = analyticsManager.topTenExpensivePurchases(mainDebitCard);
        //then
        assertEquals(returnedMostExpensivePurchases, actualMostExpensivePurchases);
    }

    @Test
    public void topTenExpensivePurchases_LessThanTen_ReturnsCollectionOfTransactions() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        DebitCard mainDebitCard = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard beneficiary1 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard beneficiary2 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard beneficiary3 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard beneficiary4 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard originator1 = new DebitCard(transactionManager, bonusAccountPercent);
        mainDebitCard.addCash(100000);
        mainDebitCard.add(5000000, originator1);
        mainDebitCard.withdraw(800, beneficiary3);//3
        mainDebitCard.withdraw(50, beneficiary4);//8
        mainDebitCard.withdraw(200, beneficiary1);//6
        mainDebitCard.withdraw(100, beneficiary2);//7
        mainDebitCard.withdraw(500, beneficiary4);//5
        mainDebitCard.withdraw(700, beneficiary3);//4
        mainDebitCard.withdraw(1000, beneficiary1);//2
        mainDebitCard.withdrawCash(2000);//1
        Transaction transaction1 = new Transaction(2000, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction2 = new Transaction(1000, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction3 = new Transaction(800, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction4 = new Transaction(700, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction5 = new Transaction(500, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction6 = new Transaction(200, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction7 = new Transaction(100, mainDebitCard, beneficiary1,
                true, false);
        Transaction transaction8 = new Transaction(50, mainDebitCard, beneficiary1,
                true, false);
        Collection<Transaction> actualMostExpensivePurchases = Arrays.asList((new Transaction[]{transaction1,
                transaction2, transaction3, transaction4, transaction5, transaction6, transaction7,
                transaction8}).clone());
        //when
        Collection<Transaction> returnedMostExpensivePurchases = analyticsManager.topTenExpensivePurchases(mainDebitCard);
        //then
        assertEquals(returnedMostExpensivePurchases, actualMostExpensivePurchases);
    }

    @Test
    public void overallBalanceOfAccounts() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, 0);
        DebitCard debitCard2 = new DebitCard(transactionManager, 0);
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        //when
        debitCard1.addCash(2000);
        debitCard1.withdraw(800, debitCard2);
        debitCard1.withdraw(150, debitCard2);
        debitCard2.withdraw(400, debitCard1);
        //then
        assertEquals(2000., analyticsManager.overallBalanceOfAccounts(List.of(debitCard1, debitCard2)),
                0.0001);
    }

    @Test
    public void uniqueKeysOf() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, 0);
        DebitCard debitCard2 = new DebitCard(transactionManager, 0);
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        //when
        debitCard1.addCash(2000);
        debitCard1.withdraw(800, debitCard2);
        debitCard1.withdraw(150, debitCard2);
        debitCard2.withdraw(400, debitCard1);
        //then
        assertEquals(new TreeSet<>(List.of(1450., 550.)), analyticsManager.uniqueKeysOf(List.of(debitCard1, debitCard2),
                account -> account.balanceOn(LocalDateTime.MAX)));
    }

    @Test
    public void accountsRangeFrom() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, 0);
        DebitCard debitCard2 = new DebitCard(transactionManager, 0);
        DebitCard debitCard3 = new DebitCard(transactionManager, 0);
        DebitCard debitCard4 = new DebitCard(transactionManager, 0);
        DebitCard debitCard5 = new DebitCard(transactionManager, 0);
        DebitCard debitCard6 = new DebitCard(transactionManager, 0);
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        //when
        debitCard1.addCash(2000);
        debitCard3.addCash(3000);
        debitCard4.addCash(4000);
        debitCard5.addCash(5000);
        debitCard6.addCash(6000);
        debitCard1.withdraw(800, debitCard2);
        debitCard1.withdraw(150, debitCard2);
        debitCard2.withdraw(400, debitCard1);
        //then
        assertEquals(new ArrayList<>(List.of(debitCard1, debitCard3, debitCard4, debitCard5, debitCard6)),
                analyticsManager.accountsRangeFrom(List.of(debitCard5, debitCard4, debitCard3, debitCard6, debitCard1,
                        debitCard2), debitCard1,
                Comparator.comparingDouble(account -> account.balanceOn(LocalDateTime.MAX))));
    }
}
