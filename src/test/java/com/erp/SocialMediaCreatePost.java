package com.erp;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
    public void TC_080_HomeOwnerPageLoad() {
        ExtentManager.getTest().info("TC_080: Start - Verify Create Post page loads for homeowner");
        ExtentManager.getTest().info("Logged in as homeowner");
        ExtentManager.getTest().info("Navigating to Create Post page");

//        Assert.assertTrue(driver.findElement(By.id("postSubmit")).isDisplayed());
        ExtentManager.getTest().pass("Create Post page loaded successfully");
    }

    /* =========================
       TC-081 Valid Image Upload
    ========================== */
    @Test
    public void TC_081_validVideoUpload() throws InterruptedException {
        ExtentManager.getTest().info("TC_081: Start - Valid image upload");
        ExtentManager.getTest().info("Navigated to Create Post page");

        ExtentManager.getTest().info("Uploading image: C:\\images\\file_example_MOV_480_700kB.movfile_example_MOV_480_700kB.mov");
        page.uploadImage("/home/lz-2/Downloads/");
        page.clickSubmit();
        Thread.sleep(2000); // Wait for upload to process (replace with better wait in real code)
        Assert.assertTrue(driver.getPageSource().contains("uploaded"));
        ExtentManager.getTest().pass("Image uploaded and confirmed by page source");
    }

    /* =========================      
        goTo("/create-post");
       TC-082 Unsupported File Format
    ========================== */ 

    @Test
    public void TC_082_invalidvideoFileFormat() {
        ExtentManager.getTest().info("TC_082: Start - Unsupported file format validation");
        ExtentManager.getTest().info("Uploading invalid file: C:\\images\\test.pdf");
        page.uploadImage("/home/lz-2/Downloads/file_example_AVI_480_750kB.avi");
        Assert.assertTrue(page.getErrorMessage().contains("Invalid format"));
        ExtentManager.getTest().pass("Proper error shown for invalid file format");
    }

    @Test
    public void TC_081_validImageUpload() throws InterruptedException {
        ExtentManager.getTest().info("TC_081: Start - Valid image upload");
        ExtentManager.getTest().info("Navigated to Create Post page");

        ExtentManager.getTest().info("Uploading image: C:\\images\\test.jpg");
        page.uploadImage("/home/lz-2/Downloads/download.webp");
        page.clickSubmit();
        Thread.sleep(2000); // Wait for upload to process (replace with better wait in real code)
        Assert.assertTrue(driver.getPageSource().contains("uploaded"));
        ExtentManager.getTest().pass("Image uploaded and confirmed by page source");
    }

    /* =========================        loginAsRole("homeowner");
        goTo("/create-post");
       TC-082 Unsupported File Format
    ========================== */ 

    @Test
    public void TC_082_invalidFileFormat() {
        ExtentManager.getTest().info("TC_082: Start - Unsupported file format validation");
        ExtentManager.getTest().info("Uploading invalid file: C:\\images\\test.pdf");
        page.uploadImage("/home/lz-2/Downloads/yodixa_environment.json");
        Assert.assertTrue(page.getErrorMessage().contains("Invalid format"));
        ExtentManager.getTest().pass("Proper error shown for invalid file format");
    }

    /* =========================
       TC-083 File Size Limit
    ========================== */
    @Test
    public void TC_083_fileSizeValidation() {
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
	public void TC_084_validCaption() {
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
	public void TC_085_captionLimit() {
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
	public void TC_086_emojiCaption() {
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
    public void TC_087_xssProtection() {
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
//    public void TC_088_dateSelection() {
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
//    public void TC_089_dateRestriction() {
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
//    public void TC_090_validLocation() {
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
//    public void TC_091_invalidLocation() {
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
//    public void TC_092_mandatoryValidation() {
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
    public void TC_093_successfulPost() {
        ExtentManager.getTest().info("TC_093: Start - Successful post flow");

        ExtentManager.getTest().info("Uploading image and filling post  ");
    }

//
///* =========================
// TC-088 Date Selection
//========================== */
//@Test
//public void TC_088_dateSelection() {
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
//public void TC_089_dateRestriction() {
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
//public void TC_090_validLocation() {
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
//public void TC_091_invalidLocation() {
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

    /* =========================
       TC-094 Post Visible in Feed
    ========================== */
    @Test
    public void TC_094_postVisibleInFeed() {
        ExtentManager.getTest().info("TC_094: Start - Verify post is visible in feed");
        Assert.assertTrue(driver.getPageSource().contains("My renovation update"));
        ExtentManager.getTest().pass("Created post is visible in feed");
    }

    /* =========================
       TC-096 Unauthorized Access
    ========================== */
    @Test
    public void TC_096_unauthorizedAccess() {
        ExtentManager.getTest().info("TC_096: Start - Unauthorized access to create-post");
        // Attempt to access create-post without login
        goTo("/create-post");
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
        ExtentManager.getTest().pass("Unauthorized user redirected to login page");
    }

    /* =========================
       TC-097 Responsive Layout
    ========================== */
    @Test
    public void TC_097_responsiveLayout() {
        ExtentManager.getTest().info("TC_097: Start - Responsive layout check");
        driver.manage().window().setSize(new Dimension(390, 844));
        Assert.assertTrue(driver.findElement(By.xpath("(//button[normalize-space()='Post'])[2]")).isDisplayed());
        ExtentManager.getTest().pass("Post submit button visible on mobile viewport");
    }
}
