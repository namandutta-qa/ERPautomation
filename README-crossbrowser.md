Cross-browser parallel test instructions

This project includes a TestNG suite (`testng-crossbrowser.xml`) that runs the `com.erp.Signup` tests in parallel across Chrome, Firefox, and Edge.

Run the cross-browser suite with Maven (default uses headful browsers):

```sh
mvn test
```

Recommended: run headless for CI or when you don't need a visible browser:

```sh
mvn test -Dheadless=true
```

Override base URL (default: https://your-yodixa-url.com):

```sh
mvn test -DbaseUrl=https://google.com -Dheadless=true
```

Notes:
- The suite uses WebDriverManager to download browser drivers automatically.
- The TestNG suite runs 3 tests in parallel (thread-count="3") so ensure your machine has sufficient resources.
- To run only a single browser, pass the `-Dtestng.suiteXmlFiles=testng-crossbrowser.xml` and edit the suite or create a custom suite with a single test.
