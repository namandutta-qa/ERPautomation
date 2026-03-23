package com.erp.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SocialMediaFeedPage extends BasePage {

	WebDriver driver;

	public SocialMediaFeedPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// Locators (Update with real IDs)
	By likeButton = By.xpath("(//button[.//span[@class='text-xs']])[3]");
	By likeCount = By.xpath("//button[.//span[@class='text-xs']]"); // Adjust index as needed
	By commentBox = By.xpath("(//button[.//span[@class='text-xs']])[1]");
	By commentSubmit = By.id("commentSubmit");
	By enteredComment = By.xpath("//textarea[@placeholder='Write a comment…']");
	By repostButton = By.xpath("(//button[.//span[@class='text-xs']])[2]");
	By saveButton = By.xpath("//button[@aria-label='Bookmarks — choose collections']");
	By sendButton = By.xpath("//button[@type='submit']");

	public void clickLike() {

		List<WebElement> buttons = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(likeButton, 0));

		WebElement el = buttons.get(0);

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);

		// Get count BEFORE 
		String before = driver.findElements(likeCount).get(0).getText();
		System.out.println("Before Like Count: " + before);

		// Click
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
		System.out.println("Clicked LIKE");

		// ✅ Wait for ANY change OR retry-safe
		boolean changed = wait.until(driver -> {
			try {
				String after = driver.findElements(likeCount).get(0).getText();
				System.out.println("After Like Count " + after);
				return !after.equals(before);
			} catch (Exception e) {
				return false;
			}
		});

		if (!changed) {
			throw new RuntimeException("Like count did NOT change!");
		}
	}

	public void enterComment(String commentText) {
		driver.findElement(enteredComment).sendKeys(commentText);
	}

	public void countComment() {
		driver.findElement(commentBox).getText();
	}

	public void countlike() {
		driver.findElement(likeCount).getText();
	}

	public void clickComment() {
		waitForVisible(commentBox).click(); // Click to open comment box
	}

	public void submitComment() {
		driver.findElement(commentSubmit).click();
	}

	public void clickRepost() {
		driver.findElement(repostButton).click();
	}

	public void newcollection() {
		
		
		driver.findElement(saveButton).click();
		waitForClickability(By.xpath("//button[normalize-space()='Add new collection']")).click();
		
		waitForVisible(By.xpath("//input[@aria-label='Collection name']")).sendKeys("My Collection");
		
		waitForClickability(By.xpath("//button[normalize-space()='Create']")).click();
		
	}
	
	public void clickSave(String value) {
		waitForVisible(saveButton).click();
		waitForClickability(By.xpath("//button[.//span[normalize-space()='" + value + "']]")).click();
	}

	public void clickSend() {
		waitForVisible(sendButton).click();
	}
}