package ru.sbt.mipt.hw2;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class TransactionTest {
    @Test
    public void execute_AddEntriesToTheHistory() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(transactionManager);
        Account beneficiary = new Account(transactionManager);
        double amount = 2000;
        Transaction transaction = new Transaction(amount, originator, beneficiary, false, false);
        Transaction transactionTrueExecuted = new Transaction(amount, originator, beneficiary,
                true, false);
        //when
        transaction.execute();
        Collection<Entry> originatorHistory = originator.history(LocalDateTime.MIN, LocalDateTime.MAX);
        Collection<Entry> expectedOriginatorHistory = new ArrayList<Entry>();
        expectedOriginatorHistory.add(new Entry(originator, transactionTrueExecuted, -amount,
                ((ArrayList<Entry>)originatorHistory).get(0).getTime()));
        //then
        assertEquals(expectedOriginatorHistory, originatorHistory);
    }

    @Test
    public void rollback_AddEntriesToTheHistory() {
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
        transaction.execute();
        transaction.rollback();
        Collection<Entry> originatorHistory = originator.history(LocalDateTime.MIN, LocalDateTime.MAX);
        Collection<Entry> expectedOriginatorHistory = new ArrayList<Entry>();
        expectedOriginatorHistory.add(new Entry(originator, transactionTrueExecuted, -amount,
                ((ArrayList<Entry>)originatorHistory).get(0).getTime()));
        expectedOriginatorHistory.add(new Entry(originator, transactionTrueRolledBack, amount,
                ((ArrayList<Entry>)originatorHistory).get(1).getTime()));
        //then
        assertEquals(expectedOriginatorHistory, originatorHistory);
    }
}
