package ru.sbt.mipt.hw2;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class TransactionManagerTest {
    @Test
    public void createTransaction_ReturnsTransaction() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(transactionManager);
        Account beneficiary = new Account(transactionManager);
        //when
        Transaction expectedTransaction = new Transaction(2000, originator, beneficiary, false, false);
        Transaction actualTransaction = transactionManager.createTransaction(2000, originator, beneficiary);
        //then
        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void findAllTransactionsByAccount_ReturnsCollectionOfTransactions() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(transactionManager);
        Account beneficiary = new Account(transactionManager);
        //when
        beneficiary.add(200, originator);
        beneficiary.addCash(300);
        beneficiary.withdrawCash(45);
        Transaction transaction1 = new Transaction(200, originator, beneficiary, true, false);
        Transaction transaction2 = new Transaction(300, null, beneficiary, true, false);
        Transaction transaction3 = new Transaction(45, beneficiary, null, true, false);
        Collection<Transaction> expectedListOfTransactions = Arrays.asList(transaction1, transaction2, transaction3);
        Collection<Transaction> actualListOfTransactions = transactionManager.findAllTransactionsByAccount(beneficiary);
        //then
        assertEquals(expectedListOfTransactions, actualListOfTransactions);
    }

    @Test
    public void rollbackTransaction_AddsToTheHistory() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(transactionManager);
        Account beneficiary = new Account(transactionManager);
        double amount = 2000;
        Transaction transaction = new Transaction(amount, originator, beneficiary, false, false);
        Transaction transactionTrueExecuted = new Transaction(amount, originator, beneficiary,
                true, false);
        Transaction transactionTrueRolledBack = new Transaction(amount, originator, beneficiary,
                true, true);
        //when
        transactionManager.executeTransaction(transaction);
        transactionManager.rollbackTransaction(transaction);
        Collection<Entry> originatorHistory = originator.history(LocalDateTime.MIN, LocalDateTime.MAX);
        Collection<Entry> expectedOriginatorHistory = new ArrayList<>();
        expectedOriginatorHistory.add(new Entry(originator, transactionTrueExecuted, -amount,
                ((ArrayList<Entry>)originatorHistory).get(0).getTime()));
        expectedOriginatorHistory.add(new Entry(originator, transactionTrueRolledBack, amount,
                ((ArrayList<Entry>)originatorHistory).get(1).getTime()));
        //then
        assertEquals(expectedOriginatorHistory, originatorHistory);
    }

    @Test
    public void executeTransaction_AddsToTheHistory() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(transactionManager);
        Account beneficiary = new Account(transactionManager);
        double amount = 2000;
        Transaction transaction = new Transaction(amount, originator, beneficiary, false, false);
        Transaction transactionTrueExecuted = new Transaction(amount, originator, beneficiary,
                true, false);
        //when
        transactionManager.executeTransaction(transaction);
        Collection<Entry> originatorHistory = originator.history(LocalDateTime.MIN, LocalDateTime.MAX);
        Collection<Entry> expectedOriginatorHistory = new ArrayList<>();
        expectedOriginatorHistory.add(new Entry(originator, transactionTrueExecuted, -amount,
                ((ArrayList<Entry>)originatorHistory).get(0).getTime()));
        //then
        assertEquals(expectedOriginatorHistory, originatorHistory);
    }
}
