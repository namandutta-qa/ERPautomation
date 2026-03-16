package com.erp.base;

import java.lang.reflect.Method;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.erp.utils.ExtentManager;
import com.erp.utils.ScreenshotUtil;
import com.erp.utils.ExtentTestNGListener;

// WebDriverManager import
import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(ExtentTestNGListener.class)
public class BaseTest {

    public WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    // Centralized base URL; default can be overridden with -DbaseUrl=...
    protected String baseUrl;

    // Toggle to enable/disable attaching step-level screenshots to Extent steps.
    // Controlled via system property -DattachStepScreenshots=true|false (defaults to true)
    protected boolean attachStepScreenshots = Boolean.parseBoolean(System.getProperty("attachStepScreenshots", "true"));

    // Initialize report once
    @BeforeSuite
    public void startReport() {
        extent = ExtentManager.getInstance();
        // initialize baseUrl from system property or default
        baseUrl = System.getProperty("baseUrl", "https://yodixa.lusites.xyz");
    }

    // Setup browser before each test
    @Parameters({"browser"})
    @BeforeMethod
    public void setup(@Optional("chrome") String browser, Method method) {

        // Normalize
        String br = (browser == null) ? "chrome" : browser.toLowerCase();

        ChromeOptions chromeOptions = new ChromeOptions();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        EdgeOptions edgeOptions = new EdgeOptions();

        // allow toggling headless mode with -Dheadless=true
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            chromeOptions.addArguments("--headless=new");
            // Firefox uses headless argument
            firefoxOptions.addArguments("-headless");
            // Edge can use the same flag as chrome
            edgeOptions.addArguments("--headless=new");
        }

        // common options
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        firefoxOptions.addArguments("--no-sandbox");
        firefoxOptions.addArguments("--disable-dev-shm-usage");
        edgeOptions.addArguments("--no-sandbox");
        edgeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--use-fake-ui-for-media-stream");
        chromeOptions.addArguments("--use-fake-device-for-media-stream");
        chromeOptions.addArguments("--use-file-for-fake-video-capture=/path/selfie.y4m");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-autofill");
        chromeOptions.addArguments("--disable-password-manager");
        chromeOptions.addArguments("--remote-allow-origins=*");

        switch (br) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        try {
            driver.manage().window().maximize();
        } catch (Exception ignore) {
            // Some headless drivers may not support maximize; ignore
        }

