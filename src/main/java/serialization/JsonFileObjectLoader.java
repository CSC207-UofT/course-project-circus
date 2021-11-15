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

    public JsonFileObjectLoader(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T load(String filepath) throws IOException {
        Gson gson = new Gson();
        Reader reader = new FileReader(filepath);
        JsonReader jsonReader = new JsonReader(reader);
        T data = gson.fromJson(jsonReader, clazz);
        reader.close();
        return data;
    }
}
