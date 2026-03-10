package com.erp.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;

public class ExtentTestNGListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        // suite start
    }

    @Override
    public void onFinish(ITestContext context) {
        // suite finish
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        // Only create a test if one hasn't been created for this thread yet
        if (ExtentManager.getTest() == null) {
            ExtentManager.createTest(testName);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (ExtentManager.getTest() != null) {
            ExtentManager.getTest().log(Status.PASS, "Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Try to capture screenshot path from test's WebDriver (if available)
        Object testClass = result.getInstance();
        try {
            Field driverField = testClass.getClass().getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);

            Object driverObject = driverField.get(testClass);
            if (driverObject != null && driverObject instanceof WebDriver) {
                WebDriver driver = (WebDriver) driverObject;
                String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
                if (ExtentManager.getTest() != null) {
                    ExtentManager.getTest().fail(result.getThrowable()).addScreenCaptureFromPath(screenshotPath);
                }
                return;
            }
        } catch (Exception e) {
            // ignore and log without screenshot
        }

        if (ExtentManager.getTest() != null) {
            ExtentManager.getTest().fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (ExtentManager.getTest() != null) {
            ExtentManager.getTest().log(Status.SKIP, "Test Skipped");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // not implemented
    }
}