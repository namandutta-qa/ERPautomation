package com.erp.pages;

import com.erp.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

	WebDriver driver;
	WaitUtils waitUtils;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.waitUtils = new WaitUtils(driver);
	}

	// Locators
	By email = By.name("email");
	By password = By.name("password");
	By loginBtn = By.xpath("//button[normalize-space()='Sign in']");
	By emailerrorMsg = By.xpath("//p[@id='_R_ainebn9esndlb_-form-item-message']");
	By passworderrorMsg = By.xpath("//p[@id='_R_einebn9esndlb_-form-item-message']");
	By logoutBtn = By.id("logout");

	// Actions
	public void enterEmail(String userEmail) {
		WebElement emailField = waitUtils.waitForVisibility(email);
		emailField.sendKeys(userEmail);
	}

	public void enterPassword(String userPassword) {

		WebElement passField = waitUtils.waitForVisibility(password);
		passField.click();
		passField.sendKeys(userPassword);

	}

	public void clickLogin() {
		driver.findElement(loginBtn).click();
	}

	public String getErrorMessage() {
		return driver.findElement(emailerrorMsg).getText();
	}

	public String getPasswordErrorMessage() {
		return driver.findElement(passworderrorMsg).getText();
	}

	public void clickLogout() {
		driver.findElement(logoutBtn).click();
	}
}