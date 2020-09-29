package ru.sbt.mipt.hw2;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class EntriesTest {
    @Test
    public void fromTest_TimeIsNotFromCollection_ReturnsCollectionOfEntries() {
        //given
        Entries entries = new Entries();
        Entry firstEntry0minutes0seconds = new Entry(null, null, 0,
                LocalDateTime.now());
        entries.addEntry(firstEntry0minutes0seconds);
        Entry secondEntry5minutes = new Entry(null, null, 0,
                LocalDateTime.now().plusMinutes(5));
        entries.addEntry(secondEntry5minutes);
        Entry thirdEntry10minutes = new Entry(null, null, 0,
                LocalDateTime.now().plusMinutes(10));
        entries.addEntry(thirdEntry10minutes);
        Entry fourthEntry15minutes = new Entry(null, null, 0,
                LocalDateTime.now().plusMinutes(15));
        entries.addEntry(fourthEntry15minutes);
        LocalDateTime entryBetweenFirstAndSecond = LocalDateTime.now().plusMinutes(3);
        //when
        Collection<Entry> returnedArray = entries.from(entryBetweenFirstAndSecond);
        //then
        Collection<Entry> correctSubCollection = Arrays.asList(secondEntry5minutes, thirdEntry10minutes,
                fourthEntry15minutes);
        assertEquals(correctSubCollection, returnedArray);
    }

    @Test
    public void fromTest_TimeIsFromCollection_ReturnsCollectionOfEntries() {
        //given
        Entries entries = new Entries();
        Transaction firstTransaction = new Transaction(1, null, null,
                false, false);
        Entry firstEntry0minutes0seconds = new Entry(null, firstTransaction, 0,
                LocalDateTime.now());
        entries.addEntry(firstEntry0minutes0seconds);
        LocalDateTime secondTime = LocalDateTime.now().plusMinutes(5);
        Transaction secondTransaction = new Transaction(1, null, null,
                false, false);
        Entry secondEntry5minutes = new Entry(null, secondTransaction, 0,
                secondTime);
        entries.addEntry(secondEntry5minutes);
        Transaction thirdTransaction = new Transaction(1, null, null,
                false, false);
        Entry thirdEntry10minutes = new Entry(null, thirdTransaction, 0,
                LocalDateTime.now().plusMinutes(10));
        entries.addEntry(thirdEntry10minutes);
        Transaction fourthTransaction = new Transaction(1, null, null,
                false, false);
        Entry fourthEntry15minutes = new Entry(null, fourthTransaction, 0,
                LocalDateTime.now().plusMinutes(15));
        entries.addEntry(fourthEntry15minutes);
        //when
        Collection<Entry> returnedArray = entries.from(secondTime);
        //then
        Collection<Entry> correctSubCollection = Arrays.asList(secondEntry5minutes, thirdEntry10minutes,
                fourthEntry15minutes);
        assertEquals(correctSubCollection, returnedArray);
    }

    @Test
    public void betweenDates_TimeIsNotFromCollection_ReturnsCollectionOfEntries() {
        //given
        Entries entries = new Entries();
        Entry firstEntry0minutes0seconds = new Entry(null, null, 0,
                LocalDateTime.now());
        entries.addEntry(firstEntry0minutes0seconds);
        Entry secondEntry5minutes = new Entry(null, null, 0,
                LocalDateTime.now().plusMinutes(5));
        entries.addEntry(secondEntry5minutes);
        Entry thirdEntry10minutes = new Entry(null, null, 0,
                LocalDateTime.now().plusMinutes(10));
        entries.addEntry(thirdEntry10minutes);
        Entry fourthEntry15minutes = new Entry(null, null, 0,
                LocalDateTime.now().plusMinutes(15));
        entries.addEntry(fourthEntry15minutes);
        Entry fifthEntry20minutes = new Entry(null, null, 0,
                LocalDateTime.now().plusMinutes(20));
        entries.addEntry(fifthEntry20minutes);
        LocalDateTime timeBetweenFirstAndSecond = LocalDateTime.now().plusMinutes(3);
        LocalDateTime timeBetweenFourthAndFifth = LocalDateTime.now().plusMinutes(18);
        //when
        Collection<Entry> returnedArray = entries.betweenDates(timeBetweenFirstAndSecond, timeBetweenFourthAndFifth);
        //then
        Collection<Entry> correctSubCollection = Arrays.asList(secondEntry5minutes, thirdEntry10minutes,
                fourthEntry15minutes);
        assertEquals(correctSubCollection, returnedArray);
    }

    @Test
    public void betweenDates_ToTimeIsFromCollection_ReturnsCollectionOfEntries() {
        //given
        Entries entries = new Entries();
        Transaction firstTransaction = new Transaction(1, null, null,
                false, false);
        Entry firstEntry0minutes0seconds = new Entry(null, firstTransaction, 0,
                LocalDateTime.now());
        entries.addEntry(firstEntry0minutes0seconds);
        LocalDateTime secondTime = LocalDateTime.now().plusMinutes(5);
        Transaction secondTransaction = new Transaction(1, null, null,
                false, false);
        Entry secondEntry5minutes = new Entry(null, secondTransaction, 0,
                secondTime);
        entries.addEntry(secondEntry5minutes);
        Transaction thirdTransaction = new Transaction(1, null, null,
                false, false);
        Entry thirdEntry10minutes = new Entry(null, thirdTransaction, 0,
                LocalDateTime.now().plusMinutes(10));
        entries.addEntry(thirdEntry10minutes);
        Transaction fourthTransaction = new Transaction(1, null, null,
                false, false);
        LocalDateTime fourthTime = LocalDateTime.now().plusMinutes(15);
        Entry fourthEntry15minutes = new Entry(null, fourthTransaction, 0,
                fourthTime);
        entries.addEntry(fourthEntry15minutes);
        LocalDateTime timeBetweenFirstAndSecond = LocalDateTime.now().plusMinutes(3);
        //when
        Collection<Entry> returnedArray = entries.betweenDates(timeBetweenFirstAndSecond, fourthTime);
        //then
        Collection<Entry> correctSubCollection = Arrays.asList(secondEntry5minutes, thirdEntry10minutes);
        assertEquals(correctSubCollection, returnedArray);
    }

    @Test
    public void last() {
        //given
        Entries entries = new Entries();
        Entry firstEntry0minutes0seconds = new Entry(null, null, 0,
                LocalDateTime.now());
        entries.addEntry(firstEntry0minutes0seconds);
        Entry secondEntry5minutes = new Entry(null, null, 0,
                LocalDateTime.now().plusMinutes(5));
        entries.addEntry(secondEntry5minutes);
        Entry thirdEntry10minutes = new Entry(null, null, 0,
                LocalDateTime.now().plusMinutes(10));
        entries.addEntry(thirdEntry10minutes);
        Entry fourthEntry15minutes = new Entry(null, null, 0,
                LocalDateTime.now().plusMinutes(15));
        entries.addEntry(fourthEntry15minutes);
        LocalDateTime entryBetweenFirstAndSecond = LocalDateTime.now().plusMinutes(3);
        //when
        Entry returnedEntry = entries.last();
        //then
        assertEquals(fourthEntry15minutes, returnedEntry);
    }
}
