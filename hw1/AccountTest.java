import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void add_NegativeAmount_ReturnsFalse() {
        //given
        Account account = new Account(1);
        //when
        boolean mustBeFalse = account.add(-10);
        //then
        assertFalse(mustBeFalse);
    }

    @Test
    public void add_ReturnsTrue() {
        //given
        Account account = new Account(1);
        //when
        boolean mustBeTrue = account.add(20);
        //then
        assertTrue(mustBeTrue);
    }

    @Test
    public void withdraw_NegativeAmount_ReturnsFalse() {
        //given
        Account account = new Account(1);
        //when
        boolean mustBeFalse = account.withdraw(-10);
        //then
        assertFalse(mustBeFalse);
    }

    @Test
    public void withdraw_LowBalance_ReturnsFalse() {
        //given
        Account account = new Account(1);
        account.add(8);
        //when
        boolean mustBeFalse = account.withdraw(9);
        //then
        assertFalse(mustBeFalse);
    }

    @Test
    public void withdraw_ReturnsTrue() {
        //given
        Account account = new Account(1);
        account.add(10);
        //when
        boolean mustBeTrue = account.withdraw(9);
        //then
        assertTrue(mustBeTrue);
    }

    @Test
    public void withdraw_BalanceEqualsAmount_ReturnsTrue() {
        //given
        Account account = new Account(1);
        account.add(10);
        //when
        boolean mustBeTrue = account.withdraw(10);
        //then
        assertTrue(mustBeTrue);
    }
}
