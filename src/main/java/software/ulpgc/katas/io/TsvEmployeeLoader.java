
package software.ulpgc.katas.io;

import software.ulpgc.katas.domain.Department;
import software.ulpgc.katas.domain.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class TsvEmployeeLoader implements EmployeeLoader {

    private static final String HEADER =
            "name\tdepartment\tmonthly_income";

    @Override
    public List<Employee> load(InputStream input)
            throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        input, StandardCharsets.UTF_8));

        String header = reader.readLine();

        if (header == null) {
            throw new IllegalArgumentException(
                    "El TSV esta vacio");
        }

        if (!HEADER.equals(header)) {
            throw new IllegalArgumentException(
                    "Cabecera TSV no valida");
        }

        List<Employee> employees = new ArrayList<>();

        String line;
        int lineNumber = 1;

        while ((line = reader.readLine()) != null) {
            lineNumber++;
            employees.add(parse(line, lineNumber));
        }

        return List.copyOf(employees);
    }

    private Employee parse(String line, int lineNumber) {

        String[] fields = line.split("\t", -1);

        if (fields.length != 3) {
            throw new IllegalArgumentException(
                    "Fila " + lineNumber +
                            ": se esperan 3 columnas");
        }

        try {
            return new Employee(
                    fields[0],
                    new Department(fields[1]),
                    Integer.parseInt(fields[2].trim())
            );
        } catch (IllegalArgumentException error) {
            throw new IllegalArgumentException(
                    "Fila " + lineNumber +
                            ": " + error.getMessage(), error);
        }
    }
}
