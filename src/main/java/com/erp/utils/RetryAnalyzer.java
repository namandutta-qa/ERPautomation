package com.erp.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Simple retry analyzer driven by system property `retryCount` (default 1).
 * Returns true to retry when the current retry count is less than maxRetries.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private final int maxRetryCount;

    public RetryAnalyzer() {
        int configured = 1;
        try {
            configured = Integer.parseInt(System.getProperty("retryCount", "1"));
        } catch (NumberFormatException ignored) {
        }
        this.maxRetryCount = Math.max(0, configured);
    }

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("[RetryAnalyzer] Retrying " + result.getMethod().getMethodName() + " (" + retryCount + "/" + maxRetryCount + ")");
            return true;
        }
        return false;
    }
}
