package resources;

import report.ReportGenerator;
import report.ReportGeneratorCSV;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(transactionManager, 15);
        DebitCard debitCard2 = new DebitCard(transactionManager, 25);
        ReportGenerator<DebitCard> reportGenerator = new ReportGeneratorCSV<>(DebitCard.class);
        FileOutputStream os = new FileOutputStream("report1.csv");
        reportGenerator.generate(List.of(debitCard1, debitCard2)).writeTo(os);
    }
}
