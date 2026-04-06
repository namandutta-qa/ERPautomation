package com.erp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads the `locators.properties` file from classpath and provides access to locator strings.
 * This allows tests to change a locator in one place instead of changing multiple Page Objects.
 */
public class LocatorRepository {

    private final Properties props = new Properties();

    public LocatorRepository() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("locators.properties")) {
            if (in != null) {
                props.load(in);
            } else {
                System.err.println("[LocatorRepository] locators.properties not found on classpath");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load locators.properties", e);
        }
    }

    public String get(String key) {
        return props.getProperty(key);
    }
}
