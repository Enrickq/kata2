
package software.ulpgc.katas;

import software.ulpgc.katas.io.*;
import software.ulpgc.katas.service.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public final class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: <fichero TSV>");
            return;
        }

        try (InputStream input =
                     new FileInputSource(Path.of(args[0])).open()) {

            var report = new EmployeeReport(
                    new TsvEmployeeLoader(),
                    new DepartmentCounter(),
                    new TextSummaryWriter()
            );

            report.generate(input, System.out);

        } catch (IOException | IllegalArgumentException error) {
            System.err.println("Error: " + error.getMessage());
        }
    }
}
