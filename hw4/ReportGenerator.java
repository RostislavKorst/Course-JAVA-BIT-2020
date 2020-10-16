package ru.sbt.mipt.hw4;

import java.util.List;

interface ReportGenerator<T> {
    Report generate(List<T> entities) throws IllegalAccessException;
}