package com.erp;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.erp.base.BaseTest;
import com.erp.pages.FollowPage;
import com.erp.utils.ExtentManager;

public class FollowTest extends BaseTest {

    @Test
    public void verifyFollowButtonVisible() {

        loginAsRole("homeowner");
        driver.get("https://your-yodixa-url.com/profile/testuser");

        FollowPage followPage = new FollowPage(driver);

        ExtentManager.getTest().info("Checking if Follow button is visible");
        Assert.assertTrue(followPage.isFollowButtonVisible());

        ExtentManager.getTest().pass("Follow button is visible");
    }

    @Test
    public void verifyUserCanFollow() {

        loginAsRole("homeowner");
        driver.get("https://your-yodixa-url.com/profile/testuser");

        FollowPage followPage = new FollowPage(driver);

        int beforeCount = followPage.getFollowerCount();

        ExtentManager.getTest().info("Clicking Follow button");
        followPage.clickFollow();

        Assert.assertTrue(followPage.isFollowingDisplayed());

        int afterCount = followPage.getFollowerCount();

        Assert.assertEquals(afterCount, beforeCount + 1);

        ExtentManager.getTest().pass("User successfully followed and count updated");
    }

    @Test
    public void verifyUserCanUnfollow() {

        loginAsRole("homeowner");
        driver.get("https://your-yodixa-url.com/profile/testuser");

        FollowPage followPage = new FollowPage(driver);

        followPage.clickFollow(); // Ensure followed first
        int beforeCount = followPage.getFollowerCount();

        ExtentManager.getTest().info("Clicking Unfollow button");
        followPage.clickUnfollow();

        Assert.assertTrue(followPage.isFollowButtonVisible());

        int afterCount = followPage.getFollowerCount();

        Assert.assertEquals(afterCount, beforeCount - 1);

        ExtentManager.getTest().pass("User successfully unfollowed and count updated");
    }

    @Test
    public void verifyFollowPersistsAfterRefresh() {

        loginAsRole("homeowner");
        driver.get("https://your-yodixa-url.com/profile/testuser");

        FollowPage followPage = new FollowPage(driver);

        followPage.clickFollow();

        driver.navigate().refresh();

        Assert.assertTrue(followPage.isFollowingDisplayed());

        ExtentManager.getTest().pass("Follow state persists after refresh");
    }

    @Test
    public void verifyUserCannotFollowSelf() {

        loginAsRole("homeowner");
        driver.get("https://your-yodixa-url.com/profile/homeowner");

        FollowPage followPage = new FollowPage(driver);

        Assert.assertFalse(followPage.isFollowButtonVisible());

        ExtentManager.getTest().pass("User cannot follow themselves");
    }
}