package ru.sbt.mipt.hw2;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void withdraw_AddsEntryToTheHistory() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(transactionManager);
        Account account2 = new Account(transactionManager);
        //when
        Transaction expectedTransaction = new Transaction(200, account1, account2, true, false);
        account1.addCash(300);
        account1.withdraw(200, account2);
        Collection<Entry> originatorHistory = account1.history(LocalDateTime.MIN, LocalDateTime.MAX);
        Entry expectedEntry = new Entry(account1, expectedTransaction, -200, ((ArrayList<Entry>)originatorHistory).get(1).getTime());
        Entry actualEntry = ((ArrayList<Entry>) originatorHistory).get(1);
        //then
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void withdraw_LowBalance_ReturnsFalse() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(transactionManager);
        Account account2 = new Account(transactionManager);
        //when
        boolean isNotCorrect = account1.withdraw(200, account2);
        //then
        assertFalse(isNotCorrect);
    }

    @Test
    public void withdrawCash() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(transactionManager);
        Account account2 = new Account(transactionManager);
        //when
        Transaction expectedTransaction = new Transaction(200, account1, account2, true, false);
        account1.addCash(300);
        account1.withdrawCash(200);
        Collection<Entry> originatorHistory = account1.history(LocalDateTime.MIN, LocalDateTime.MAX);
        Entry expectedEntry = new Entry(account1, expectedTransaction, -200, ((ArrayList<Entry>)originatorHistory).get(1).getTime());
        Entry actualEntry = ((ArrayList<Entry>) originatorHistory).get(1);
        //then
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void withdrawCash_LowBalance_ReturnsFalse() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(transactionManager);
        //when
        boolean isNotCorrect = account1.withdrawCash(200);
        //then
        assertFalse(isNotCorrect);
    }

    @Test
    public void addCash() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(transactionManager);
        Account account2 = new Account(transactionManager);
        //when
        Transaction expectedTransaction = new Transaction(300, account1, account2, true, false);
        account1.addCash(300);
        Collection<Entry> originatorHistory = account1.history(LocalDateTime.MIN, LocalDateTime.MAX);
        Entry expectedEntry = new Entry(account1, expectedTransaction, 300, ((ArrayList<Entry>)originatorHistory).get(0).getTime());
        Entry actualEntry = ((ArrayList<Entry>) originatorHistory).get(0);
        //then
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void addCash_NegativeAmount_ReturnsFalse() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(transactionManager);
        //when
        boolean isNotCorrect = account1.addCash(-200);
        //then
        assertFalse(isNotCorrect);
    }

    @Test
    public void add() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(transactionManager);
        Account account2 = new Account(transactionManager);
        //when
        Transaction expectedTransaction = new Transaction(300, account1, account2, true, false);
        account1.add(300, account2);
        Collection<Entry> originatorHistory = account1.history(LocalDateTime.MIN, LocalDateTime.MAX);
        Entry expectedEntry = new Entry(account1, expectedTransaction, 300, ((ArrayList<Entry>)originatorHistory).get(0).getTime());
        Entry actualEntry = ((ArrayList<Entry>) originatorHistory).get(0);
        //then
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void add_NegativeAmount_ReturnsFalse() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(transactionManager);
        Account account2 = new Account(transactionManager);
        //when
        boolean isNotCorrect = account1.add(-200, account2);
        //then
        assertFalse(isNotCorrect);
    }

    @Test
    public void balanceOn() throws InterruptedException {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(transactionManager);
        Account account2 = new Account(transactionManager);
        //when
        account2.add(200, account1);
        account2.addCash(300);
        TimeUnit.SECONDS.sleep(2);
        account2.withdrawCash(45);
        account2.withdrawCash(50);
        double actualBalance = account2.balanceOn(LocalDateTime.now().minusSeconds(1));
        //then
        assertEquals(200 + 300, actualBalance, 0.0001);
    }

    @Test
    public void rollbackLastTransaction_ReturnsBalanceWithoutRollBackedTransaction() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(transactionManager);
        Account account2 = new Account(transactionManager);
        //when
        account2.add(200, account1);
        account2.addCash(300);
        account2.withdrawCash(45);
        account2.withdrawCash(50);
        account2.rollbackLastTransaction();
        double actualBalance = account2.currentBalance();
        //then
        assertEquals(200 + 300 - 45, actualBalance, 0.0001);
    }

    @Test
    public void currentBalance() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(transactionManager);
        Account beneficiary = new Account(transactionManager);
        //when
        beneficiary.add(200, originator);
        beneficiary.addCash(300);
        beneficiary.withdrawCash(45);
        double actualBalance = beneficiary.currentBalance();
        //then
        assertEquals(200 + 300 - 45, actualBalance, 0.0001);
    }
}