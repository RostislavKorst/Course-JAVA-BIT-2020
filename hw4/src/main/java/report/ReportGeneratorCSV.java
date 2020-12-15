package report;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class ReportGeneratorCSV<T> implements ReportGenerator<T> {
    private final Class<T> clazz;
    private final Map<String, String> fieldNamesToCustomNames;

    public ReportGeneratorCSV(Class<T> clazz, Map<String, String> fieldNamesToCustomNames) {
        this.clazz = clazz;
        this.fieldNamesToCustomNames = fieldNamesToCustomNames;
    }

    public ReportGeneratorCSV(Class<T> clazz) {
        this.clazz = clazz;
        this.fieldNamesToCustomNames = new HashMap<>();
    }

    @Override
    public Report generate(List<? extends T> entities) {
        StringBuilder builder = new StringBuilder();
        List<Field> fieldsList = getFields();
        builder.append(String.join(",", headers(fieldsList)));
        body(entities, builder, fieldsList);
        return new CSVReport(builder.toString().getBytes());
    }

    private List<Field> getFields() {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .peek(field -> field.setAccessible(true))
                .filter(not(Field::isSynthetic))
                .filter(not(field -> Modifier.isStatic(field.getModifiers())))
                .collect(toList());
    }

    private List<String> headers(List<Field> fields) {
        return fields.stream()
                .map(Field::getName)
                .map(fieldName -> fieldNamesToCustomNames.getOrDefault(fieldName, fieldName))
                .collect(toList());
    }

    private void body(List<? extends T> entities, StringBuilder builder, List<Field> fields) {
        entities.stream()
                .map(entity -> constructEntityRow(fields, entity))
                .forEach(row -> builder.append('\n').append(String.join(",", row)));
    }

    private List<String> constructEntityRow(List<Field> fields, T entity) {
        List<String> entityRow = new ArrayList<>();
        for (Field field : fields) {
            try {
                entityRow.add(field.get(entity).toString());
            } catch (IllegalAccessException | NullPointerException ex) {
                entityRow.add("null");
            }
        }
        return entityRow;
    }
}