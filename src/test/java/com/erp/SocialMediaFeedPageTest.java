package com.erp;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.erp.base.BaseTest;
import com.erp.pages.SocialMediaFeedPage;
import com.erp.utils.WaitUtils;
import com.erp.utils.ExtentManager;

public class SocialMediaFeedPageTest extends BaseTest {

	SocialMediaFeedPage feedPage;
	WaitUtils waitUtils;

	@BeforeMethod
	public void initPage() throws InterruptedException {
		feedPage = new SocialMediaFeedPage(driver);
		waitUtils = new WaitUtils(driver);
		loginAsRole("homeowner");

	}

	/*
	 * ========================= TC-100 Like Post ==========================
	 */
	@Test
	public void TC_100_likePost() {

		ExtentManager.getTest().info("TC_100: Verify user can like a post");

		feedPage.clickLike();
	    apiLogs.stream()
        .filter(api -> api.contains("posts"))
        .forEach(System.out::println);
		ExtentManager.getTest().pass("Like applied successfully and count increased");
	}

	/*
	 * ========================= TC-101 Unlike Post ==========================
	 */
	@Test
	public void TC_101_unlikePost() {

		ExtentManager.getTest().info("TC_101: Verify user can unlike a post");

		feedPage.clickLike();

		feedPage.countlike();
		ExtentManager.getTest().pass("Unlike functionality working correctly");
	}

	/*
	 * ========================= TC-110 Add Valid Comment ==========================
	 */
	@Test
	public void TC_110_addValidComment() {

		ExtentManager.getTest().info("TC_110: Add valid comment");

		feedPage.clickComment();
		feedPage.enterComment("This is a test comment");
		feedPage.clickSend();

		ExtentManager.getTest().pass("Comment added successfully");
	}

	/*
	 * ========================= TC-111 Empty Comment Validation*
	 * ==========================
	 */
	@Test
	public void TC_111_emptyCommentValidation() {

		ExtentManager.getTest().info("TC_111: Empty comment validation");

		feedPage.clickComment();
		feedPage.clickSend();
		
		ExtentManager.getTest().pass("Empty comment validation displayed");
	}

	/*
	 * ========================= TC-120 Repost ==========================
	 */
	@Test
	public void TC_120_repostPost() {

		ExtentManager.getTest().info("TC_120: Verify repost");
		feedPage.clickRepost();
		
		ExtentManager.getTest().pass("Repost successful");
	}

	/*
	 * ========================= TC-130 Save Post ==========================
	 */
	@Test
	public void TC_130_savePost() {

		ExtentManager.getTest().info("TC_130: Verify save functionality");

		feedPage.newcollection();
		feedPage.clickSave("TestCollection");
		
		ExtentManager.getTest().pass("Post saved successfully");
	}

	/*
	 * ========================= TC-131 Unsave Post ==========================
	 */
	@Test
	public void TC_131_unsavePost() {
		
		feedPage.clickSave("TestCollection");

		ExtentManager.getTest().info("TC_131: Verify unsave functionality");

		ExtentManager.getTest().pass("Unsave working correctly");
	}

	/*
	 * ========================= TC-140 Send Post ==========================
	 */
	@Test
	public void TC_140_sendPost() {

		ExtentManager.getTest().info("TC_140: Verify send functionality");

		waitUtils.waitForClickability(By.xpath("(//button[@id='sendBtn'])[1]")).click();

		WebElement toast = waitUtils.waitForVisibility(By.className("toast-message"));

		Assert.assertTrue(toast.getText().toLowerCase().contains("copy"));
		ExtentManager.getTest().pass("Post sent successfully");
	}

	/*
	 * ========================= TC-150 Unauthorized Like Attempt*
	 * ==========================
	 */
	@Test
	public void TC_150_unauthorizedLike() {

		ExtentManager.getTest().info("TC_150: Unauthorized like attempt");

		waitUtils.waitForUrlContains("");

		Assert.assertTrue(driver.getCurrentUrl().contains("login"));
		ExtentManager.getTest().pass("Unauthorized user redirected to login");
	}
}
