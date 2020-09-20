import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {
    @Test
    public void openAccount_NullAccount_ReturnsTrue() {
        //given
        Customer customer = new Customer("Ivan", "Ivanov");
        //when
        boolean mustBeTrue = customer.openAccount(1);
        //then
        assertTrue(mustBeTrue);
    }

    @Test
    public void openAccount_HasAccount_ReturnsFalse() {
        //given
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(1);
        //when
        boolean mustBeFalse = customer.openAccount(2);
        //then
        assertFalse(mustBeFalse);
    }

    @Test
    public void closeAccount_NullAccount_ReturnsFalse() {
        //given
        Customer customer = new Customer("Ivan", "Ivanov");
        //when
        boolean mustBeFalse = customer.closeAccount();
        //then
        assertFalse(mustBeFalse);
    }

    @Test
    public void closeAccount_HasAccount_ReturnsTrue() {
        //given
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(1);
        //when
        boolean mustBeTrue = customer.closeAccount();
        //then
        assertTrue(mustBeTrue);
    }

    @Test
    public void fullName_ReturnsConcatenatedName() {
        //given
        Customer customer = new Customer("Ivan", "Ivanov");
        //when
        String fullName = customer.fullName();
        //then
        assertEquals(fullName, "Ivan Ivanov");
    }

    @Test
    public void withdrawFromCurrentAccount_NullAccount_ReturnsFalse() {
        //given
        Customer customer = new Customer("Ivan", "Ivanov");
        //when
        boolean mustBeFalse = customer.withdrawFromCurrentAccount(300);
        //then
        assertFalse(mustBeFalse);
    }

    @Test
    public void withdrawFromCurrentAccount_BalanceMoreThenWithdraw_ReturnsTrue() {
        //given
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(4);
        customer.addMoneyToCurrentAccount(500);
        //when
        boolean mustBeTrue = customer.withdrawFromCurrentAccount(400);
        //then
        assertTrue(mustBeTrue);
    }

    @Test
    public void withdrawFromCurrentAccount_LowBalance_ReturnsFalse() {
        //given
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(4);
        //when
        boolean mustBeFalse = customer.withdrawFromCurrentAccount(400);
        //then
        assertFalse(mustBeFalse);
    }

    @Test
    public void withdrawFromCurrentAccount_NegativeAmount_ReturnsFalse() {
        //given
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(4);
        //when
        boolean mustBeFalse = customer.withdrawFromCurrentAccount(-200);
        //then
        assertFalse(mustBeFalse);
    }

    @Test
    public void addMoneyToCurrentAccount_NullAccount_ReturnsFalse() {
        //given
        Customer customer = new Customer("Ivan", "Ivanov");
        //when
        boolean mustBeFalse = customer.addMoneyToCurrentAccount(400);
        //then
        assertFalse(mustBeFalse);
    }

    @Test
    public void addMoneyToCurrentAccount_NegativeAmount_ReturnsFalse() {
        //given
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(5);
        //when
        boolean mustBeFalse = customer.addMoneyToCurrentAccount(-100);
        //then
        assertFalse(mustBeFalse);
    }
}
