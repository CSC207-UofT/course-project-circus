package serialization;

import java.io.IOException;

/**
 * Saves an object to file.
 */
public interface FileObjectSaver<T> {
    void save(T data, String filepath) throws IOException;
}
