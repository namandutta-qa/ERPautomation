package com.erp.pages;

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
	By fileInput = By.xpath("//input[@type='file']");

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
	public void enterCaption(String text) {
	    WebElement caption = waitForVisible(captionField);

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    js.executeScript(
	        "const element = arguments[0];" +
	        "const value = arguments[1];" +

	        // 🔥 Use native setter (THIS IS THE KEY FIX)
	        "const nativeInputValueSetter = Object.getOwnPropertyDescriptor(" +
	        "window.HTMLTextAreaElement.prototype, 'value').set;" +
	        "nativeInputValueSetter.call(element, value);" +

	        // Trigger React events
	        "element.dispatchEvent(new Event('input', { bubbles: true }));",
	        caption, text
	    );
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

	public void clickSubmit() {
		waitForClickability(submitBtn).click();
	}

	public String getErrorMessage() {
		return driver.findElement(errorMsg).getText();
	}
}