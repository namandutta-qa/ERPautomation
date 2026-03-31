package com.erp.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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

	    WebDriver driver = new ChromeDriver(options);

	    return driver;
	}
}