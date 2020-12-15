package resources;

import java.time.LocalDateTime;

public interface Account {
    void addEntry(Entry entry);

    double balanceOn(LocalDateTime date);
}
