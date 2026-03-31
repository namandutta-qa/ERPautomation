This repository uses ExtentReports for test reporting.

Where reports & screenshots land
- Reports: <project-root>/test-output/extent-reports/extent-report-<timestamp>.html
- Screenshots: <project-root>/test-output/screenshots/<testName>.png

How it works
- `com.erp.utils.ExtentManager` creates a timestamped ExtentSparkReporter and manages thread-local ExtentTest instances.
- `com.erp.utils.ExtentTestNGListener` hooks into TestNG lifecycle. `BaseTest` also creates an ExtentTest in @BeforeMethod; the listener creates one only if missing.
- `com.erp.utils.ScreenshotUtil` saves screenshots to `test-output/screenshots` and the listener attaches them on failures.

Run tests (local machine)
1. Build: mvn -DskipTests=true package
2. Run tests: mvn test

Notes & safety
- Tests open real browsers (ChromeDriver). Running `mvn test` will launch browsers for each test; ensure you have ChromeDriver installed and on PATH or configured appropriately.
- If you prefer to run a single test class via Maven Surefire:

```sh
mvn -Dtest=ChatTest test
```

- The Extent report file is generated after the suite completes and will be under `test-output/extent-reports`.

If you'd like, I can:
- Add a TestNG XML (`testng.xml`) with included tests and the listener registered there.
- Add a small script to open the latest report automatically after test run.

Run test for screenshot run this command 'cd /home/lz-2/IdeaProjects/erp-automation && mvn -Dtest=com.erp.Signup test'
cd /home/lz-2 && mvn -Dtest=com.erp.SocialMediaCreatePost test
cd /home/lz-2/IdeaProjects/erp-automation && mvn -Dtest=com.erp.Signup test
cd /home/lz-2/IdeaProjects/erp-automation && mvn -Dtest=com.erp.AffiliateTest test
cd /home/lz-2/IdeaProjects/erp-automation && mvn -Dtest=com.erp.SocialMediaFeedPageTest test
cd /home/lz-2/IdeaProjects/erp-automation && mvn -Dtest=com.erp.Bookmark test
cd /home/lz-2/IdeaProjects/erp-automation && mvn -Dtest=com.erp.ChatTest test