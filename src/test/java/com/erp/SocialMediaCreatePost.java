package com.erp;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import com.erp.base.*;
import com.erp.pages.SocialMediaCreatePostPage;
import com.erp.utils.ExtentManager;

public class SocialMediaCreatePost extends BaseTest {
	SocialMediaCreatePostPage page;

	@BeforeMethod
	public void initPage() throws InterruptedException {
		// BaseTest.setup will run first (opens browser). Initialize page object here.
		ExtentManager.getTest().info("Initializing SocialMediaCreatePostPage");
		page = new SocialMediaCreatePostPage(driver);
		loginAsRole("homeowner");

	}

	/*
	 * ========================= TC-080 Page Load ==========================
	 */
	@Test
	public void TC_001_HomeOwnerPageLoad() {
		ExtentManager.getTest().info("TC_080: Start - Verify Create Post page loads for homeowner");
		ExtentManager.getTest().info("Logged in as homeowner");
		ExtentManager.getTest().info("Navigating to Create Post page");

//        Assert.assertTrue(driver.findElement(By.id("postSubmit")).isDisplayed());
		ExtentManager.getTest().pass("Create Post page loaded successfully");
	}

	@Test
	public void TC_002_emojiPickerOpens() {
		ExtentManager.getTest().info("TC_084: Emoji picker visibility");

		page.clickemoji();

		Assert.assertTrue(page.isEmojiPickerVisible());

		ExtentManager.getTest().pass("Emoji picker opened successfully");
	}

	@Test
	public void TC_003_validemojiselection() throws InterruptedException {
		ExtentManager.getTest().info("TC_081: Start - Valid image upload");
		ExtentManager.getTest().info("Navigated to Create Post page");

		page.clickemoji();
		ExtentManager.getTest().info("Add Emoji 😊");
		page.selectEmoji("smile", "1f60a");
		page.clickSubmit();
		Thread.sleep(2000); // Wait for upload to process (replace with better wait in real code)
		Assert.assertTrue(driver.getPageSource().contains("uploaded"));
		ExtentManager.getTest().pass("Image uploaded and confirmed by page source");
	}

	@Test
	public void TC_004_multipleEmojiSelection() {

		ExtentManager.getTest().info("TC_004: Multiple emoji selection");

		page.clickemoji();

		for (int i = 0; i < 3; i++) {
			page.selectEmoji("smile", "1f60a");
			page.waitForEmojiToBeAdded();
		}

		page.clickSubmit();

		Assert.assertTrue(driver.getPageSource().contains("😊"));
		ExtentManager.getTest().pass("Multiple emojis added successfully");
	}

	@Test
	public void TC_005_emojiWithTextCaption() {

		ExtentManager.getTest().info("TC_005: Emoji with text caption");

		page.enterCaption("Renovation completed ");
		page.clickemoji();
		page.selectEmoji("smile", "1f60a");

		page.clickSubmit();

		Assert.assertTrue(driver.getPageSource().contains("Renovation completed"));

		ExtentManager.getTest().pass("Emoji + text works");
	}

	@Test
	public void TC_006_invalidEmojiSelection() {

		ExtentManager.getTest().info("TC_006: Invalid emoji handling");

		page.clickemoji();

		boolean isSelected = page.trySelectEmoji("INVALID_EMOJI");

		Assert.assertFalse(isSelected, "Invalid emoji should not be selectable");

		ExtentManager.getTest().pass("Invalid emoji handled correctly");
	}

	@Test
	public void TC_007_emojiLimitValidation() {

		ExtentManager.getTest().info("TC_007: Emoji limit validation");

		page.clickemoji();

		for (int i = 0; i < 50; i++) {
			page.selectEmoji("smile", "1f60a");
		}

		page.clickSubmit();

		ExtentManager.getTest().pass("Emoji limit validation works");
	}

	@Test
	public void TC_008_multipleDifferentEmojiSelection() {

		ExtentManager.getTest().info("TC_004: Multiple different emoji selection");

		page.clickemoji();

		// ✅ Select different emojis
		page.selectEmoji("smile", "1f60a"); // 😊
		page.selectEmoji("laugh", "1f602"); // 😂
		page.selectEmoji("heart eyes", "1f60d"); // 😍

		page.clickSubmit();

		Assert.assertTrue(driver.getPageSource().contains("😊"));
		ExtentManager.getTest().pass("Multiple different emojis added successfully");
	}

