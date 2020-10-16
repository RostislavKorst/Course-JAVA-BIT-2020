package ru.sbt.mipt.hw4;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class TableReport implements Report {
    private final List<List<String>> table;
    private ReportWriter reportWriter;

    public TableReport(List<List<String>> table) {
        this.table = table;
    }

    @Override
    public byte[] asBytes() {
        StringBuilder aggregatedTable = new StringBuilder();
        for (List<String> row : table) {
            for (String cell : row) {
                aggregatedTable.append(cell);
            }
        }
        return aggregatedTable.toString().getBytes();
    }

    @Override
    public void writeTo(OutputStream os) throws IOException {
        if (reportWriter == null) return;
        reportWriter.writeTo(os, table);
    }

    public void setReportWriter(ReportWriter reportWriter) {
        this.reportWriter = reportWriter;
    }
}