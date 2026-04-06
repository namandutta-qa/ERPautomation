package com.erp.utils;

import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Enhanced TestNG listener that ensures an Extent test exists for each test method
 * and attaches a RetryAnalyzer when not already specified. This keeps retry logic
 * centralized and visible.
 */
public class ExtentTestNGListener implements ITestListener, IAnnotationTransformer {

    @Override 
    public void onStart(ITestContext context) {
        // suite start
        ExtentManager.getInstance();
    }

    @Override
    public void onFinish(ITestContext context) {
        // suite finish - flush reports
        ExtentManager.getInstance().flush();
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

    // Attach RetryAnalyzer to tests that don't have one explicitly
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // Some TestNG versions don't expose a getter for the retry analyzer; set our analyzer
        // as a conservative default so retries are handled uniformly. This will be applied
        // only at runtime by TestNG when annotations are processed.
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}