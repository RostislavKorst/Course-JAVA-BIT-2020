package ru.sbt.mipt.hw3;

public interface KeyExtractor<E, T> {
    T extract(E entity);
}
