package com.example.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads {@code api.properties} once and exposes values via static getters.
 * Tests call {@code ConfigReader.getProperty(...)} and never construct this class.
 */
public class ConfigReader {

    private static final String PROPERTIES_FILE = "api.properties";
    private static final Properties properties = new Properties();

    /*
     * Static block (not a method or constructor) because {@code properties} is static
     * and should load once for the whole JVM.
     *
     * A static { } block runs when the class is first used (first getProperty call
     * or any other first reference to ConfigReader). It runs once, before any
     * static method. That matches "read api.properties at startup and reuse it."
     *
     * Why not a constructor: a constructor runs every time you new ConfigReader().
     * Callers use ConfigReader.getProperty("baseUrl") instead. Static methods do
     * not go through the constructor, so load() in a constructor would never run.
     *
     * Why not a normal load() function: every test (or BaseTest) would have to
     * remember to call load() first. If someone calls getProperty before load(),
     * values would be empty/null. The static block does that automatically:
     * first use of the class → file is already loaded. You could lazy-load inside
     * getProperty, but that adds a check on every call. A static block is the
     * simple "load once, fail fast if the file is missing" version.
     */
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

    /**
     * Private so nobody can {@code new ConfigReader()}. This class only holds
     * shared config; it is not meant to be instantiated.
     */
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

    public static String getBaseUrl() {
        return getProperty("baseUrl");
    }

    public static String getCourseBaseTest() {
        return getProperty("rahulShettyBaseUrl");
    }

    public static String getCourseKey() {
        return getProperty("courseKey");
    }

    public static String getContentType() {
        return getProperty("contentType");
    }
}
