
package software.ulpgc.katas.io;

import software.ulpgc.katas.domain.Department;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public interface SummaryWriter {

    void write(
            Map<Department, Long> counts,
            OutputStream output
    ) throws IOException;
}

