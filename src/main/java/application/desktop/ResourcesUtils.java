package application.desktop;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Resources utility methods.
 */
public class ResourcesUtils {
    /**
     * Load file from resources folder.
     * @param name of the file.
     * @return file data as a byte array.
     */
    public static byte[] loadFromResources(String name) {
        try {
            return Files.readAllBytes(Paths.get(Objects.requireNonNull(DesktopApplication.class.getResource(name)).toURI()));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
