
package software.ulpgc.katas.service;

import software.ulpgc.katas.io.EmployeeLoader;
import software.ulpgc.katas.io.SummaryWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public record EmployeeReport(
        EmployeeLoader loader,
        DepartmentCounter counter,
        SummaryWriter writer
) {

    public void generate(
            InputStream input,
            OutputStream output
    ) throws IOException {

        var employees = loader.load(input);
        var counts = counter.count(employees);

        writer.write(counts, output);
    }
}
