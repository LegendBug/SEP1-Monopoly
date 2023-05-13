package mdc.tools;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ConfigLoader {
    /**
     * Loads configuration from a JSON file.
     *
     * @param path the path to the JSON file
     * @return the configuration
     */
    public static Config loadConfig(String path) {
        try (Reader reader = new FileReader(path)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
