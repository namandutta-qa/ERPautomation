package com.erp.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

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
        // Create a test for this thread if not already present
        if (ExtentManager.getTest() == null) {
            ExtentManager.createTest(testName);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // NO-OP: BaseTest will handle logging and attaching screenshots in @AfterMethod
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // NO-OP: BaseTest will handle logging and attaching screenshots in @AfterMethod
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // NO-OP: BaseTest or test code can log skips if desired
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // not implemented
    }
}