package ru.sbt.mipt.hw4;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ReportWriterCSV implements ReportWriter {
    @Override
    public void writeTo(OutputStream os, List<List<String>> table) throws IOException {
        for (List<String> row : table) {
            StringBuilder csvRow = new StringBuilder();
            for (String cell : row) {
                csvRow.append(cell).append(", ");
            }
            csvRow.append("\n");
            os.write(csvRow.toString().getBytes());
        }
    }
}