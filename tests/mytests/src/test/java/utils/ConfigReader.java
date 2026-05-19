package utils;

import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ConfigReader {
    private static Map<String, Object> settings;

    static {
        try {
            Yaml yaml = new Yaml();
            
            String filePath = "settings.yml";
            File configFile = new File(filePath);
            
            if (!configFile.exists()) {
                throw new RuntimeException("File not found! Looked at exact path: " + configFile.getAbsolutePath());
            }

            InputStream inputStream = new FileInputStream(configFile);
            settings = yaml.load(inputStream);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to load settings.yml", e);
        }
    }

    public static String get(String key) {
        return (String) settings.get(key);
    }

    public static List<String> getList(String key) {
        return (List<String>) settings.get(key);
    }
}