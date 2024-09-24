package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyProvider {

    private static PropertyProvider instance;
    private final Properties properties;

    private PropertyProvider() {
        properties = new Properties();
        try (InputStream configStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (configStream == null) {
                System.err.println("config.properties not found in classpath");
                throw new FileNotFoundException("config.properties not found in classpath");
            }
            properties.load(configStream);
            System.out.println("config.properties successfully loaded.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load configuration file.");
        }
    }

    public static PropertyProvider getInstance() {
        if (instance == null) {
            instance = new PropertyProvider();
        }
        return instance;
    }

    public String getProperty(String key) {
        String property = properties.getProperty(key);
        if (property == null) {
            System.err.println("Property '" + key + "' not found in config.properties");
        }
        return property;
    }
}