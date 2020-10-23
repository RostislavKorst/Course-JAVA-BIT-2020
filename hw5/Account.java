package ru.sbt.mipt.hw3;

import java.time.LocalDateTime;

public interface Account {
    void addEntry(Entry entry);

    double balanceOn(LocalDateTime date);

    double currentBalance();
}
