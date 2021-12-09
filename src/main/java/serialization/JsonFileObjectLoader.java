package serialization;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Load an object from file in JSON format.
 */
public class JsonFileObjectLoader<T> implements FileObjectLoader<T> {
    private final Class<T> clazz;

    /**
     * Construct a JsonFileObjectLoader.
     * @param clazz The class of the object to load.
     */
    public JsonFileObjectLoader(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Load serialized data from the given filepath.
     * @param filepath The path of the file containing the serialized data.
     * @return An object of type T representing the serialized data.
     */
    @Override
    public T load(String filepath) throws IOException {
        Gson gson = new Gson();
        Reader reader = new FileReader(filepath);
        JsonReader jsonReader = new JsonReader(reader);
        T data = gson.fromJson(jsonReader, clazz);
        reader.close();
        return data;
    }

    /**
     * Get a list of valid extensions accepted by this JsonFileObjectLoader.
     */
    @Override
    public String getExtensionFilter() {
        return ".json";
    }
}
