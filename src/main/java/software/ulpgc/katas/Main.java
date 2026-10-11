package software.ulpgc.katas;

import software.ulpgc.katas.io.EmployeeLoader;
import software.ulpgc.katas.io.FileInputSource;
import software.ulpgc.katas.io.InputSource;
import software.ulpgc.katas.io.ResourceInputSource;
import software.ulpgc.katas.io.SummaryWriter;
import software.ulpgc.katas.io.TextSummaryWriter;
import software.ulpgc.katas.io.TsvEmployeeLoader;
import software.ulpgc.katas.service.DepartmentCounter;
import software.ulpgc.katas.service.EmployeeReport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Main {

    public static void main(String[] args) {

        if (args.length != 3) {
            System.err.println(
                    "Uso: <file|resource> <ruta|recurso> " +
                            "<console|fichero-salida>");
            return;
        }

        try (InputStream input =
                     source(args[0], args[1]).open()) {

            if (args[2].equals("console")) {
                generate(input, System.out);
            } else {
                try (OutputStream output =
                             Files.newOutputStream(Path.of(args[2]))) {
                    generate(input, output);
                }
            }

        } catch (IOException | IllegalArgumentException error) {
            System.err.println("Error: " + error.getMessage());
            System.exit(1);
        }
    }

    private static InputSource source(
            String type, String location
    ) {
        return switch (type) {

            case "file" ->
                    new FileInputSource(Path.of(location));

            case "resource" ->
                    new ResourceInputSource(location);

            default ->
                    throw new IllegalArgumentException(
                            "Origen no valido: " + type);
        };
    }

    private static void generate(
            InputStream input,
            OutputStream output
    ) throws IOException {

        EmployeeLoader loader = new TsvEmployeeLoader();
        SummaryWriter writer = new TextSummaryWriter();

        var report = new EmployeeReport(
                loader,
                new DepartmentCounter(),
                writer
        );

        report.generate(input, output);
    }
}
