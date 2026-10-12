
package software.ulpgc.katas;

import org.junit.jupiter.api.Test;
import software.ulpgc.katas.io.*;
import software.ulpgc.katas.service.DepartmentCounter;
import software.ulpgc.katas.service.EmployeeReport;

import software.ulpgc.katas.domain.Department;
import software.ulpgc.katas.domain.Employee;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeReportTest {

    private static final String HEADER =
            "name\tdepartment\tmonthly_income\n";

    @Test
    void readsSameReportFromFileAndResource()
            throws IOException {

        String fromFile;
        String fromResource;

        try (InputStream file = new FileInputSource(
                Path.of("src/main/resources/employees.tsv")).open();

             InputStream resource =
                     new ResourceInputSource("employees.tsv").open()) {

            fromFile = report(file);
            fromResource = report(resource);
        }

        assertEquals(fromFile, fromResource);
        assertEquals(
                "Total de empleados: 25\n" +
                        "Development: 1\n" +
                        "R&D: 7\n" +
                        "Sales: 3\n" +
                        "Software Development: 13\n" +
                        "Training: 1\n",
                fromFile);
    }

    @Test
    void writesResultToFile() throws IOException {

        Path path = Files.createTempFile("kata2-", ".txt");

        try {
            try (InputStream input =
                         new ResourceInputSource("employees.tsv").open();
                 var output = Files.newOutputStream(path)) {

                newReport().generate(input, output);
            }

            String result = Files.readString(
                    path, StandardCharsets.UTF_8);

            assertTrue(result.contains(
                    "Software Development: 13"));

        } finally {
            Files.deleteIfExists(path);
        }
    }

    @Test
    void writesResultToMemory() throws IOException {

        try (InputStream input =
                     new ResourceInputSource("employees.tsv").open()) {

            String result = report(input);

            assertTrue(result.contains(
                    "Total de empleados: 25"));
        }
    }

    @Test
    void rejectsMissingFile() {

        IOException error = assertThrows(
                IOException.class,
                () -> new FileInputSource(
                        Path.of("no-existe.tsv")).open());

        assertTrue(error.getMessage().contains(
                "No existe el fichero"));
    }

    @Test
    void rejectsMissingResource() {

        IOException error = assertThrows(
                IOException.class,
                () -> new ResourceInputSource(
                        "no-existe.tsv").open());

        assertTrue(error.getMessage().contains(
                "No existe el recurso"));
    }

    @Test
    void rejectsEmptyInput() {

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> report(new ByteArrayInputStream(
                        new byte[0])));

        assertTrue(error.getMessage().contains("vacio"));
    }

    @Test
    void acceptsHeaderWithNoEmployees() throws IOException {

        assertEquals(
                "Total de empleados: 0\n",
                report(stream(HEADER)));
    }

    @Test
    void rejectsInvalidHeader() {

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> report(stream("wrong\theader\n")));

        assertTrue(error.getMessage().contains(
                "Cabecera TSV no valida"));
    }

    @Test
    void rejectsMalformedRowWithLineNumber() {

        String invalid = HEADER + "Ana\tSales\n";

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> report(stream(invalid)));

        assertTrue(error.getMessage().contains("Fila 2"));
    }

    @Test
    void rejectsNonNumericSalary() {

        String invalid = HEADER + "Ana\tSales\tmucho\n";

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> report(stream(invalid)));

        assertTrue(error.getMessage().contains("Fila 2"));
    }

    @Test
    void resultDoesNotDependOnRowOrder() throws IOException {

        String original = HEADER +
                "Ana\tSales\t2000\n" +
                "Luis\tR&D\t3000\n" +
                "Sonia\tSales\t2500\n";

        String reordered = HEADER +
                "Sonia\tSales\t2500\n" +
                "Luis\tR&D\t3000\n" +
                "Ana\tSales\t2000\n";

        assertEquals(
                report(stream(original)),
                report(stream(reordered)));
    }

    @Test
    void preservesUtf8Characters() throws IOException {

        String content = HEADER +
                "María\tInvestigación\t2000\n";

        assertEquals(
                "Total de empleados: 1\n" +
                        "Investigación: 1\n",
                report(stream(content)));
    }

    private static EmployeeReport newReport() {

        return new EmployeeReport(
                new TsvEmployeeLoader(),
                new DepartmentCounter(),
                new TextSummaryWriter()
        );
    }

    private static String report(InputStream input)
            throws IOException {

        var output = new ByteArrayOutputStream();

        newReport().generate(input, output);

        return output.toString(StandardCharsets.UTF_8);
    }

    private static InputStream stream(String content) {

        return new ByteArrayInputStream(
                content.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void rejectsNegativeMonthlyIncome() {
        assertThrows(IllegalArgumentException.class, () -> new Employee("Ana", new Department("Sales"), -1));
    }
}
