package com.erp;

import com.erp.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ScreenshotAttachTest extends BaseTest {

    @Test
    public void testFailureScreenshot() {
        // simple navigation to a blank page
        goTo("about:blank");
        // create a step to force screenshot on pass
        step("Open blank page", () -> {});
        // force a failure to trigger onTestFailure screenshot
        Assert.fail("Intentional failure to verify screenshot capture");
    }
}
