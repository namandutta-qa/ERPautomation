package com.erp.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public synchronized static ExtentReports getInstance() {

        if (extent == null) {
            try {
                String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                String userDir = System.getProperty("user.dir");
                String reportsDir = userDir + File.separator + "test-output" + File.separator + "extent-reports";
                File dir = new File(reportsDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String reportPath = reportsDir + File.separator + "extent-report-" + timestamp + ".html";

                ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
                reporter.config().setReportName("Automation Test Report");
                reporter.config().setDocumentTitle("Extent Report");
                reporter.config().setTheme(Theme.STANDARD);

                extent = new ExtentReports();
                extent.attachReporter(reporter);
                extent.setSystemInfo("Framework", "Selenium + TestNG");
                extent.setSystemInfo("Tester", "Automation Engineer");
                extent.setSystemInfo("OS", System.getProperty("os.name"));
                extent.setSystemInfo("Java Version", System.getProperty("java.version"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return extent;
    }

    // Create and store ExtentTest for current thread
    public static ExtentTest createTest(String testName) {
        ExtentReports rep = getInstance();
        ExtentTest test = rep.createTest(testName);
        extentTest.set(test);
        return test;
    }

    // Get ExtentTest for current thread
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    // Remove ExtentTest for current thread
    public static void removeTest() {
        extentTest.remove();
    }
}