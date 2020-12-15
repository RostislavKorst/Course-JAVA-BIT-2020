package resources;

import java.time.LocalDateTime;

public class BonusAccount implements Account {
    private final int percent;
    private final long id;
    private final Entries entries;
    private static long counter = 1;

    public BonusAccount(int percent) {
        this.percent = percent;
        this.id = counter++;
        this.entries = new Entries();
    }

    @Override
    public void addEntry(Entry entry) {
        entries.addEntry(entry);
    }

    /**
     * Calculates balance on the accounting entries basis
     *
     * @param date
     * @return balance
     */
    @Override
    public double balanceOn(LocalDateTime date) {
        double toReturn = 0;
        for (Entry entry : entries.betweenDates(LocalDateTime.MIN, date)) {
            toReturn += entry.getAmount();
        }
        return toReturn;
    }
}