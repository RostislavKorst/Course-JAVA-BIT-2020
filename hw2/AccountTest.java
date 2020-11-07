package ru.sbt.mipt.hw2;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class AccountTest {
    private Account givenAccountWithInitialBalance(int initialBalance) {
        TransactionManager transactionManager = new TransactionManager();
        Account account = new Account(transactionManager);
        if (initialBalance > 0) {
            account.addCash(initialBalance);
        }
        return account;
    }

    @Test
    public void withdraw_AddsEntryToTheHistory() {
        //given
        Account account1 = givenAccountWithInitialBalance(300);
        Account account2 = givenAccountWithInitialBalance(0);
        //when
        account1.withdraw(200, account2);
        //then
        Entry expectedEntry = new Entry(account1, null, -200, LocalDateTime.now());
        Entry actualEntry = account1.lastEntry();
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void withdraw_LowBalance_ReturnsFalse() {
        //given
        Account account1 = givenAccountWithInitialBalance(50);
        Account account2 = givenAccountWithInitialBalance(0);
        //when
        boolean isSuccessWithdraw = account1.withdraw(200, account2);
        //then
        assertFalse(isSuccessWithdraw);
    }

    @Test
    public void withdrawCash_AddsEntryToTheHistory() {
        //given
        Account account1 = givenAccountWithInitialBalance(300);
        //when
        account1.withdrawCash(200);
        //then
        Entry expectedEntry = new Entry(account1, null, -200, LocalDateTime.now());
        Entry actualEntry = account1.lastEntry();
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void addCash_AddsEntryToTheHistory() {
        //given
        Account account1 = givenAccountWithInitialBalance(300);
        //when
        account1.addCash(200);
        //then
        Entry expectedEntry = new Entry(account1,null, 200, LocalDateTime.now());
        Entry actualEntry = account1.lastEntry();
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void add_AddsEntryToTheHistory() {
        //given
        Account account1 = givenAccountWithInitialBalance(300);
        Account account2 = givenAccountWithInitialBalance(200);
        //when
        account1.add(200, account2);
        //then
        Entry expectedEntry = new Entry(account1,null, 200, LocalDateTime.now());
        Entry actualEntry = account1.lastEntry();
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void withdrawCash_LowBalance_ReturnsFalse() {
        //given
        Account account = givenAccountWithInitialBalance(50);
        //when
        boolean isSuccessWithdraw = account.withdrawCash(200);
        //then
        assertFalse(isSuccessWithdraw);
    }

    @Test
    public void addCash_NegativeAmount_ReturnsFalse() {
        //given
        Account account = givenAccountWithInitialBalance(0);
        //when
        boolean isSuccessAdd = account.addCash(-200);
        //then
        assertFalse(isSuccessAdd);
    }

    @Test
    public void add_NegativeAmount_ReturnsFalse() {
        //given
        Account account1 = givenAccountWithInitialBalance(200);
        Account account2 = givenAccountWithInitialBalance(300);
        //when
        boolean isSuccessAdd = account1.add(-200, account2);
        //then
        assertFalse(isSuccessAdd);
    }

    private void executeSomeTransactionsWithTimeDelay(Account account) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        TimeUnit.MILLISECONDS.sleep(10);
        account.withdrawCash(45);
        account.withdrawCash(50);
    }

    @Test
    public void balanceOn() throws InterruptedException {
        //given
        Account account = givenAccountWithInitialBalance(500);
        executeSomeTransactionsWithTimeDelay(account);
        //when
        double actualBalance = account.balanceOn(LocalDateTime.now().minusSeconds(1));
        //then
        assertEquals(500, actualBalance, 1e-4);
    }

    @Test
    public void rollbackLastTransaction_ReturnsBalanceWithoutRollBackedTransaction() {
        //given
        Account account = givenAccountWithInitialBalance(500);
        //when
        account.withdrawCash(50);
        account.rollbackLastTransaction();
        double actualBalance = account.currentBalance();
        //then
        assertEquals(500, actualBalance, 1e-4);
    }

    @Test
    public void currentBalance() {
        //given
        Account account = givenAccountWithInitialBalance(500);
        //when
        double actualBalance = account.currentBalance();
        //then
        assertEquals(500, actualBalance, 1e-4);
    }
}