package report;

import report.Report;

import java.util.List;

public interface ReportGenerator<T> {
    Report generate(List<? extends T> entities);
}