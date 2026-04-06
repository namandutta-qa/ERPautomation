package com.erp.utils;

import org.openqa.selenium.By;

/**
 * Converts repository entries like `name>email` into a Selenium `By` locator.
 */
public class LocatorResolver {

    private final LocatorRepository repo;

    public LocatorResolver(LocatorRepository repo) {
        this.repo = repo;
    }

    public By resolve(String key) {
        String raw = repo.get(key);
        if (raw == null) {
            throw new IllegalArgumentException("Locator key not found: " + key);
        }
        String[] parts = raw.split(">", 2);
        String type = parts[0].trim();
        String value = parts.length > 1 ? parts[1].trim() : "";
        switch (type) {
            case "id":
                return By.id(value);
            case "name":
                return By.name(value);
            case "css":
                return By.cssSelector(value);
            case "xpath":
                return By.xpath(value);
            case "class":
                return By.className(value);
            case "tag":
                return By.tagName(value);
            default:
                throw new IllegalArgumentException("Unsupported locator type: " + type + " for key: " + key);
        }
    }
}
