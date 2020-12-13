import java.time.LocalDateTime;
import java.util.*;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
public class Entries {
    private final SortedSet<Entry> storage = new TreeSet<>();

    void addEntry(Entry entry) {
        storage.add(entry);
    }

    Collection<Entry> from(LocalDateTime date) {
        Transaction fictionTransaction1 = new Transaction(0, 0, null, null,
                false, false);
        Entry fromEntry = new Entry(null, fictionTransaction1, 0, date);
        return storage.tailSet(fromEntry);
    }

    Collection<Entry> betweenDates(LocalDateTime from, LocalDateTime to) {
        try {
            if (from.compareTo(to) > 0) {
                throw new IllegalArgumentException("Wrong dates");
            }
        } catch (IllegalArgumentException exception) {
            throw new RuntimeException(exception);
        }
        Transaction fictionTransaction = new Transaction(0, 0, null, null,
                false, false);
        Entry fromEntry = new Entry(null, fictionTransaction, 0, from);
        Entry toEntry = new Entry(null, fictionTransaction, 0, to);
        return storage.subSet(fromEntry, toEntry);
    }

    Entry last() {
        return storage.last();
    }

    Collection<Entry> upTo(LocalDateTime date) {
        Transaction fictionTransaction1 = new Transaction(Long.MAX_VALUE, 0, null, null,
                false, false);
        Entry toEntry = new Entry(null, fictionTransaction1, 0, date);
        return storage.headSet(toEntry);
    }
}
