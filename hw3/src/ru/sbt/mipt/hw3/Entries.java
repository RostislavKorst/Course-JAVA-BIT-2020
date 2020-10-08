package ru.sbt.mipt.hw3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
public class Entries {
    private final ArrayList<Entry> storage = new ArrayList<>();

    void addEntry(Entry entry) {
        storage.add(entry);
    }

    Collection<Entry> from(LocalDateTime date) {
        Transaction fictionTransaction = new Transaction(1, null, null,
                false, false);
        Entry fromEntry = new Entry(null, fictionTransaction, 0, date);
        int startIndex = Collections.binarySearch(storage, fromEntry);
        startIndex = -startIndex - 1;
        int endIndex = storage.size();
        return new ArrayList<>(storage.subList(startIndex, endIndex));
    }

    Collection<Entry> betweenDates(LocalDateTime from, LocalDateTime to) throws IllegalArgumentException {
        Transaction fictionTransaction1 = new Transaction(1, null, null,
                false, false);
        Transaction fictionTransaction2 = new Transaction(1, null, null,
                false, false);
        Entry fromEntry = new Entry(null, fictionTransaction1, 0, from);
        Entry toEntry = new Entry(null, fictionTransaction2, 0, to);
        int startIndex = Collections.binarySearch(storage, fromEntry);
        startIndex = -startIndex - 1;
        int endIndex = Collections.binarySearch(storage, toEntry);
        endIndex = -endIndex - 1;
        if (startIndex > endIndex) {
            throw new IllegalArgumentException("Wrong dates");
        }
        return new ArrayList<>(storage.subList(startIndex, endIndex));
    }

    Entry last() {
        return storage.get(storage.size() - 1);
    }
}