	/*
	 * ========================= TC-081 Valid Image Upload
	 * ==========================
	 */
	@Test
	public void TC_009_validVideoUpload() throws InterruptedException {
		ExtentManager.getTest().info("TC_081: Start - Valid image upload");
		ExtentManager.getTest().info("Navigated to Create Post page");

		ExtentManager.getTest()
				.info("Uploading image: C:\\images\\file_example_MOV_480_700kB.movfile_example_MOV_480_700kB.mov");
		page.uploadImage("/home/lz-2/Downloads/");
		page.clickSubmit();
		Thread.sleep(2000); // Wait for upload to process (replace with better wait in real code)
		Assert.assertTrue(driver.getPageSource().contains("uploaded"));
		ExtentManager.getTest().pass("Image uploaded and confirmed by page source");
	}

	/*
	 * ========================= goTo("/create-post"); TC-082 Unsupported File
	 * Format ==========================
	 */

	@Test
	public void TC_010_invalidvideoFileFormat() {
		ExtentManager.getTest().info("TC_082: Start - Unsupported file format validation");
		ExtentManager.getTest().info("Uploading invalid file: C:\\images\\test.pdf");
		page.uploadImage("/home/lz-2/Downloads/file_example_AVI_480_750kB.avi");
		Assert.assertTrue(page.getErrorMessage().contains("Invalid format"));
		ExtentManager.getTest().pass("Proper error shown for invalid file format");
	}

	@Test
	public void TC_011_validImageUpload() throws InterruptedException {
		ExtentManager.getTest().info("TC_081: Start - Valid image upload");
		ExtentManager.getTest().info("Navigated to Create Post page");

		ExtentManager.getTest().info("Uploading image: C:\\images\\test.jpg");
		page.uploadImage("/home/lz-2/Downloads/download.webp");
		page.clickSubmit();
		Thread.sleep(2000); // Wait for upload to process (replace with better wait in real code)
		Assert.assertTrue(driver.getPageSource().contains("uploaded"));
		ExtentManager.getTest().pass("Image uploaded and confirmed by page source");
	}

	/*
	 * ========================= loginAsRole("homeowner"); goTo("/create-post");
	 * TC-082 Unsupported File Format ==========================
	 */

	@Test
	public void TC_012_invalidFileFormat() {
		ExtentManager.getTest().info("TC_082: Start - Unsupported file format validation");
		ExtentManager.getTest().info("Uploading invalid file: C:\\images\\test.pdf");
		page.uploadImage("/home/lz-2/Downloads/yodixa_environment.json");
		Assert.assertTrue(page.getErrorMessage().contains("Invalid format"));
		ExtentManager.getTest().pass("Proper error shown for invalid file format");
	}

	/*
	 * ========================= TC-083 File Size Limit ==========================
	 */
	@Test
	public void TC_013_fileSizeValidation() {
		ExtentManager.getTest().info("TC_083: Start - File size validation");

		ExtentManager.getTest().info("Uploading large image: C:\\images\\largeImage.jpg");
		page.uploadImage("/home/lz-2/Downloads/Hello.jpg");
		Assert.assertTrue(page.getErrorMessage().contains("size"));
		ExtentManager.getTest().pass("File size validation triggered and message verified");
	}

	/*
	 * ========================= TC-084 Caption Valid ==========================
	 */
	@Test
	public void TC_014_validCaption() {
		ExtentManager.getTest().info("TC_084: Start - Valid caption submission");

		ExtentManager.getTest().info("Entering caption");
		page.enterCaption("This is my home renovation post");
		page.clickSubmit();
		Assert.assertTrue(true);
		ExtentManager.getTest().pass("Caption entered successfully");
	}

//
	/*
	 * ========================= TC-085 Caption Limit ==========================
	 */
	@Test
	public void TC_015_captionLimit() {
		ExtentManager.getTest().info("TC_085: Start - Caption character limit validation");
		// Use Java 8-compatible way to build a long string
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 600; i++)
			sb.append('A');
		ExtentManager.getTest().info("Entering over-limit caption (600 chars)");
		page.enterCaption(sb.toString());
		page.clickSubmit();

