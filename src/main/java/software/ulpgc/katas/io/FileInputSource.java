
package software.ulpgc.katas.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public record FileInputSource(Path path)
        implements InputSource {

    @Override
    public InputStream open() throws IOException {

        if (!Files.isRegularFile(path)) {
            throw new IOException(
                    "No existe el fichero: " + path);
        }

        return new FileInputStream(path.toFile());
    }
}
