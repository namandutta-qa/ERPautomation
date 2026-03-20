package com.erp;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.erp.base.BaseTest;
import com.erp.pages.VerifiedPage;
import com.erp.utils.ExtentManager;

public class VeifiedPageTest extends BaseTest {

    @Test
    public void verifyVerifiedTabAccessible() {

        VerifiedPage page = new VerifiedPage(driver);

        page.openVerifiedTab();
        Assert.assertTrue(page.areVerifiedPostsVisible());

        ExtentManager.getTest().pass("Verified tab loaded successfully");
    }

    @Test
    public void verifyVerifiedBadgeVisible() {

        VerifiedPage page = new VerifiedPage(driver);

        page.openVerifiedTab();
        Assert.assertTrue(page.isVerifiedBadgeVisible());

        ExtentManager.getTest().pass("Verified badge visible");
    }

    @Test
    public void verifyLikeCommentRepostBookmarkInVerifiedTab() {

        VerifiedPage page = new VerifiedPage(driver);

        page.openVerifiedTab();

        page.likePost();
        page.commentOnPost("Automation comment");
        page.repostPost();
        page.bookmarkPost();

        ExtentManager.getTest().pass("All engagement actions working in Verified tab");
    }
}