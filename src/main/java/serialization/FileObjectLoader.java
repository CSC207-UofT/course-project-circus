package serialization;

import java.io.IOException;
import java.util.List;

/**
 * Load an object from file.
 */
public interface FileObjectLoader<T> {
    /**
     * Load serialized data from the given filepath.
     * @param filepath The path of the file containing the serialized data.
     * @return An object of type T representing the serialized data.
     */
    T load(String filepath) throws IOException;

    /**
     * Return a filter of valid file extensions for this FileObjectSaver.
     */
    String getExtensionFilter();
}
