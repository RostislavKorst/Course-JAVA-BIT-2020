import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
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
    public void execute_AddEntryToTheHistory() throws NoSuchFieldException, IllegalAccessException {
        //given
        double amount = 2000;
        startTransactionIdFromOne();
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
    public void rollback_AddEntriesToTheHistory() throws NoSuchFieldException, IllegalAccessException {
        //given
        double amount = 2000;
        startTransactionIdFromOne();
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

    private void startTransactionIdFromOne() throws NoSuchFieldException, IllegalAccessException {
        Transaction transaction = new Transaction(0, 2000, account1, account2,
                true, false);
        Field field = Transaction.class.getDeclaredField("counter");
        field.setAccessible(true);
        field.setLong(transaction, 1);
    }
}