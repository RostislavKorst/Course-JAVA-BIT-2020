import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class CustomerTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void openAccount_NullAccount_ReturnsTrue() {
        Customer customer = new Customer("Ivan", "Ivanov");
        assertTrue(customer.openAccount(1));
    }

    @Test
    public void openAccount_HasAccount_ReturnsFalse() {
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(1);
        assertFalse(customer.openAccount(2));
    }

    @Test
    public void openAccount_HasAccount_PrintsMessage() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(1);
        customer.openAccount(2);
        assertEquals("Customer Ivan Ivanov already has the active account", outputStreamCaptor.toString().trim());
    }

    @Test
    public void closeAccount_NullAccount_ReturnsFalse() {
        Customer customer = new Customer("Ivan", "Ivanov");
        assertFalse(customer.closeAccount());
    }

    @Test
    public void closeAccount_NullAccount_PrintsMessage() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.closeAccount();
        assertEquals("Customer Ivan Ivanov has no active account to close", outputStreamCaptor.toString().trim());
    }

    @Test
    public void closeAccount_HasAccount_ReturnsTrue() {
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(1);
        assertTrue(customer.closeAccount());
    }

    @Test
    public void fullName_ReturnsConcatenatedName() {
        Customer customer = new Customer("Ivan", "Ivanov");
        assertEquals(customer.fullName(), "Ivan Ivanov");
    }

    @Test
    public void withdrawFromCurrentAccount_NullAccount_PrintsMessage() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.withdrawFromCurrentAccount(100);
        assertEquals("Customer Ivan Ivanov has no active account", outputStreamCaptor.toString().trim());
    }

    @Test
    public void withdrawFromCurrentAccount_NullAccount_ReturnsFalse() {
        Customer customer = new Customer("Ivan", "Ivanov");
        assertFalse(customer.withdrawFromCurrentAccount(300));
    }

    @Test
    public void withdrawFromCurrentAccount_BalanceMoreThenWithdraw_ReturnsTrue() {
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(4);
        customer.addMoneyToCurrentAccount(500);
        assertTrue(customer.withdrawFromCurrentAccount(400));
    }

    @Test
    public void withdrawFromCurrentAccount_LowBalance_ReturnsFalse() {
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(4);
        assertFalse(customer.withdrawFromCurrentAccount(400));
    }

    @Test
    public void withdrawFromCurrentAccount_NegativeAmount_ReturnsFalse() {
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(4);
        assertFalse(customer.withdrawFromCurrentAccount(-200));
    }

    @Test
    public void addMoneyToCurrentAccount_NullAccount_ReturnsFalse() {
        Customer customer = new Customer("Ivan", "Ivanov");
        assertFalse(customer.addMoneyToCurrentAccount(400));
    }

    @Test
    public void addMoneyToCurrentAccount_NullAccount_PrintsMessage() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.addMoneyToCurrentAccount(200);
        assertEquals("Customer Ivan Ivanov has no active account", outputStreamCaptor.toString().trim());
    }

    @Test
    public void addMoneyToCurrentAccount_NegativeAmount_ReturnsFalse() {
        Customer customer = new Customer("Ivan", "Ivanov");
        customer.openAccount(5);
        assertFalse(customer.addMoneyToCurrentAccount(-100));
    }
}
