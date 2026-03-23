package com.erp.utils;

import org.openqa.selenium.*;
import java.io.File;
import org.apache.commons.io.FileUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String name) {

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String userDir = System.getProperty("user.dir");
            String screenshotsDir = userDir + File.separator + "test-output" + File.separator + "screenshots";
            File dir = new File(screenshotsDir);
            if (!dir.exists()) { 
                dir.mkdirs();
            }

            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS").format(new Date());
            String safeName = name.replaceAll("[^a-zA-Z0-9-_\\. ]", "_");
            String path = screenshotsDir + File.separator + safeName + "-" + timestamp + ".png";

            FileUtils.copyFile(src, new File(path));

            // Log success so users see where screenshot was saved
            System.out.println("[ScreenshotUtil] Saved screenshot: " + path);
            return path;
        } catch (Exception e) {
            // Log the error to stderr so it is visible in build output
            System.err.println("[ScreenshotUtil] Failed to capture screenshot by file: " + e.getMessage());
            e.printStackTrace();
            try {
                // Try base64 fallback which works even when file copy fails
                String base64 = captureScreenshotAsBase64(driver);
                if (base64 != null) {
                    System.out.println("[ScreenshotUtil] Captured screenshot as base64 fallback");
                    return "data:image/png;base64," + base64;
                }
            } catch (Exception ex) {
                System.err.println("[ScreenshotUtil] Base64 fallback also failed: " + ex.getMessage());
            }
            return null;
        }
    }

    // New helper to return screenshot as Base64 (useful for embedding directly into reports)
    public static String captureScreenshotAsBase64(WebDriver driver) {
        try {
            String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            return base64;
        } catch (Exception e) {
            System.err.println("[ScreenshotUtil] Failed to capture screenshot as base64: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}