package ru.sbt.mipt.hw3;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

public class EntriesTest {
    private Entries entries;
    private Collection<Entry> expectedEntries;
    private Collection<Entry> expectedEntriesBetweenDates;
    private LocalDateTime testTime;

    @Before
    public void createEntries() {
        entries = new Entries();
        DebitCard account = new DebitCard(new TransactionManager(), 0);
        DebitCard account1 = new DebitCard(new TransactionManager(), 0);
        Entry firstEntry0minutes0seconds = new Entry(account, new Transaction(0, 0, account, account1,
                false, false), 0,
                LocalDateTime.now());
        entries.addEntry(firstEntry0minutes0seconds);
        testTime = LocalDateTime.now().plusMinutes(5);
        Entry secondEntry5minutes = new Entry(account, new Transaction(0, 0, account, account1,
                false, false), 0,
                testTime);
        entries.addEntry(secondEntry5minutes);
        Entry thirdEntry10minutes = new Entry(account, new Transaction(0, 0, account, account1,
                false, false), 0,
                LocalDateTime.now().plusMinutes(10));
        entries.addEntry(thirdEntry10minutes);
        Entry fourthEntry15minutes = new Entry(account, new Transaction(0, 0, account, account1,
                false, false), 0,
                LocalDateTime.now().plusMinutes(15));
        entries.addEntry(fourthEntry15minutes);
        Entry fifthEntry20minutes = new Entry(null, new Transaction(0, 0, account, account1,
                false, false), 0,
                LocalDateTime.now().plusMinutes(20));
        entries.addEntry(fifthEntry20minutes);

        expectedEntries = new TreeSet<>(List.of(secondEntry5minutes,
                thirdEntry10minutes,
                fourthEntry15minutes, fifthEntry20minutes));
        expectedEntriesBetweenDates = new TreeSet<>(List.of(secondEntry5minutes,
                thirdEntry10minutes,
                fourthEntry15minutes));
    }

    @Test
    public void fromTest_TimeIsNotFromCollection_ReturnsCollectionOfEntries() {
        //given
        LocalDateTime entryBetweenFirstAndSecond = LocalDateTime.now().plusMinutes(3);
        //when
        Collection<Entry> returnedArray = entries.from(entryBetweenFirstAndSecond);
        //then
        assertEquals(expectedEntries, returnedArray);
    }

    @Test
    public void fromTest_TimeIsFromCollection_ReturnsCollectionOfEntries() {
        //given
        LocalDateTime secondTime = testTime;
        //when
        Collection<Entry> returnedArray = entries.from(secondTime);
        //then
        assertEquals(expectedEntries, returnedArray);
    }

    @Test
    public void betweenDates_TimeIsNotFromCollection_ReturnsCollectionOfEntries() {
        //given
        LocalDateTime timeBetweenFirstAndSecond = LocalDateTime.now().plusMinutes(3);
        LocalDateTime timeBetweenFourthAndFifth = LocalDateTime.now().plusMinutes(18);
        //when
        Collection<Entry> returnedArray = entries.betweenDates(timeBetweenFirstAndSecond, timeBetweenFourthAndFifth);
        //then
        assertEquals(expectedEntriesBetweenDates, returnedArray);
    }

    @Test
    public void betweenDates_TimeIsFromCollection_ReturnsCollectionOfEntries() {
        //given
        LocalDateTime timeBetweenFourthAndFifth = LocalDateTime.now().plusMinutes(18);
        //when
        Collection<Entry> returnedArray = entries.betweenDates(testTime, timeBetweenFourthAndFifth);
        //then
        assertEquals(expectedEntriesBetweenDates, returnedArray);
    }

    @Test
    public void last() {
        //given
        Transaction firstTransaction = new Transaction(1, null, null,
                false, false);
        Entry expectedLastEntry = new Entry(null, firstTransaction, 0,
                LocalDateTime.now().plusMinutes(30));
        entries.addEntry(expectedLastEntry);
        //when
        Entry returnedEntry = entries.last();
        //then
        assertEquals(expectedLastEntry, returnedEntry);
    }
}
