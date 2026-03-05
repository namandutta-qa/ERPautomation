package com.erp;

import org.openqa.selenium.By;
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

	private final String FEED_URL = "https://your-yodixa-url.com/feed";

	@BeforeMethod
	public void initPage() {
		feedPage = new SocialMediaFeedPage(driver);
		waitUtils = new WaitUtils(driver);
	}

	/*
	 * ========================= TC-100 Like Post ==========================
	 */
	@Test
	public void TC_100_likePost() {

		ExtentManager.getTest().info("TC_100: Verify user can like a post");

		loginAsRole("homeowner");
		driver.get(FEED_URL);

		WebElement likeBtn = waitUtils.waitForClickability(By.xpath("(//button[@id='likeBtn'])[1]"));

		WebElement likeCount = waitUtils.waitForVisibility(By.xpath("(//span[@class='likeCount'])[1]"));

		int before = Integer.parseInt(likeCount.getText());

		likeBtn.click();
		ExtentManager.getTest().info("Clicked like button");

		waitUtils.waitForTextToBePresent(likeCount, String.valueOf(before + 1));

		int after = Integer.parseInt(likeCount.getText());

		Assert.assertEquals(after, before + 1);
		ExtentManager.getTest().pass("Like applied successfully and count increased");
	}

	/*
	 * ========================= TC-101 Unlike Post ==========================
	 */
	@Test
	public void TC_101_unlikePost() {

		ExtentManager.getTest().info("TC_101: Verify user can unlike a post");

		loginAsRole("homeowner");
		driver.get(FEED_URL);

		WebElement likeBtn = waitUtils.waitForClickability(By.xpath("(//button[@id='likeBtn'])[1]"));

		WebElement likeCount = waitUtils.waitForVisibility(By.xpath("(//span[@class='likeCount'])[1]"));

		likeBtn.click();
		int likedCount = Integer.parseInt(likeCount.getText());

		likeBtn.click();
		ExtentManager.getTest().info("Clicked again to unlike");

		waitUtils.waitForTextToBePresent(likeCount, String.valueOf(likedCount - 1));

		int afterUnlike = Integer.parseInt(likeCount.getText());

		Assert.assertEquals(afterUnlike, likedCount - 1);
		ExtentManager.getTest().pass("Unlike functionality working correctly");
	}

	/*
	 * ========================= TC-110 Add Valid Comment ==========================
	 */
	@Test
	public void TC_110_addValidComment() {

		ExtentManager.getTest().info("TC_110: Add valid comment");

		loginAsRole("homeowner");
		driver.get(FEED_URL);

		String commentText = "Automation Comment " + System.currentTimeMillis();

		WebElement commentBox = waitUtils.waitForVisibility(By.xpath("(//input[@id='commentInput'])[1]"));

		commentBox.sendKeys(commentText);

		waitUtils.waitForClickability(By.xpath("(//button[@id='commentSubmit'])[1]")).click();

		WebElement addedComment = waitUtils.waitForVisibility(By.xpath("//p[text()='" + commentText + "']"));

		Assert.assertTrue(addedComment.isDisplayed());
		ExtentManager.getTest().pass("Comment added successfully");
	}

	/*
	 * ========================= TC-111 Empty Comment Validation* ==========================
	 */
	@Test
	public void TC_111_emptyCommentValidation() {

		ExtentManager.getTest().info("TC_111: Empty comment validation");

		loginAsRole("homeowner");
		driver.get(FEED_URL);

		waitUtils.waitForClickability(By.xpath("(//button[@id='commentSubmit'])[1]")).click();

		WebElement error = waitUtils.waitForVisibility(By.className("error"));

		Assert.assertTrue(error.isDisplayed());
		ExtentManager.getTest().pass("Empty comment validation displayed");
	}

	/*
	 * ========================= TC-120 Repost ==========================
	 */
	@Test
	public void TC_120_repostPost() {

		ExtentManager.getTest().info("TC_120: Verify repost");

		loginAsRole("homeowner");
		driver.get(FEED_URL);

		WebElement repostBtn = waitUtils.waitForClickability(By.xpath("(//button[@id='repostBtn'])[1]"));

		repostBtn.click();

		Assert.assertTrue(repostBtn.getAttribute("class").contains("active"));
		ExtentManager.getTest().pass("Repost successful");
	}

	/*
	 * ========================= TC-130 Save Post ==========================
	 */
	@Test
	public void TC_130_savePost() {

		ExtentManager.getTest().info("TC_130: Verify save functionality");

		loginAsRole("homeowner");
		driver.get(FEED_URL);

		WebElement saveBtn = waitUtils.waitForClickability(By.xpath("(//button[@id='saveBtn'])[1]"));

		saveBtn.click();

		Assert.assertTrue(saveBtn.getAttribute("class").contains("saved"));
		ExtentManager.getTest().pass("Post saved successfully");
	}

	/*
	 * ========================= TC-131 Unsave Post ==========================
	 */
	@Test
	public void TC_131_unsavePost() {

		ExtentManager.getTest().info("TC_131: Verify unsave functionality");

		loginAsRole("homeowner");
		driver.get(FEED_URL);

		WebElement saveBtn = waitUtils.waitForClickability(By.xpath("(//button[@id='saveBtn'])[1]"));

		saveBtn.click();
		saveBtn.click();

		Assert.assertFalse(saveBtn.getAttribute("class").contains("saved"));
		ExtentManager.getTest().pass("Unsave working correctly");
	}

	/*
	 * ========================= TC-140 Send Post ==========================
	 */
	@Test
	public void TC_140_sendPost() {

		ExtentManager.getTest().info("TC_140: Verify send functionality");

		loginAsRole("homeowner");
		driver.get(FEED_URL);

		waitUtils.waitForClickability(By.xpath("(//button[@id='sendBtn'])[1]")).click();

		WebElement toast = waitUtils.waitForVisibility(By.className("toast-message"));

		Assert.assertTrue(toast.getText().toLowerCase().contains("sent"));
		ExtentManager.getTest().pass("Post sent successfully");
	}

	/*
	 * ========================= TC-150 Unauthorized Like Attempt* ==========================
	 */
	@Test
	public void TC_150_unauthorizedLike() {

		ExtentManager.getTest().info("TC_150: Unauthorized like attempt");

		driver.get(FEED_URL);

		waitUtils.waitForUrlContains("login");

		Assert.assertTrue(driver.getCurrentUrl().contains("login"));
		ExtentManager.getTest().pass("Unauthorized user redirected to login");
	}
}
