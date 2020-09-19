import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void add_NegativeAmount_ReturnsFalse() {
        Account account = new Account(1);
        assertFalse(account.add(-10));
    }

    @Test
    public void add_ReturnsTrue() {
        Account account = new Account(1);
        assertTrue(account.add(20));
    }

    @Test
    public void withdraw_NegativeAmount_ReturnsFalse() {
        Account account = new Account(1);
        assertFalse(account.withdraw(-10));
    }

    @Test
    public void withdraw_LowBalance_ReturnsFalse() {
        Account account = new Account(1);
        account.add(8);
        assertFalse(account.withdraw(9));
    }

    @Test
    public void withdraw_ReturnsTrue() {
        Account account = new Account(1);
        account.add(10);
        assertTrue(account.withdraw(9));
    }

    @Test
    public void withdraw_BalanceEqualsAmount_ReturnsTrue() {
        Account account = new Account(1);
        account.add(10);
        assertTrue(account.withdraw(10));
    }
}
