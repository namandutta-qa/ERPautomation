package com.erp.pages;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AffiliatePage extends BasePage {

	WebDriver driver;

	public AffiliatePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// Accept Terms
	private By acceptCheckbox = By.xpath("//input[@type='checkbox']");
	private By acceptBtn = By.xpath("//button[normalize-space()='Accept']");
	private By referralCount = By.xpath("//span[@class='referral-count']");
	private By termsHeader = By.xpath("//h1[contains(text(),'Terms')]");
	private By copyLinkBtn = By.xpath("//button[normalize-space()='Copy']");

	public void acceptcheckbox() {
		driver.findElement(acceptCheckbox).click();
	}

	public String acceptbutton() {
		return waitForVisible(acceptBtn).getText();
	}

	// Check if Terms page is displayed
	public boolean isTermsPageVisible() {
		return driver.findElements(termsHeader).size() > 0;
	}

	public String getAffiliateLink() {
		By affiliateLink = By.xpath("//div[contains(text(),'/ref/')]");

		String link = wait.until(ExpectedConditions.visibilityOfElementLocated(affiliateLink)).getText().trim();

		System.out.println("Affiliate Link: " + link);

		return link;
	}

	public String handleTermsAndGetLink() {

		if (isTermsPageVisible()) {
			acceptbutton();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'/ref/')]")));
		}

		return getAffiliateLink();
	}

	public void clickcopy() {
		wait.until(ExpectedConditions.elementToBeClickable(copyLinkBtn)).click();
	}
}