        // Ensure ExtentTest exists for this thread before any @BeforeMethod logging
        if (ExtentManager.getTest() == null) {
            ExtentManager.createTest(method.getName());
        }

    }

    // Handle result after each test
    @AfterMethod
    public void tearDown(ITestResult result) {

        String screenshotPath = null;

        try {
            if (driver != null) {
                // capture file-based screenshot (for archival) but prefer Base64 for embedding
                screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName() + "_" + System.currentTimeMillis());
            }
        } catch (Exception e) {
            // Don't fail the tearDown on screenshot errors
            System.err.println("[BaseTest] Failed to capture test-level screenshot: " + e.getMessage());
            e.printStackTrace();
        }

        if (result.getStatus() == ITestResult.FAILURE) {
            if (ExtentManager.getTest() != null) {
                try {
                    // Prefer embedding base64 (ensures image shows up in HTML report regardless of path)
                    String base64 = null;
                    try {
                        base64 = ScreenshotUtil.captureScreenshotAsBase64(driver);
                    } catch (Exception ignore) {
                        // ignore base64 capture error - we'll try file path
                    }

                    if (base64 != null) {
                        ExtentManager.getTest().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
                        // also log file path if available so user can open it directly
                        if (screenshotPath != null) {
                            ExtentManager.getTest().info("Screenshot saved to: " + screenshotPath);
                        }
                    } else if (screenshotPath != null) {
                        try {
                            ExtentManager.getTest().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                            ExtentManager.getTest().info("Screenshot saved to: " + screenshotPath);
                        } catch (Exception e) {
                            // final fallback
                            ExtentManager.getTest().fail(result.getThrowable());
                        }
                    } else {
                        ExtentManager.getTest().fail(result.getThrowable());
                    }
                } catch (Exception e) {
                    ExtentManager.getTest().fail(result.getThrowable());
                }
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            if (ExtentManager.getTest() != null) {
                try {
                    String base64 = null;
                    try {
                        base64 = ScreenshotUtil.captureScreenshotAsBase64(driver);
                    } catch (Exception ignore) {
                    }

                    if (base64 != null) {
                        ExtentManager.getTest().pass("Test Passed", MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
                        if (screenshotPath != null) {
                            ExtentManager.getTest().info("Screenshot saved to: " + screenshotPath);
                        }
                    } else if (screenshotPath != null) {
                        try {
                            ExtentManager.getTest().pass("Test Passed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                            ExtentManager.getTest().info("Screenshot saved to: " + screenshotPath);
                        } catch (Exception e) {
                            ExtentManager.getTest().pass("Test Passed");
                        }
                    } else {
                        ExtentManager.getTest().pass("Test Passed");
                    }
                } catch (Exception e) {
                    ExtentManager.getTest().pass("Test Passed");
                }
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            if (ExtentManager.getTest() != null) {
                ExtentManager.getTest().skip("Test Skipped");
            }
        }

        if (driver != null) {
//            driver.quit();
        }

        // remove thread-local test
        ExtentManager.removeTest();
    }

    // Flush report once after entire suite
    @AfterSuite
    public void endReport() {
        extent.flush();
    }

    // Navigation helper: accepts either absolute URL or relative path (e.g., "/login" or "login")
    protected void goTo(String pathOrUrl) {
        String url;
        if (pathOrUrl == null) {
            url = baseUrl;
        } else if (pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://")) {
            url = pathOrUrl;
        } else {
            url = baseUrl + (pathOrUrl.startsWith("/") ? pathOrUrl : "/" + pathOrUrl);
        }

        driver.get(url);
        try {
            ExtentManager.getTest().info("Navigated to: " + url);
        } catch (Exception e) {
            // If extent test not ready, ignore logging to avoid breaking navigation
        }
    }

    protected String getBaseUrl() {
        return baseUrl;
    }

    // Login methods
    public void login(String email, String password) {
        goTo("/login");
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("loginBtn")).click();
    }

    public void loginAsRole(String role) {

        switch (role.toLowerCase()) {

        case "homeowner":
            login("homeowner@mailinator.com", "Password123");
            break;

        case "contractor":
            login("contractor@mailinator.com", "Password123");
            break;

        case "admin":
            login("admin@mailinator.com", "Password123");
            break;

        default:
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    /**
     * Utility to execute a test step with Extent reporting.
     * Example usage:
     *   step("Enter first name", () -> page.enterFirstName("Naman"));
     * The method will log the step, mark it passed on success, or failed on exception and rethrow.
     */
    protected void step(String name, Runnable action) {
        ExtentManager.getTest().info("STEP: " + name);
        try {
            action.run();

            // Capture screenshot after successful step and attach to report (if enabled and driver available)
            if (attachStepScreenshots) {
                try {
                    if (driver != null) {
                        // prefer base64 embedding for step screenshots as well
                        String base64 = null;
                        String stepPath = null;
                        try {
                            base64 = ScreenshotUtil.captureScreenshotAsBase64(driver);
                        } catch (Exception ignore) {
                        }
                        try {
                            stepPath = ScreenshotUtil.captureScreenshot(driver, name);
                        } catch (Exception ignore) {
                        }

                        if (base64 != null) {
                            ExtentManager.getTest().pass("PASS: " + name, MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
                            if (stepPath != null) {
                                ExtentManager.getTest().info("Step screenshot saved to: " + stepPath);
                            }
                        } else if (stepPath != null) {
                            ExtentManager.getTest().pass("PASS: " + name, MediaEntityBuilder.createScreenCaptureFromPath(stepPath).build());
                            ExtentManager.getTest().info("Step screenshot saved to: " + stepPath);
                        } else {
                            ExtentManager.getTest().pass("PASS: " + name);
                        }
                    } else {
                        ExtentManager.getTest().pass("PASS: " + name);
                    }
                } catch (Exception e) {
                    // If screenshot capture/attach fails, still mark the step as passed with message
                    ExtentManager.getTest().pass("PASS: " + name + " (screenshot failed: " + e.getMessage() + ")");
                }
            } else {
                ExtentManager.getTest().pass("PASS: " + name);
            }

        } catch (Throwable t) {
            // Attempt to capture a screenshot on step failure and attach (if enabled)
            if (attachStepScreenshots) {
                try {
                    if (driver != null) {
                        String base64 = null;
                        String stepPath = null;
                        try {
                            base64 = ScreenshotUtil.captureScreenshotAsBase64(driver);
                        } catch (Exception ignore) {
                        }
                        try {
                            stepPath = ScreenshotUtil.captureScreenshot(driver, name + "-failed");
                        } catch (Exception ignore) {
                        }

                        if (base64 != null) {
                            ExtentManager.getTest().fail("FAIL: " + name + " - " + t.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
                            if (stepPath != null) {
                                ExtentManager.getTest().info("Step screenshot saved to: " + stepPath);
                            }
                        } else if (stepPath != null) {
                            ExtentManager.getTest().fail("FAIL: " + name + " - " + t.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(stepPath).build());
                            ExtentManager.getTest().info("Step screenshot saved to: " + stepPath);
                        } else {
                            ExtentManager.getTest().fail("FAIL: " + name + " - " + t.getMessage());
                        }
                    } else {
                        ExtentManager.getTest().fail("FAIL: " + name + " - " + t.getMessage());
                    }
                } catch (Exception e2) {
                    ExtentManager.getTest().fail("FAIL: " + name + " - " + t.getMessage() + " (screenshot failed: " + e2.getMessage() + ")");
                }
            } else {
                ExtentManager.getTest().fail("FAIL: " + name + " - " + t.getMessage());
            }
            throw t;
        }
    }

    protected String randomemailgenerator() {
        // TODO Auto-generated method stub
        String prefix = "user" + System.currentTimeMillis();
        return prefix + "@mailinator.com";
    }

}
