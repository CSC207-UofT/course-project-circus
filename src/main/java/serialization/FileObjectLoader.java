package serialization;

import java.io.IOException;

/**
 * Load an object from file.
 */
public interface FileObjectLoader<T> {
    T load(String filepath) throws IOException;
}
