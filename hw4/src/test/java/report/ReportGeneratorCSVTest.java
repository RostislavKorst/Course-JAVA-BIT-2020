package report;

import junit.framework.TestCase;
import org.junit.Test;
import resources.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ReportGeneratorCSVTest extends TestCase {
    @Test
    public void testGenerate() throws IOException {
        Person jack = new Person("Jack", "Alexov", 180, 80, null);
        Person julia = new Person("Julia", "Alexova", 160, 55, null);
        Person alex = new Person("Alex", "Alexov", 170, 80, List.of(jack, julia));
        Person bob = new Person("Bob", "Bobov", 165, 80, List.of(alex));
        ReportGenerator<Person> reportGenerator = new ReportGeneratorCSV<>(Person.class);
        FileOutputStream os = new FileOutputStream("report3.csv");
        reportGenerator.generate(List.of(alex, bob, jack, julia)).writeTo(os);
    }
}