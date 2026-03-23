package com.erp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.erp.utils.ExtentManager;

public class BookmarkPage extends BasePage {

	public BookmarkPage(WebDriver driver) {
		super(driver);
	}

	// Static Locators
	private By newBtn = By.xpath("//button[normalize-space()='New']");
	private By selectBtn = By.xpath("//button[normalize-space()='Select']");
	private By deleteBtn = By.xpath("//button[normalize-space()='Delete']");
	private By cancelBtn = By.xpath("//button[normalize-space()='Cancel']");
	private By collectionInput = By.xpath("//input[@placeholder='Collection name']");
	private By createBtn = By.xpath("//button[normalize-space()='Create']");
	private By bookmarkedItems = By.xpath("//div[contains(@class,'border') and .//span[contains(text(),'@')]]");

	// Dynamic Locator Methods
	private By bookmarkByName(String name) {
		return By.xpath(String.format("//button[.//span[normalize-space()='%s']]", name));
	}

	private By bookmarkCountByName(String name) {
		return By.xpath(String.format("//button[.//span[normalize-space()='%s']]//div[string-length(text())>0]", name));
	}

	// Actions

	public void clickNew(String collectionName) {
		waitForClickability(newBtn).click();
		waitForVisible(collectionInput).sendKeys(collectionName);
		click(createBtn);

		ExtentManager.getTest().info("Clicked New and created collection: " + collectionName);
	}

	public void clickSelect() {
		click(selectBtn);
		ExtentManager.getTest().info("Clicked Select button");
	}

	public void clickBookmark(String name) {
		By locator = bookmarkByName(name);
		waitForClickability(locator).click();

		ExtentManager.getTest().info("Clicked bookmark: " + name);
	}

	public void clickDelete() {
		click(deleteBtn);
		ExtentManager.getTest().info("Clicked Delete button");
	}

	public void clickCancel() {
		click(cancelBtn);
		ExtentManager.getTest().info("Clicked Cancel button");
	}

	// Get Count

	public int getBookmarkCount(String name) {
		WebElement element = waitForVisible(bookmarkCountByName(name));
		String text = element.getText().trim();

		if (text.isEmpty()) {
			return 0;
		}

		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			throw new RuntimeException("Invalid bookmark count: " + text);
		}
	}

	public void verifyBookmarkCount(String name) {

		int uiCount = getBookmarkCount(name); // badge count
		System.out.println("Bookmark count validated successfully. UI Count = Actual Count = " + uiCount);

		int actualCount = getActualBookmarkItemsCount(); // real elements

		if (uiCount != actualCount) {
			throw new AssertionError("Bookmark count mismatch! UI Count: " + uiCount + " | Actual Items: " + actualCount

			);
		}

		ExtentManager.getTest().info("Bookmark count validated successfully. UI Count = Actual Count = " + uiCount

		);

	}

	public int getActualBookmarkItemsCount() {
		return driver.findElements(bookmarkedItems).size();
	}

	public void verifyBookmarkPresent(String name) {

		By bookmarkName = By.xpath("//span[normalize-space()='" + name + "']");

		boolean isPresent = driver.findElements(bookmarkName).size() > 0;

		if (!isPresent) {
			throw new AssertionError("Bookmark [" + name + "] is NOT present");
		}

		ExtentManager.getTest().info("Verified bookmark is present: " + name);
	}

	public void verifyBookmarkSelected(String name) {

		By selectedBookmark = By.xpath("//button[.//span[normalize-space()='" + name + "'] and @aria-pressed='true']");

		boolean isSelected = driver.findElements(selectedBookmark).size() > 0;

		if (!isSelected) {
			throw new AssertionError("Bookmark [" + name + "] is NOT selected");
		}

		ExtentManager.getTest().info("Verified bookmark is selected: " + name);
	}

	public void verifyBookmarkPresentOrValidation(String name) {

		By bookmark = By.xpath("//span[normalize-space()='" + name + "']");
		By errorMsg = By
				.xpath("//p[contains(@class,'error') or contains(text(),'invalid') or contains(text(),'limit')]");

		boolean isBookmarkPresent = driver.findElements(bookmark).size() > 0;
		boolean isErrorVisible = driver.findElements(errorMsg).size() > 0;

		if (isBookmarkPresent) {
			ExtentManager.getTest().info("Bookmark created successfully: " + name);
		} else if (isErrorVisible) {
			ExtentManager.getTest().info("Validation message displayed for bookmark: " + name);
		} else {
			throw new AssertionError("Neither bookmark created nor validation message shown for: " + name);
		}
	}
}