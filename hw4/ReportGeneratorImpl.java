package ru.sbt.mipt.hw4;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReportGeneratorImpl<T> implements ReportGenerator<T> {
    private final Class<T> clazz;
    private List<String> columnNames;

    public ReportGeneratorImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Report generate(List<T> entities) throws IllegalAccessException {
        List<List<String>> table = new ArrayList<>();
        List<String> headerRow = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        if (columnNames != null) {
            table.add(columnNames);
        } else {
            for (Field field : fields) {
                headerRow.add(field.getName());
            }
            table.add(headerRow);
        }
        for (T entity : entities) {
            List<String> entityRow = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                entityRow.add(field.get(entity).toString());
            }
            table.add(entityRow);
        }
        return new TableReport(table);
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }
}