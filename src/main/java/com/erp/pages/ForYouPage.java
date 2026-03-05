package com.erp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import com.erp.utils.WaitUtils;

public class ForYouPage {

	private WebDriver driver;
	private WaitUtils waitUtils;

	public ForYouPage(WebDriver driver) {
		this.driver = driver;
		this.waitUtils = new WaitUtils(driver);
	}

	// ================= LOCATORS =================

	private By forYouTab = By.id("forYouTab");
	private By posts = By.className("post");
	private By postAuthor = By.className("post-author");
	private By likeBtn = By.xpath("(//button[@id='likeBtn'])[1]");
	private By likeCount = By.xpath("(//span[@class='likeCount'])[1]");
	private By commentInput = By.xpath("(//input[@id='commentInput'])[1]");
	private By commentSubmit = By.xpath("(//button[@id='commentSubmit'])[1]");
	private By repostBtn = By.xpath("(//button[@id='repostBtn'])[1]");
	private By bookmarkBtn = By.xpath("(//button[@id='saveBtn'])[1]");
	private By toastMessage = By.className("toast-message");
	// ================= SEARCH LOCATORS =================
	private By searchBar = By.id("searchInput");
	private By searchButton = By.id("searchBtn");
	private By searchResults = By.className("search-result");
	private By noResultsMessage = By.className("no-results");

	// ================= TRENDING LOCATORS =================
	private By trendingSection = By.id("trendingSection");
	private By trendingHashtags = By.className("trending-tag");
	private By hashtagPostCount = By.className("hashtag-count");

	// ================= ACTION METHODS =================

	public void openForYouTab() {
		waitUtils.waitForClickability(forYouTab).click();
	}

	public int getPostCount() {
		return driver.findElements(posts).size();
	}

	public List<WebElement> getAllAuthors() {
		return driver.findElements(postAuthor);
	}

	public void likeFirstPost() {
		waitUtils.waitForClickability(likeBtn).click();
	}

	public int getLikeCount() {
		return Integer.parseInt(waitUtils.waitForVisibility(likeCount).getText());
	}

	public void addComment(String text) {
		waitUtils.waitForVisibility(commentInput).sendKeys(text);
		waitUtils.waitForClickability(commentSubmit).click();
	}

	public boolean isCommentVisible(String text) {
		return waitUtils.waitForVisibility(By.xpath("//p[text()='" + text + "']")).isDisplayed();
	}

	public void repostFirstPost() {
		waitUtils.waitForClickability(repostBtn).click();
	}

	public void bookmarkFirstPost() {
		waitUtils.waitForClickability(bookmarkBtn).click();
	}

	public boolean isBookmarkActive() {
		return waitUtils.waitForVisibility(bookmarkBtn).getAttribute("class").contains("saved");
	}

	public void scrollToBottom() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	public boolean isToastVisible() {
		return waitUtils.waitForVisibility(toastMessage).isDisplayed();
	}
	// ================= SEARCH METHODS =================

	public boolean isSearchBarVisible() {
	    return waitUtils.waitForVisibility(searchBar).isDisplayed();
	}

	public void search(String keyword) {
	    waitUtils.waitForVisibility(searchBar).clear();
	    waitUtils.waitForVisibility(searchBar).sendKeys(keyword);
	    waitUtils.waitForClickability(searchButton).click();
	}

	public int getSearchResultCount() {
	    return driver.findElements(searchResults).size();
	}

	public boolean isNoResultsMessageVisible() {
	    return waitUtils.waitForVisibility(noResultsMessage).isDisplayed();
	}

	public void clickFirstSearchResult() {
	    waitUtils.waitForClickability(searchResults).click();
	}

	// ================= TRENDING METHODS =================

	public boolean isTrendingSectionVisible() {
	    return waitUtils.waitForVisibility(trendingSection).isDisplayed();
	}

	public int getTrendingCount() {
	    return driver.findElements(trendingHashtags).size();
	}

	public void clickFirstTrendingHashtag() {
	    waitUtils.waitForClickability(trendingHashtags).click();
	}

	public int getHashtagPostCount() {
	    return Integer.parseInt(waitUtils.waitForVisibility(hashtagPostCount).getText());
	}
}