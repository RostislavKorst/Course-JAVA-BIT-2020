package ru.sbt.mipt.hw4;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ReportWriter {
    void writeTo(OutputStream os, List<List<String>> table) throws IOException;
}