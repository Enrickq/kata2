
package software.ulpgc.katas.io;

import software.ulpgc.katas.domain.Department;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public final class TextSummaryWriter implements SummaryWriter {

    @Override
    public void write(
            Map<Department, Long> counts,
            OutputStream output
    ) throws IOException {

        OutputStreamWriter writer = new OutputStreamWriter(
                output, StandardCharsets.UTF_8);

        long total = counts.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();

        writer.write(
                "Total de empleados: " + total + "\n");

        var entries = new ArrayList<>(counts.entrySet());

        entries.sort(Comparator.comparing(
                entry -> entry.getKey().name()));

        for (var entry : entries) {
            writer.write(
                    entry.getKey().name() + ": " +
                            entry.getValue() + "\n");
        }

        writer.flush();
    }
}

