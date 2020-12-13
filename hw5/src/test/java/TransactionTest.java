import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TransactionTest {
    private TransactionManager transactionManager;
    private DebitCard account1;
    private DebitCard account2;

    @Before
    public void createAccounts() {
        transactionManager = new TransactionManager();
        BonusAccount bonusAccount = new BonusAccount(0);
        account1 = new DebitCard(transactionManager, bonusAccount);
        account2 = new DebitCard(transactionManager, bonusAccount);
    }

    @Test
    public void execute_AddEntryToTheHistory() {
        //given
        double amount = 2000;
        Transaction transaction = new Transaction(amount, account1, account2, false, false);
        Transaction transactionTrueExecuted = new Transaction(amount, account1, account2,
                true, false);
        //when
        transaction.execute();
        Entry expectedEntry = new Entry(account1, transactionTrueExecuted, -amount, LocalDateTime.now());
        Entry actualEntry = account1.lastEntry();
        //then
        assertEquals(expectedEntry, actualEntry);
    }

    @Test
    public void rollback_AddEntriesToTheHistory() {
        //given
        double amount = 2000;
        Transaction transaction = new Transaction(amount, account1, account2, false, false);
        Transaction transactionTrueRolledBack = new Transaction(amount, account1, account2,
                true, true);
        transactionManager.executeTransaction(transaction);
        //when
        transaction.rollback();
        Entry expectedEntry = new Entry(account1, transactionTrueRolledBack, amount, LocalDateTime.now());
        Entry actualEntry = account1.lastEntry();
        //then
        assertEquals(expectedEntry, actualEntry);
    }
}