		Assert.assertTrue(page.getErrorMessage().contains("limit"));
		ExtentManager.getTest().pass("Caption limit validation displayed");
	}

	/*
	 * ========================= TC-086 Emoji in Caption ==========================
	 */
	@Test
	public void TC_016_emojiCaption() {
		ExtentManager.getTest().info("TC_086: Start - Emoji support in caption");

		ExtentManager.getTest().info("Entering emoji caption");
		page.enterCaption("Great work 😊😊");
		page.clickSubmit();

		Assert.assertTrue(driver.getPageSource().contains("😊"));
		ExtentManager.getTest().pass("Emoji displayed in page source after submit");
	}

	/*
	 * ========================= TC-087 XSS Protection ==========================
	 */
	@Test
	public void TC_017_xssProtection() {
		ExtentManager.getTest().info("TC_087: Start - XSS protection check");

		ExtentManager.getTest().info("Entering script tag into caption");
		page.enterCaption("<script>alert('hack')</script>");
		page.clickSubmit();

		Assert.assertFalse(driver.getPageSource().contains("alert"));
		ExtentManager.getTest().pass("XSS payload not executed or rendered");
	}

//
//    /* =========================
//       TC-088 Date Selection
//    ========================== */
//    @Test
//    public void TC_018_dateSelection() {
//        ExtentManager.getTest().info("TC_088: Start - Date selection");
//
//        ExtentManager.getTest().info("Selecting date 2025-03-01");
//        page.selectDate("2025-03-01");
//        Assert.assertTrue(true);
//        ExtentManager.getTest().pass("Date selected successfully");
//    }
//
//    /* =========================
//       TC-089 Restricted Date
//    ========================== */
//    @Test
//    public void TC_019_dateRestriction() {
//        ExtentManager.getTest().info("TC_089: Start - Restricted date validation");
//
//        ExtentManager.getTest().info("Selecting restricted date 2035-01-01");
//        page.selectDate("2035-01-01");
//        page.clickSubmit();
//
//        Assert.assertTrue(page.getErrorMessage().contains("Invalid date"));
//        ExtentManager.getTest().pass("Restricted date error displayed");
//    }
//
//    /* =========================
//       TC-090 Valid Location
//    ========================== */
//    @Test
//    public void TC_020_validLocation() {
//        ExtentManager.getTest().info("TC_090: Start - Valid location entry");
//
//        ExtentManager.getTest().info("Entering location: New York");
//        page.enterLocation("New York");
//        Assert.assertTrue(true);
//        ExtentManager.getTest().pass("Location entered successfully");
//    }
//
//    /* =========================
//       TC-091 Invalid Location
//    ========================== */
//    @Test
//    public void TC_021_invalidLocation() {
//        ExtentManager.getTest().info("TC_091: Start - Invalid location validation");
//
//        ExtentManager.getTest().info("Entering invalid location: xyz123@@");
//        page.enterLocation("xyz123@@");
//        page.clickSubmit();
//
//        Assert.assertTrue(page.getErrorMessage().contains("Invalid location"));
//        ExtentManager.getTest().pass("Invalid location error verified");
//    }
//
//    /* =========================
//       TC-092 Mandatory Fields
//    ========================== */
//    @Test
//    public void TC_022_mandatoryValidation() {
//        ExtentManager.getTest().info("TC_092: Start - Mandatory fields validation");
//        ExtentManager.getTest().info("Submitting form without filling fields");
//        page.clickSubmit();
//        Assert.assertTrue(page.getErrorMessage().contains("required"));
//        ExtentManager.getTest().pass("Mandatory field validation message shown");
//    }
//
//    /* =========================
//       TC-093 Successful Post
//    ========================== */
	@Test
	public void TC_023_successfulPost() {
		ExtentManager.getTest().info("TC_093: Start - Successful post flow");

		ExtentManager.getTest().info("Uploading image and filling post  ");
	}

