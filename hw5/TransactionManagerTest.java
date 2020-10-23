package ru.sbt.mipt.hw3;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class TransactionManagerTest {
    private TransactionManager transactionManager;
    private DebitCard account1;
    private DebitCard account2;

    @Before
    public void createAccounts() {
        transactionManager = new TransactionManager();
        account1 = new DebitCard(transactionManager, 0);
        account2 = new DebitCard(transactionManager, 0);
    }

    @Test
    public void createTransaction_ReturnsTransaction() {
        //when
        Transaction actualTransaction = transactionManager.createTransaction(2000, account1, account2);
        Transaction expectedTransaction = new Transaction(actualTransaction.getId(), 2000, account1,
                account2, false, false);
        //then
        assertEquals(expectedTransaction, actualTransaction);
    }

    private void executeSomeTransactions() {
        account2.add(200, account1);
        account2.addCash(300);
        account2.withdrawCash(45);
    }

    private Collection<Transaction> getExpectedTransactions() {
        Transaction transaction1 = new Transaction(1, 200, account1, account2,
                true, false);
        Transaction transaction2 = new Transaction(2, 300, null, account2,
                true, false);
        Transaction transaction3 = new Transaction(3, 45, account2, null,
                true, false);
        return Arrays.asList(transaction1, transaction2, transaction3);
    }

    @Test
    public void findAllTransactionsByAccount_ReturnsCollectionOfTransactions() {
        //given
        executeSomeTransactions();
        Collection<Transaction> expectedListOfTransactions = getExpectedTransactions();
        //when
        Collection<Transaction> actualListOfTransactions = transactionManager.findAllTransactionsByAccount(account2);
        //then
        assertEquals(expectedListOfTransactions, actualListOfTransactions);
    }

    @Test
    public void rollbackTransaction_AddsEntryToTheHistory() {
        //given
        double amount = 2000;
        Transaction transaction = new Transaction(amount, account1, account2, false, false);
        Transaction transactionTrueRolledBack = new Transaction(amount, account1, account2,
                true, true);
        transactionManager.executeTransaction(transaction);
        //when
        transactionManager.rollbackTransaction(transaction);
        Entry expectedEntry = new Entry(account1, transactionTrueRolledBack, amount, LocalDateTime.now());
        Entry actualEntry = account1.lastEntry();
        //then
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void executeTransaction_AddsToTheHistory() {
        //given
        double amount = 2000;
        Transaction transaction = new Transaction(amount, account1, account2, false, false);
        Transaction transactionTrueExecuted = new Transaction(amount, account1, account2,
                true, false);
        //when
        transactionManager.executeTransaction(transaction);
        Entry expectedEntry = new Entry(account1, transactionTrueExecuted, -amount, LocalDateTime.now());
        Entry actualEntry = account1.lastEntry();
        //then
        assertEquals(expectedEntry, actualEntry);
    }
}
