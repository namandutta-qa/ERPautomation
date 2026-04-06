Modernizing ERP Automation — Quick guide

What I changed/provided:
- Added a `RetryAnalyzer` and enhanced `ExtentTestNGListener` to provide consistent retry handling and ensure Extent tests are created per TestNG test.
- Left existing Extent reporting and screenshot utilities intact; listener now initializes and flushes reports.

How to run a quick smoke test locally (recommended):

- Run a single test method (headless optional):

```sh
# run a single test method
mvn -Dtest=com.erp.ChatTest#TC_208_SendTextMessage -Dheadless=true -DretryCount=1 test

# run whole testng suite (cross-browser defined in pom):
mvn -Dheadless=true -DretryCount=1 test
```

Notes & next steps:
- CI: Add a GitHub Actions workflow to run `mvn test` and upload `test-output/extent-reports` and screenshots as artifacts.
- Consider integrating Allure for richer API-level traces; Extent is currently used and works.
- You can control retries with `-DretryCount`. Setting `0` disables retries.

If you want, I can now add a minimal GitHub Actions workflow that runs tests in headless chrome and uploads test-output as artifacts.