
package software.ulpgc.katas.io;

import java.io.IOException;
import java.io.InputStream;

public interface InputSource {
    InputStream open() throws IOException;
}
