package ru.sbt.mipt.hw3;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class DebitCardTest {
    //before
    private final int bonusAccountPercent = 0;

    @Test
    public void withdraw_AddsEntryToTheHistory() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard debitCard2 = new DebitCard(transactionManager, bonusAccountPercent);
        //when
        Transaction expectedTransaction = new Transaction(200, debitCard1, debitCard2, true, false);
        debitCard1.addCash(300);
        debitCard1.withdraw(200, debitCard2);
        Collection<Entry> originatorHistory = debitCard1.history(LocalDateTime.MIN, LocalDateTime.MAX);
        Entry expectedEntry = new Entry(debitCard1, expectedTransaction, -200, ((ArrayList<Entry>)originatorHistory).get(1).getTime());
        Entry actualEntry = ((ArrayList<Entry>) originatorHistory).get(1);
        //then
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void withdraw_LowBalance_ReturnsFalse() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard debitCard2 = new DebitCard(transactionManager, bonusAccountPercent);
        //when
        boolean isNotCorrect = debitCard1.withdraw(200, debitCard2);
        //then
        assertFalse(isNotCorrect);
    }

    @Test
    public void withdrawCash() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard debitCard2 = new DebitCard(transactionManager, bonusAccountPercent);
        //when
        Transaction expectedTransaction = new Transaction(200, debitCard1, debitCard2, true, false);
        debitCard1.addCash(300);
        debitCard1.withdrawCash(200);
        Collection<Entry> originatorHistory = debitCard1.history(LocalDateTime.MIN, LocalDateTime.MAX);
        Entry expectedEntry = new Entry(debitCard1, expectedTransaction, -200, ((ArrayList<Entry>)originatorHistory).get(1).getTime());
        Entry actualEntry = ((ArrayList<Entry>) originatorHistory).get(1);
        //then
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void withdrawCash_LowBalance_ReturnsFalse() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, bonusAccountPercent);
        //when
        boolean isNotCorrect = debitCard1.withdrawCash(200);
        //then
        assertFalse(isNotCorrect);
    }

    @Test
    public void addCash() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard debitCard2 = new DebitCard(transactionManager, bonusAccountPercent);
        //when
        Transaction expectedTransaction = new Transaction(300, debitCard1, debitCard2, true, false);
        debitCard1.addCash(300);
        Collection<Entry> originatorHistory = debitCard1.history(LocalDateTime.MIN, LocalDateTime.MAX);
        Entry expectedEntry = new Entry(debitCard1, expectedTransaction, 300, ((ArrayList<Entry>)originatorHistory).get(0).getTime());
        Entry actualEntry = ((ArrayList<Entry>) originatorHistory).get(0);
        //then
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void addCash_NegativeAmount_ReturnsFalse() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, bonusAccountPercent);
        //when
        boolean isNotCorrect = debitCard1.addCash(-200);
        //then
        assertFalse(isNotCorrect);
    }

    @Test
    public void add() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard debitCard2 = new DebitCard(transactionManager, bonusAccountPercent);
        //when
        Transaction expectedTransaction = new Transaction(300, debitCard1, debitCard2, true, false);
        debitCard1.add(300, debitCard2);
        Collection<Entry> originatorHistory = debitCard1.history(LocalDateTime.MIN, LocalDateTime.MAX);
        Entry expectedEntry = new Entry(debitCard1, expectedTransaction, 300, ((ArrayList<Entry>)originatorHistory).get(0).getTime());
        Entry actualEntry = ((ArrayList<Entry>) originatorHistory).get(0);
        //then
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void add_NegativeAmount_ReturnsFalse() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard debitCard2 = new DebitCard(transactionManager, bonusAccountPercent);
        //when
        boolean isNotCorrect = debitCard1.add(-200, debitCard2);
        //then
        assertFalse(isNotCorrect);
    }

    @Test
    public void balanceOn() throws InterruptedException {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard debitCard2 = new DebitCard(transactionManager, bonusAccountPercent);
        //when
        debitCard2.add(200, debitCard1);
        debitCard2.addCash(300);
        TimeUnit.SECONDS.sleep(2);
        debitCard2.withdrawCash(45);
        debitCard2.withdrawCash(50);
        double actualBalance = debitCard2.balanceOn(LocalDateTime.now().minusSeconds(1));
        //then
        assertEquals(200 + 300, actualBalance, 0.0001);
    }

    @Test
    public void rollbackLastTransaction_ReturnsBalanceWithoutRollBackedTransaction() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard debitCard2 = new DebitCard(transactionManager, bonusAccountPercent);
        //when
        debitCard2.add(200, debitCard1);
        debitCard2.addCash(300);
        debitCard2.withdrawCash(45);
        debitCard2.withdrawCash(50);
        debitCard2.rollbackLastTransaction();
        double actualBalance = debitCard2.currentBalance();
        //then
        assertEquals(200 + 300 - 45, actualBalance, 0.0001);
    }

    @Test
    public void currentBalance() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        DebitCard originator = new DebitCard(transactionManager, bonusAccountPercent);
        DebitCard beneficiary = new DebitCard(transactionManager, bonusAccountPercent);
        //when
        beneficiary.add(200, originator);
        beneficiary.addCash(300);
        beneficiary.withdrawCash(45);
        double actualBalance = beneficiary.currentBalance();
        //then
        assertEquals(200 + 300 - 45, actualBalance, 0.0001);
    }
}