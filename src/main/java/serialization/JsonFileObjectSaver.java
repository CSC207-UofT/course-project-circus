package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Saves an object to file in JSON format.
 */
public class JsonFileObjectSaver<T> implements FileObjectSaver<T> {
    /**
     * Save the given data at the specified filepath.
     * @param data The data to save.
     * @param filepath The path to the serialized data file.
     */
    @Override
    public void save(T data, String filepath) throws IOException {
        Writer writer = new FileWriter(filepath);
        // Create Gson instance and convert to JSON
        Gson gson = new GsonBuilder().create();
        gson.toJson(data, writer);
        // Flush and close file
        writer.flush();
        writer.close();
    }

    /**
     * Get a list of valid extensions accepted by this JsonFileObjectLoader.
     */
    @Override
    public String getExtensionFilter() {
        return ".json";
    }
}
