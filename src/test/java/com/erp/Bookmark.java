package com.erp;

import com.erp.base.BaseTest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.erp.pages.BookmarkPage;
import com.erp.utils.WaitUtils;

public class Bookmark extends BaseTest {

	BookmarkPage bookmark;
	WaitUtils waitUtils;

	@BeforeMethod
	public void initPage() throws InterruptedException {
		bookmark = new BookmarkPage(driver);
		waitUtils = new WaitUtils(driver);
		loginAsRole("homeowner");
		goTo("bookmarks");

	}

	/*
	 * ========================= TC-100 Like Post ==========================
	 */
	@Test
	public void TC_001_VerifyCount() {
 
		step("Verify bookmark count", () -> {
			bookmark.verifyBookmarkCount("new");
		});

	}
	@Test
	public void TC_002_CreateNewBookmark() {
		step("Create new bookmark collection", () -> {
			bookmark.clickNew("My Collection");

		});
	}
	@Test
	public void TC_004_VerifyDefaultBookmarkExists() {

	    step("Verify default bookmark collection exists", () -> {
	        bookmark.verifyBookmarkPresent("new");
	    });
	}
	@Test
	public void TC_005_VerifyCreateBookmarkWithEmptyName() {

	    step("Try creating bookmark with empty name", () -> {
	        bookmark.clickNew("");
	    });
	}
	@Test
	public void TC_007_VerifyBookmarkSelection() {

	    step("Select bookmark collection", () -> {
	        bookmark.clickSelect();
	        bookmark.clickBookmark("new");
	        bookmark.verifyBookmarkSelected("new");
	    });
	}
	@Test
	public void TC_011_VerifyLongBookmarkNameHandling() {

	    String longName = "Bookmark_" + "A".repeat(100);

	    step("Create bookmark with long name", () -> {
	        bookmark.clickNew(longName);
	    });

	    step("Verify bookmark created or validation shown", () -> {
	        bookmark.verifyBookmarkPresentOrValidation(longName);
	    });
	}
	@Test
	public void TC_012_VerifySpecialCharacterBookmarkName() {

	    step("Create bookmark with special characters", () -> {
	        bookmark.clickNew("@#$%^&*()");
	    });

	    step("Verify handling of special characters", () -> {
	        bookmark.verifyBookmarkPresent("@#$%^&*()");
	    });
	}
	@Test
	public void TC_013_VerifyMultipleBookmarkCreation() {

	    step("Create multiple bookmarks", () -> {
	        bookmark.clickNew("Collection1");
	        bookmark.clickNew("Collection2");
	        bookmark.clickNew("Collection3");
	    });

	    step("Verify all bookmarks created", () -> {
	        bookmark.verifyBookmarkPresent("Collection1");
	        bookmark.verifyBookmarkPresent("Collection2");
	        bookmark.verifyBookmarkPresent("Collection3");
	    });
	}
	@Test
	public void TC_014_VerifyBookmarkPersistenceAfterRefresh() {

	    bookmark.clickNew("Persistent");

	    step("Refresh page", () -> {
	        driver.navigate().refresh();
	    });

	    step("Verify bookmark still exists after refresh", () -> {
	        bookmark.verifyBookmarkPresent("Persistent");
	    });
	}
	@Test
	public void TC_003_DeleteBookmark() {
		step("Select bookmark collection", () -> {
			bookmark.clickSelect();
			bookmark.clickBookmark("My Collection");
			bookmark.clickDelete();
			bookmark.clickDelete();
		});
	}
}
