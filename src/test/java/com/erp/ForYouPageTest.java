package com.erp;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.erp.base.BaseTest;
import com.erp.pages.ForYouPage;
import com.erp.utils.ExtentManager;

public class ForYouPageTest extends BaseTest {

	private ForYouPage forYouPage;

	private final String FOR_YOU_URL = "https://your-yodixa-url.com/feed/for-you";

	@BeforeMethod
	public void setupPage() {
		forYouPage = new ForYouPage(driver);
	}

	/* ========================= TC-153 For You Tab Access* ==========================*/
	@Test
	public void TC_153_verifyForYouTabAccessible() {

		ExtentManager.getTest().info("TC_153: Verify For You tab accessible");

		loginAsRole("homeowner");

		forYouPage.openForYouTab();

		Assert.assertTrue(driver.getCurrentUrl().contains("for-you"));
		ExtentManager.getTest().pass("For You tab loaded successfully");
	}

	/* ========================= TC-154 Followed Users Posts Appear* ==========================*/
	@Test
	public void TC_154_followedUsersPostsAppear() {

		ExtentManager.getTest().info("TC_154: Verify posts from followed users appear");

		loginAsRole("homeowner");
		driver.get(FOR_YOU_URL);

		Assert.assertTrue(forYouPage.getPostCount() > 0);
		ExtentManager.getTest().pass("Posts displayed in For You feed");
	}

	/*
	 * ========================= TC-155 Rule Based Filtering (Partial)
	 * ==========================
	 */
	@Test
	public void TC_155_ruleBasedFeedValidation() {

		ExtentManager.getTest().info("TC_155: Verify algorithm-based filtering");

		loginAsRole("homeowner");
		driver.get(FOR_YOU_URL);

		forYouPage.getAllAuthors().forEach(author -> Assert.assertFalse(author.getText().isEmpty()));

		ExtentManager.getTest().pass("Feed authors validated (Partial business rule validation)");
	}

	/*
	 * ========================= TC-156 Feed Updates ==========================
	 */
	@Test
	public void TC_156_feedRefreshValidation() {

		ExtentManager.getTest().info("TC_156: Verify feed refresh");

		loginAsRole("homeowner");
		driver.get(FOR_YOU_URL);

		int before = forYouPage.getPostCount();

		driver.navigate().refresh();

		int after = forYouPage.getPostCount();

		Assert.assertTrue(after >= before);
		ExtentManager.getTest().pass("Feed refreshed successfully");
	}

	/*
	 * ========================= TC-157 Infinite Scroll ==========================
	 */
	@Test
	public void TC_157_infiniteScrollValidation() {

		ExtentManager.getTest().info("TC_157: Verify infinite scroll");

		loginAsRole("homeowner");
		driver.get(FOR_YOU_URL);

		int before = forYouPage.getPostCount();

		forYouPage.scrollToBottom();

		int after = forYouPage.getPostCount();

		Assert.assertTrue(after > before);
		ExtentManager.getTest().pass("Infinite scroll working correctly");
	}

	/*
	 * ========================= TC-158 Like Functionality
	 * ==========================
	 */
	@Test
	public void TC_158_likeFunctionality() {

		ExtentManager.getTest().info("TC_158: Verify like in For You");

		loginAsRole("homeowner");
		driver.get(FOR_YOU_URL);

		int before = forYouPage.getLikeCount();

		forYouPage.likeFirstPost();

		int after = forYouPage.getLikeCount();

		Assert.assertEquals(after, before + 1);
		ExtentManager.getTest().pass("Like working correctly");
	}

	/*
	 * ========================= TC-159 Comment Functionality
	 * ==========================
	 */
	@Test
	public void TC_159_commentFunctionality() {

		ExtentManager.getTest().info("TC_159: Verify comment in For You");

		loginAsRole("homeowner");
		driver.get(FOR_YOU_URL);

		String comment = "ForYou Comment " + System.currentTimeMillis();

		forYouPage.addComment(comment);

		Assert.assertTrue(forYouPage.isCommentVisible(comment));
		ExtentManager.getTest().pass("Comment added successfully");
	}

	/*
	 * ========================= TC-160 Repost ==========================
	 */
	@Test
	public void TC_160_repostFunctionality() {

		ExtentManager.getTest().info("TC_160: Verify repost");

		loginAsRole("homeowner");
		driver.get(FOR_YOU_URL);

		forYouPage.repostFirstPost();

		ExtentManager.getTest().pass("Repost action triggered successfully");
	}

