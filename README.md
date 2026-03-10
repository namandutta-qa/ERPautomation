# ERP Automation (short notes)

Screenshots
-----------
- Step-level and failure screenshots are saved to the project directory under:

  test-output/screenshots

  Each screenshot filename includes the test/step name and a timestamp so files won't collide.

Enable / Disable step-level screenshots
--------------------------------------
- By default, step-level screenshots are enabled. You can control this with a JVM system property when running Maven or your tests.

- To disable step-level screenshots, run Maven with:

```sh
mvn test -DattachStepScreenshots=false
```

- To explicitly enable step-level screenshots (default behavior):

```sh
mvn test -DattachStepScreenshots=true
```

Notes
-----
- Even when step-level screenshots are disabled, test-level failure screenshots are still captured and attached to the Extent report when a test fails.
- Extent HTML reports are saved under `test-output/extent-reports` (file name includes a timestamp).
- If you need smaller images or a different file location, update `ScreenshotUtil.captureScreenshot`.

