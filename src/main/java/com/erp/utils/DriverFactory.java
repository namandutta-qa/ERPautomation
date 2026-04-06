package com.erp.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.devtools.DevTools;

public class DriverFactory {

	public static WebDriver createDriver(String userKey) {

	    ChromeOptions options = new ChromeOptions();

	    // ✅ CRITICAL FIXES
	    options.addArguments("--no-sandbox");
	    options.addArguments("--disable-dev-shm-usage");
	    options.addArguments("--disable-gpu");
	    options.addArguments("--remote-allow-origins=*");
	    options.addArguments("--disable-software-rasterizer");
	    // Optional but helps stability
	    options.addArguments("--disable-notifications");
	    options.addArguments("--disable-infobars");

	    options.addArguments("--start-maximized");

	    // Ensure chromedriver binary is available
	    WebDriverManager.chromedriver().setup();

	    ChromeDriver driver = new ChromeDriver(options);

	    // Attach NetworkLogger when explicitly enabled
	    boolean enableDevTools = Boolean.parseBoolean(System.getProperty("enableDevTools", "false"));
	    if (enableDevTools) {
	        try {
	            DevTools devTools = driver.getDevTools();
	            NetworkLogger logger = new NetworkLogger(devTools);
	            // Inject action correlation script into pages when driver is navigated
	            // Caller can execute NetworkLogger.getActionCorrelationScript() via JS if needed.
	            // Attempt a best-effort injection on about:blank (no-op) to register script on first navigation.
            try {
	                driver.executeScript(NetworkLogger.getActionCorrelationScript());
	            } catch (Exception ignore) {
	            }
	        } catch (Exception e) {
	            System.err.println("[DriverFactory] Failed to attach NetworkLogger: " + e.getMessage());
	        }
	    }

	    return driver;
	}
}