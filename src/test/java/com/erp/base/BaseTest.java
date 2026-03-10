package com.erp.base;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.erp.utils.ExtentManager;
import com.erp.utils.ScreenshotUtil;
import com.erp.utils.ExtentTestNGListener;

@Listeners(ExtentTestNGListener.class)
public class BaseTest {

    public WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    // Initialize report once
    @BeforeSuite
    public void startReport() {
        extent = ExtentManager.getInstance();
    }

    // Setup browser before each test
    @BeforeMethod
    public void setup(Method method) {

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // create test via ExtentManager (thread-safe)
        test = ExtentManager.createTest(method.getName());
    }

    // Handle result after each test
    @AfterMethod
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {

            try {
                if (driver != null) {
                    String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());

                    ExtentManager.getTest().fail(result.getThrowable()).addScreenCaptureFromPath(screenshotPath);
                } else {
                    ExtentManager.getTest().fail(result.getThrowable());
                }
            } catch (Exception e) {
                ExtentManager.getTest().fail("Screenshot failed: " + e.getMessage());
            }

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentManager.getTest().pass("Test Passed");
        }

        if (driver != null) {
            driver.quit();
        }

        // remove thread-local test
        ExtentManager.removeTest();
    }

    // Flush report once after entire suite
    @AfterSuite
    public void endReport() {
        extent.flush();
    }

    // Login methods
    public void login(String email, String password) {
        driver.get("https://your-yodixa-url.com/login");
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
}
