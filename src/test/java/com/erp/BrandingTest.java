package com.erp;

import com.erp.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.erp.pages.BrandingPage;

public class BrandingTest extends BaseTest {

    @Test
    public void verifyLogoVisibility() {
        BrandingPage brandingPage = new BrandingPage(driver);
        Assert.assertTrue(brandingPage.isLogoDisplayed(), "Logo is not visible");
    }

    @Test
    public void verifyLogoDimension() {
        BrandingPage brandingPage = new BrandingPage(driver);
        int width = brandingPage.getLogoWidth();
        int height = brandingPage.getLogoHeight();

        Assert.assertTrue(width > 0 && height > 0, "Logo dimension invalid");
    }

    @Test
    public void verifyPrimaryColor() {
        BrandingPage brandingPage = new BrandingPage(driver);
        String actualColor = brandingPage.getPrimaryColorOfElement();

        // Example RGB of #30b9ff
        String expectedColor = "rgba(48, 185, 255, 1)";
        Assert.assertEquals(actualColor, expectedColor, "Primary color mismatch");
    }

    @Test
    public void verifyFontFamily() {
        BrandingPage brandingPage = new BrandingPage(driver);
        String fontFamily = brandingPage.getHeadingFontFamily();

        Assert.assertTrue(fontFamily.contains("YourPrimaryFontName"), 
                "Incorrect font family used");
    }

    @Test
    public void verifyFontWeight() {
        BrandingPage brandingPage = new BrandingPage(driver);
        String fontWeight = brandingPage.getHeadingFontWeight();

        Assert.assertEquals(fontWeight, "700", "Font weight mismatch");
    }
}