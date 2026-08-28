package com.example.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final String PROPERTIES_FILE = "api.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IllegalStateException(
                        "Could not find " + PROPERTIES_FILE + " on the classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + PROPERTIES_FILE, e);
        }
    }

    private ConfigReader() {
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing property: " + key);
        }
        return value.trim();
    }

    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim();
    }
}
