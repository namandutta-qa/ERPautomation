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
            System.err.println("[ScreenshotUtil] Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}