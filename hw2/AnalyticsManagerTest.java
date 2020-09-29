package ru.sbt.mipt.hw2;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class AnalyticsManagerTest {
    @Test
    public void mostFrequentBeneficiaryOfAccount_ReturnsAccount() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        Account mainAccount = new Account(transactionManager);
        Account beneficiary1 = new Account(transactionManager);
        Account beneficiary2 = new Account(transactionManager);
        Account originator1 = new Account(transactionManager);
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
        //when
        Account returnedAccount = analyticsManager.mostFrequentBeneficiaryOfAccount(mainAccount);
        //then
        assertEquals(returnedAccount, beneficiary2);
    }

    @Test
    public void topTenExpensivePurchases_ReturnsCollectionOfTransactions() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        Account mainAccount = new Account(transactionManager);
        Account beneficiary1 = new Account(transactionManager);
        Account beneficiary2 = new Account(transactionManager);
        Account beneficiary3 = new Account(transactionManager);
        Account beneficiary4 = new Account(transactionManager);
        Account originator1 = new Account(transactionManager);
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
        Transaction transaction1 = new Transaction(2000, mainAccount, beneficiary1,
                true, false);
        Transaction transaction2 = new Transaction(1000, mainAccount, beneficiary1,
                true, false);
        Transaction transaction3 = new Transaction(800, mainAccount, beneficiary1,
                true, false);
        Transaction transaction4 = new Transaction(700, mainAccount, beneficiary1,
                true, false);
        Transaction transaction5 = new Transaction(500, mainAccount, beneficiary1,
                true, false);
        Transaction transaction6 = new Transaction(200, mainAccount, beneficiary1,
                true, false);
        Transaction transaction7 = new Transaction(100, mainAccount, beneficiary1,
                true, false);
        Transaction transaction8 = new Transaction(50, mainAccount, beneficiary1,
                true, false);
        Transaction transaction9 = new Transaction(49, mainAccount, beneficiary1,
                true, false);
        Transaction transaction10 = new Transaction(25, mainAccount, beneficiary1,
                true, false);
        Collection<Transaction> actualMostExpensivePurchases = Arrays.asList((new Transaction[]{transaction1,
                transaction2, transaction3, transaction4, transaction5, transaction6, transaction7, transaction8,
                transaction9, transaction10}).clone());
        //when
        Collection<Transaction> returnedMostExpensivePurchases = analyticsManager.topTenExpensivePurchases(mainAccount);
        //then
        assertEquals(returnedMostExpensivePurchases, actualMostExpensivePurchases);
    }

    @Test
    public void topTenExpensivePurchases_LessThanTen_ReturnsCollectionOfTransactions() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        Account mainAccount = new Account(transactionManager);
        Account beneficiary1 = new Account(transactionManager);
        Account beneficiary2 = new Account(transactionManager);
        Account beneficiary3 = new Account(transactionManager);
        Account beneficiary4 = new Account(transactionManager);
        Account originator1 = new Account(transactionManager);
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
        Transaction transaction1 = new Transaction(2000, mainAccount, beneficiary1,
                true, false);
        Transaction transaction2 = new Transaction(1000, mainAccount, beneficiary1,
                true, false);
        Transaction transaction3 = new Transaction(800, mainAccount, beneficiary1,
                true, false);
        Transaction transaction4 = new Transaction(700, mainAccount, beneficiary1,
                true, false);
        Transaction transaction5 = new Transaction(500, mainAccount, beneficiary1,
                true, false);
        Transaction transaction6 = new Transaction(200, mainAccount, beneficiary1,
                true, false);
        Transaction transaction7 = new Transaction(100, mainAccount, beneficiary1,
                true, false);
        Transaction transaction8 = new Transaction(50, mainAccount, beneficiary1,
                true, false);
        Collection<Transaction> actualMostExpensivePurchases = Arrays.asList((new Transaction[]{transaction1,
                transaction2, transaction3, transaction4, transaction5, transaction6, transaction7,
                transaction8}).clone());
        //when
        Collection<Transaction> returnedMostExpensivePurchases = analyticsManager.topTenExpensivePurchases(mainAccount);
        //then
        assertEquals(returnedMostExpensivePurchases, actualMostExpensivePurchases);
    }
}