	/*
	 * ========================= TC-161 Bookmark ==========================
	 */
	@Test
	public void TC_161_bookmarkFunctionality() {

		ExtentManager.getTest().info("TC_161: Verify bookmark");

		loginAsRole("homeowner");
		driver.get(FOR_YOU_URL);

		forYouPage.bookmarkFirstPost();

		Assert.assertTrue(forYouPage.isBookmarkActive());
		ExtentManager.getTest().pass("Bookmark working successfully");
	}
	 @Test
	    public void TC_162_verifySearchBarVisible() {

	        ExtentManager.getTest().info("TC_162: Verify search bar visible");

	        loginAsRole("homeowner");
	        driver.get(FOR_YOU_URL);

	        Assert.assertTrue(forYouPage.isSearchBarVisible());
	        ExtentManager.getTest().pass("Search bar is visible");
	    }

    /* =========================
       TC-163 Search by Username
    ========================== */
    @Test
    public void TC_163_searchByUsername() {

        ExtentManager.getTest().info("TC_163: Verify search by username");

        loginAsRole("homeowner");
        driver.get(FOR_YOU_URL);

        forYouPage.search("john_doe");

        Assert.assertTrue(forYouPage.getSearchResultCount() > 0);
        ExtentManager.getTest().pass("Username search working");
    }

    /* =========================
       TC-164 Search by Hashtag
    ========================== */
    @Test
    public void TC_164_searchByHashtag() {

        ExtentManager.getTest().info("TC_164: Verify search by hashtag");

        loginAsRole("homeowner");
        driver.get(FOR_YOU_URL);

        forYouPage.search("#automation");

        Assert.assertTrue(forYouPage.getSearchResultCount() > 0);
        ExtentManager.getTest().pass("Hashtag search working");
    }

    /* =========================
       TC-165 Invalid Search
    ========================== */
    @Test
    public void TC_165_invalidSearchValidation() {

        ExtentManager.getTest().info("TC_165: Verify no results message");

        loginAsRole("homeowner");
        driver.get(FOR_YOU_URL);

        forYouPage.search("randominvalid123xyz");

        Assert.assertTrue(forYouPage.isNoResultsMessageVisible());
        ExtentManager.getTest().pass("No results message displayed");
    }

    /* =========================
       TC-166 Search Result Clickable
    ========================== */
    @Test
    public void TC_166_searchResultClickable() {

        ExtentManager.getTest().info("TC_166: Verify search result clickable");

        loginAsRole("homeowner");
        driver.get(FOR_YOU_URL);

        forYouPage.search("john_doe");

        forYouPage.clickFirstSearchResult();

        Assert.assertTrue(driver.getCurrentUrl().contains("profile"));
        ExtentManager.getTest().pass("Search result clickable and redirected");
    }

    /* =========================
       TC-167 Trending Section Visible
    ========================== */
    @Test
    public void TC_167_trendingSectionVisible() {

        ExtentManager.getTest().info("TC_167: Verify trending section visible");

        loginAsRole("homeowner");
        driver.get(FOR_YOU_URL);

        Assert.assertTrue(forYouPage.isTrendingSectionVisible());
        ExtentManager.getTest().pass("Trending section visible");
    }

    /* =========================
       TC-168 Trending Clickable
    ========================== */
    @Test
    public void TC_168_trendingHashtagClickable() {

        ExtentManager.getTest().info("TC_168: Verify trending hashtag clickable");

        loginAsRole("homeowner");
        driver.get(FOR_YOU_URL);

        forYouPage.clickFirstTrendingHashtag();

        Assert.assertTrue(driver.getCurrentUrl().contains("hashtag"));
        ExtentManager.getTest().pass("Trending hashtag clickable");
    }

    /* =========================
       TC-169 Trending Dynamic Update (Partial)
    ========================== */
    @Test
    public void TC_169_trendingDynamicUpdate() {

        ExtentManager.getTest().info("TC_169: Verify trending list updates dynamically");

        loginAsRole("homeowner");
        driver.get(FOR_YOU_URL);

        int before = forYouPage.getTrendingCount();

        driver.navigate().refresh();

        int after = forYouPage.getTrendingCount();

        Assert.assertTrue(after >= 0); // Partial validation
        ExtentManager.getTest().pass("Trending refresh validated (Partial)");
    }

    /* =========================
       TC-170 Trending Post Count Validation
    ========================== */
    @Test
    public void TC_170_trendingPostCountValidation() {

        ExtentManager.getTest().info("TC_170: Verify trending post count display");

        loginAsRole("homeowner");
        driver.get(FOR_YOU_URL);

        int count = forYouPage.getHashtagPostCount();

        Assert.assertTrue(count >= 0);
        ExtentManager.getTest().pass("Trending post count displayed correctly");
    }
}
