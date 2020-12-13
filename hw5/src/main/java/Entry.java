import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The record of allocating the amount to the account
 * Amount can be either positive or negative depending on originator or beneficiary
 */
public class Entry implements Comparable<Entry> {
    private final Account account;
    private final Transaction transaction;
    private final double amount;
    private final LocalDateTime time;

    public Entry(Account account, Transaction transaction, double amount, LocalDateTime time) {
        this.account = account;
        this.transaction = transaction;
        this.amount = amount;
        this.time = time;
    }

    Transaction getTransaction() {
        return transaction;
    }

    double getAmount() {
        return amount;
    }

    LocalDateTime getTime() {
        return time;
    }

    @Override
    public int compareTo(Entry entry) {
        if (this.time.compareTo(entry.time) == 0) {
            return this.transaction.compareTo(entry.transaction);
        }
        return this.time.compareTo(entry.time);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Entry entry = (Entry) object;
        return Double.compare(entry.amount, amount) == 0 &&
                account.equals(entry.account) &&
                time.equals(entry.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, amount, time);
    }
}
