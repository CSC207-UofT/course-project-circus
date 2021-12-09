package serialization;

import java.io.IOException;

/**
 * Saves an object to file.
 */
@SuppressWarnings("SameReturnValue")
public interface FileObjectSaver<T> {
    /**
     * Save the given data at the specified filepath.
     * @param data The data to save.
     * @param filepath The path to the serialized data file.
     */
    void save(T data, String filepath) throws IOException;

    /**
     * Return a filter of valid file extensions for this FileObjectSaver.
     */
    String getExtensionFilter();
}
