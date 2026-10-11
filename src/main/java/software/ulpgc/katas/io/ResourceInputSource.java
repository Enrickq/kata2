
package software.ulpgc.katas.io;

import java.io.IOException;
import java.io.InputStream;

public record ResourceInputSource(String name)
        implements InputSource {

    @Override
    public InputStream open() throws IOException {

        InputStream stream = ResourceInputSource.class
                .getClassLoader()
                .getResourceAsStream(name);

        if (stream == null) {
            throw new IOException(
                    "No existe el recurso: " + name);
        }

        return stream;
    }
}