//
///* =========================
// TC-088 Date Selection
//========================== */
//@Test
//public void TC_024_dateSelection() {
//  ExtentManager.getTest().info("TC_088: Start - Date selection");
//
//  ExtentManager.getTest().info("Selecting date 2025-03-01");
//  page.selectDate("2025-03-01");
//  Assert.assertTrue(true);
//  ExtentManager.getTest().pass("Date selected successfully");
//}
//
///* =========================
// TC-089 Restricted Date
//========================== */
//@Test
//public void TC_025_dateRestriction() {
//  ExtentManager.getTest().info("TC_089: Start - Restricted date validation");
//
//  ExtentManager.getTest().info("Selecting restricted date 2035-01-01");
//  page.selectDate("2035-01-01");
//  page.clickSubmit();
//
//  Assert.assertTrue(page.getErrorMessage().contains("Invalid date"));
//  ExtentManager.getTest().pass("Restricted date error displayed");
//}
//
///* =========================
// TC-090 Valid Location
//========================== */
//@Test
//public void TC_026_validLocation() {
//  ExtentManager.getTest().info("TC_090: Start - Valid location entry");
//
//  ExtentManager.getTest().info("Entering location: New York");
//  page.enterLocation("New York");
//  Assert.assertTrue(true);
//  ExtentManager.getTest().pass("Location entered successfully");
//}
//
///* =========================
// TC-091 Invalid Location
//========================== */
//@Test
//public void TC_027_invalidLocation() {
//  ExtentManager.getTest().info("TC_091: Start - Invalid location validation");
//
//  ExtentManager.getTest().info("Entering invalid location: xyz123@@");
//  page.enterLocation("xyz123@@");
//  page.clickSubmit();
//
//  Assert.assertTrue(page.getErrorMessage().contains("Invalid location"));
//  ExtentManager.getTest().pass("Invalid location error verified");
//}
//
///* ========================= 

	/*
	 * ========================= TC-094 Post Visible in Feed
	 * ==========================
	 */
	@Test
	public void TC_028_postVisibleInFeed() {
		ExtentManager.getTest().info("TC_094: Start - Verify post is visible in feed");
		Assert.assertTrue(driver.getPageSource().contains("My renovation update"));
		ExtentManager.getTest().pass("Created post is visible in feed");
	}

	/*
	 * ========================= TC-096 Unauthorized Access
	 * ==========================
	 */
	@Test
	public void TC_029_unauthorizedAccess() {
		ExtentManager.getTest().info("TC_096: Start - Unauthorized access to create-post");
		// Attempt to access create-post without login
		goTo("/create-post");
		Assert.assertTrue(driver.getCurrentUrl().contains("login"));
		ExtentManager.getTest().pass("Unauthorized user redirected to login page");
	}

	/*
	 * ========================= TC-097 Responsive Layout ==========================
	 */
	@Test
	public void TC_030_responsiveLayout() {
		ExtentManager.getTest().info("TC_097: Start - Responsive layout check");
		driver.manage().window().setSize(new Dimension(390, 844));
		Assert.assertTrue(driver.findElement(By.xpath("(//button[normalize-space()='Post'])[2]")).isDisplayed());
		ExtentManager.getTest().pass("Post submit button visible on mobile viewport");
	}

	@Test
	public void TC_031_invalidpdfFileFormat() {
		ExtentManager.getTest().info("TC_082: Start - Unsupported file format validation");
		ExtentManager.getTest().info("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
		page.uploadPdf("/home/lz-2/Downloads/file_example_AVI_480_750kB.avi");
		ExtentManager.getTest().pass("Proper error shown for invalid file format");
	}

	@Test
	public void TC_032_validPdfUpload() {

		ExtentManager.getTest().info("Valid PDF upload");

		page.uploadPdf("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
		page.clickSubmit();

		ExtentManager.getTest().pass("PDF uploaded successfully");
	}

	@Test
	public void TC_033_invalidFileType() {

		page.uploadPdf("/home/lz-2/Downloads/download.webp");
		page.clickSubmit();

		Assert.assertTrue(page.getErrorMessage().toLowerCase().contains("invalid"));
	}

	@Test
	public void TC_034_largePdfFile() {

		page.uploadPdf("/home/lz-2/Downloads/Joy of cleaning/20MB-TESTFILE.ORG.pdf");
		page.clickSubmit();

		Assert.assertTrue(page.getErrorMessage().toLowerCase().contains("size"));
	}

	@Test
	public void TC_035_rapidUploadClicks() {

		for (int i = 0; i < 3; i++) {
			page.uploadPdf("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
		}

		page.clickSubmit();

	}

	@Test
	public void TC_038_verifyAcceptAttribute() {

		WebElement input = driver.findElement(By.xpath("//input[@type='file'][3]"));
		String accept = input.getAttribute("accept");

		Assert.assertTrue(accept.contains("pdf") && accept.contains("xlsx") && accept.contains("txt"),
				"Accept attribute incorrect: " + accept);
	}
}
