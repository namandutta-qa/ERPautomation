package com.erp;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.erp.base.BaseTest;
import com.erp.pages.TrendingPage;
import com.erp.utils.ExtentManager;

public class TrendingPageTest extends BaseTest {

    @Test
    public void verifyTrendingTabAccessible() {

        loginAsRole("homeowner");
        TrendingPage page = new TrendingPage(driver);

        ExtentManager.getTest().info("Opening Trending tab");
        page.openTrendingTab();

        Assert.assertTrue(page.areTrendingPostsVisible());
        ExtentManager.getTest().pass("Trending tab loaded successfully");
    }

    @Test 
    public void verifyTrendingInfiniteScroll() {

        loginAsRole("homeowner");
        TrendingPage page = new TrendingPage(driver);

        page.openTrendingTab();
        page.scrollDown();

        Assert.assertTrue(page.areTrendingPostsVisible());
        ExtentManager.getTest().pass("Trending infinite scroll working");
    }
}