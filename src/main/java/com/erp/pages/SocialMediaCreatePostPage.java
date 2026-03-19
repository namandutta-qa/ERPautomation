package com.erp.pages;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SocialMediaCreatePostPage extends BasePage {

	WebDriver driver;

	public SocialMediaCreatePostPage(WebDriver driver) {
		super(driver);
		this.driver = driver;

	}

	// Locators
	By createPostMenu = By.id("//button[normalize-space()='Post'][2]");
	By imageUploadBtn = By.xpath("//button[@title='Add images (jpg, jpeg, png, webp)']");
	By videoUploadBtn = By.xpath("//button[@title='Add video (mp4)']");
	By captionField = By.xpath("//textarea[@placeholder=\"What's happening?\"]");
	By dateField = By.xpath("//body//div//button[6]");
	By locationField = By.xpath("//body//div//button[6]");
	By submitBtn = By.xpath("(//button[normalize-space()='Post'])[2]");
	By errorMsg = By.id("error");
	By emoji = By.xpath("//button[@title='Emoji' and @aria-label='Insert emoji']");
	By fileInput = By.xpath("//input[@type='file']");
	By pdf = By.xpath("//button[@title='Add document (xlsx, txt, pdf)']");

	// Emoji picker container (generic – adjust after inspect)
	By emojiPicker = By.xpath("//div[contains(@class,'emoji')]");

	// Specific emoji (dynamic)
	private String emojiOptionXpath = "//span[@data-unified='1f60a']";

	// Actions
	public void openCreatePostPage() {
		waitForVisible(createPostMenu).click();
	}

	public void uploadImage(String path) {
		// Actual upload happens here
		WebElement upload1 = wait.until(ExpectedConditions.presenceOfElementLocated(fileInput));
		upload1.sendKeys(path);

	}

	public void uploadvideo(String path) {
		// Actual upload happens here
		WebElement upload1 = wait.until(ExpectedConditions.presenceOfElementLocated(videoUploadBtn));
		upload1.sendKeys(path);

	}

	public void uploadPdf(String path) {

		WebElement fileInputElement = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[3]")));

		String fullPath;

		// 🔥 Fix: Handle both absolute & relative paths
		File file = new File(path);

		if (file.isAbsolute()) {
			fullPath = path;
		} else {
			fullPath = System.getProperty("user.dir") + path;
		}

		if (!new File(fullPath).exists()) {
			throw new RuntimeException("❌ File not found at path: " + fullPath);
		}

		fileInputElement.sendKeys(fullPath);
	}

	public void enterCaption(String text) {
		WebElement caption = waitForVisible(captionField);

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("const element = arguments[0];" + "const value = arguments[1];" +

		// 🔥 Use native setter (THIS IS THE KEY FIX)
				"const nativeInputValueSetter = Object.getOwnPropertyDescriptor("
				+ "window.HTMLTextAreaElement.prototype, 'value').set;" + "nativeInputValueSetter.call(element, value);"
				+

				// Trigger React events
				"element.dispatchEvent(new Event('input', { bubbles: true }));", caption, text);
	}

	public void selectDate(String date) {
		waitForVisible(dateField);
		WebElement dateInput = driver.findElement(dateField);
		dateInput.clear();
		dateInput.sendKeys(date);
	}

	public void enterLocation(String location) {
		driver.findElement(locationField).clear();
		waitForVisible(locationField).sendKeys(location);
	}

	public boolean isEmojiPickerVisible() {
		return waitForVisible(emojiPicker).isDisplayed();
	}

	public void clickemoji() {
		waitForClickability(emoji).click();
	}

	public void scrollEmojiContainer() {
		WebElement container = driver.findElement(By.xpath("//div[contains(@class,'epr-body')]"));

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", container);
	}

	public void selectEmoji(String emojiName, String code) {

		// 🔥 Ensure picker is open
		try {
			if (!driver.findElement(emojiPicker).isDisplayed()) {
				clickemoji();
			}
		} catch (Exception e) {
			clickemoji();
		}

		WebElement searchBox = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@aria-label='Type to search for an emoji']")));

		searchBox.clear();
		searchBox.sendKeys(emojiName);

		By emojiLocator = By.xpath("//span[@data-unified='" + code + "']");

		WebElement emoji = wait.until(ExpectedConditions.visibilityOfElementLocated(emojiLocator));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", emoji);
	}

	public void waitForEmojiToBeAdded() {
		wait.until(driver -> driver.findElement(By.xpath("//textarea")).getAttribute("value").length() > 0);
	}

	public boolean trySelectEmoji(String code) {
		try {
			By emojiLocator = By.xpath("//span[@data-unified='" + code + "']");
			WebElement emoji = wait.until(ExpectedConditions.visibilityOfElementLocated(emojiLocator));
			emoji.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void addEmojiToCaption(String emoji) {
		clickemoji(); // open picker
		waitForVisible(emojiPicker); // ensure picker loaded
		selectEmoji("", emoji); // select emoji
	}

	public void clickSubmit() {
		waitForClickability(submitBtn).click();
	}

	public String getErrorMessage() {
		return driver.findElement(errorMsg).getText();
	}
}