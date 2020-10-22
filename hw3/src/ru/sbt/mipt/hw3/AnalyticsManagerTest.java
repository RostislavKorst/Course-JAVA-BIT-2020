package ru.sbt.mipt.hw3;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

public class AnalyticsManagerTest {
    //before
    private final int bonusAccountPercent = 0;

    private TransactionManager transactionManager = new TransactionManager();
    private AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
    private DebitCard mainAccount = new DebitCard(transactionManager, bonusAccountPercent);
    private DebitCard beneficiary1 = new DebitCard(transactionManager, bonusAccountPercent);
    private DebitCard beneficiary2 = new DebitCard(transactionManager, bonusAccountPercent);
    private DebitCard beneficiary3 = new DebitCard(transactionManager, bonusAccountPercent);
    private DebitCard beneficiary4 = new DebitCard(transactionManager, bonusAccountPercent);
    private DebitCard originator1;

    @Before
    public void createAccounts() {
        transactionManager = new TransactionManager();
        analyticsManager = new AnalyticsManager(transactionManager);
        mainAccount = new DebitCard(transactionManager, bonusAccountPercent);
        beneficiary1 = new DebitCard(transactionManager, bonusAccountPercent);
        beneficiary2 = new DebitCard(transactionManager, bonusAccountPercent);
        beneficiary3 = new DebitCard(transactionManager, bonusAccountPercent);
        beneficiary4 = new DebitCard(transactionManager, bonusAccountPercent);
        originator1 = new DebitCard(transactionManager, bonusAccountPercent);
    }

    @Test
    public void mostFrequentBeneficiaryOfAccount_ReturnsAccount() {
        //given
        executeSomeTransactionsWithMostFrequentBeneficiary2();
        //when
        Account returnedAccount = analyticsManager.mostFrequentBeneficiaryOfAccount(mainAccount);
        //then
        assertEquals(returnedAccount, beneficiary2);
    }

    private void executeSomeTransactionsWithMostFrequentBeneficiary2() {
        mainAccount.addCash(2000);
        mainAccount.withdraw(200, beneficiary1);
        mainAccount.withdraw(100, beneficiary2);
        mainAccount.withdraw(200, beneficiary2);
        mainAccount.withdraw(100, beneficiary2);
        mainAccount.withdraw(200, beneficiary1);
        mainAccount.add(1000, originator1);
        mainAccount.add(1500, originator1);
        mainAccount.add(1500, originator1);
        mainAccount.add(1500, originator1);
        mainAccount.add(1000, beneficiary1);
        mainAccount.add(1500, beneficiary1);
        mainAccount.add(1500, beneficiary1);
        mainAccount.add(1500, beneficiary1);
    }

    @Test
    public void topTenExpensivePurchases_ReturnsCollectionOfTransactions() throws NoSuchFieldException,
            IllegalAccessException {
        //given
        startIdFromOne();
        executeSomeTransactionsMoreThanTen();
        Collection<Transaction> expectedTransactions = getExpectedTransactions();
        //when
        Collection<Transaction> returnedMostExpensivePurchases = analyticsManager.topTenExpensivePurchases(mainAccount);
        //then
        assertEquals(expectedTransactions, returnedMostExpensivePurchases);
    }

    private Collection<Transaction> getExpectedTransactions() {
        Transaction transaction1 = new Transaction(18, 2000, mainAccount, beneficiary1,
                true, false);
        Transaction transaction2 = new Transaction(17, 1000, mainAccount, beneficiary1,
                true, false);
        Transaction transaction3 = new Transaction(5, 800, mainAccount, beneficiary1,
                true, false);
        Transaction transaction4 = new Transaction(15, 700, mainAccount, beneficiary1,
                true, false);
        Transaction transaction5 = new Transaction(11, 500, mainAccount, beneficiary1,
                true, false);
        Transaction transaction6 = new Transaction(8, 200, mainAccount, beneficiary1,
                true, false);
        Transaction transaction7 = new Transaction(9, 100, mainAccount, beneficiary1,
                true, false);
        Transaction transaction8 = new Transaction(6, 50, mainAccount, beneficiary1,
                true, false);
        Transaction transaction9 = new Transaction(4, 49, mainAccount, beneficiary1,
                true, false);
        Transaction transaction10 = new Transaction(3, 25, mainAccount, beneficiary1,
                true, false);
        return Arrays.asList((new Transaction[]{transaction1,
                transaction2, transaction3, transaction4, transaction5, transaction6, transaction7, transaction8,
                transaction9, transaction10}).clone());
    }

