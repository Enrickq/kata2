
package software.ulpgc.katas;

import software.ulpgc.katas.io.*;
import software.ulpgc.katas.service.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class Main {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Uso: <nombre-recurso>");
            return;
        }

        try (InputStream input =
                     new ResourceInputSource(args[0]).open()) {

            var output = new ByteArrayOutputStream();

            var report = new EmployeeReport(
                    new TsvEmployeeLoader(),
                    new DepartmentCounter(),
                    new TextSummaryWriter()
            );

            report.generate(input, output);

            System.out.print(
                    output.toString(StandardCharsets.UTF_8));

        } catch (IOException | IllegalArgumentException error) {
            System.err.println(
                    "Error: " + error.getMessage());
        }
    }
}