    private void executeSomeTransactionsMoreThanTen() {
        mainAccount.addCash(100000);
        mainAccount.add(5000000, originator1);
        mainAccount.withdraw(25, beneficiary1);//10
        mainAccount.withdraw(49, beneficiary2);//9
        mainAccount.withdraw(800, beneficiary3);//3
        mainAccount.withdraw(50, beneficiary4);//8
        mainAccount.withdraw(5, beneficiary1);
        mainAccount.withdraw(200, beneficiary1);//6
        mainAccount.withdraw(100, beneficiary2);//7
        mainAccount.withdraw(4, beneficiary3);
        mainAccount.withdraw(500, beneficiary4);//5
        mainAccount.withdraw(3, beneficiary1);
        mainAccount.withdraw(2, beneficiary1);
        mainAccount.withdraw(1, beneficiary2);
        mainAccount.withdraw(700, beneficiary3);//4
        mainAccount.withdraw(1, beneficiary4);
        mainAccount.withdraw(1000, beneficiary1);//2
        mainAccount.withdrawCash(2000);//1
    }

    @Test
    public void topTenExpensivePurchases_LessThanTen_ReturnsCollectionOfTransactions() throws NoSuchFieldException,
            IllegalAccessException {
        //given
        startIdFromOne();
        executeSomeTransactionsLessThanTen();
        Collection<Transaction> expectedTransactions = getExpectedTransactionsLessThanTen();
        //when
        Collection<Transaction> returnedMostExpensivePurchases = analyticsManager.topTenExpensivePurchases(mainAccount);
        //then
        assertEquals(expectedTransactions, returnedMostExpensivePurchases);
    }

    private Collection<Transaction> getExpectedTransactionsLessThanTen() {
        Transaction transaction1 = new Transaction(10, 2000, mainAccount, beneficiary1,
                true, false);
        Transaction transaction2 = new Transaction(9, 1000, mainAccount, beneficiary1,
                true, false);
        Transaction transaction3 = new Transaction(3, 800, mainAccount, beneficiary1,
                true, false);
        Transaction transaction4 = new Transaction(8, 700, mainAccount, beneficiary1,
                true, false);
        Transaction transaction5 = new Transaction(7, 500, mainAccount, beneficiary1,
                true, false);
        Transaction transaction6 = new Transaction(5, 200, mainAccount, beneficiary1,
                true, false);
        Transaction transaction7 = new Transaction(6, 100, mainAccount, beneficiary1,
                true, false);
        Transaction transaction8 = new Transaction(4, 50, mainAccount, beneficiary1,
                true, false);
        return Arrays.asList((new Transaction[]{transaction1,
                transaction2, transaction3, transaction4, transaction5, transaction6, transaction7,
                transaction8}).clone());
    }

    private void executeSomeTransactionsLessThanTen() {
        mainAccount.addCash(100000);
        mainAccount.add(5000000, originator1);
        mainAccount.withdraw(800, beneficiary3);//3
        mainAccount.withdraw(50, beneficiary4);//8
        mainAccount.withdraw(200, beneficiary1);//6
        mainAccount.withdraw(100, beneficiary2);//7
        mainAccount.withdraw(500, beneficiary4);//5
        mainAccount.withdraw(700, beneficiary3);//4
        mainAccount.withdraw(1000, beneficiary1);//2
        mainAccount.withdrawCash(2000);//1
    }

    private void startIdFromOne() throws NoSuchFieldException, IllegalAccessException {
        Transaction transaction = new Transaction(0, 2000, mainAccount, beneficiary1,
                true, false);
        Field field = Transaction.class.getDeclaredField("counter");
        field.setAccessible(true);
        field.setLong(transaction, 1);
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
                1e-4);
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
