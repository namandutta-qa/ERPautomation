package com.erp.pages;

import com.erp.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

	WebDriver driver;
	WaitUtils waitUtils;

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		this.waitUtils = new WaitUtils(driver);
	}

	// Actions
	public void enterEmail(String userEmail) {
		WebElement emailField = waitUtils.waitForVisibility(resolveBy("login.email"));
		emailField.sendKeys(userEmail);
	}

	public void enterPassword(String userPassword) {

		WebElement passField = waitUtils.waitForVisibility(resolveBy("login.password"));
		passField.click();
		passField.sendKeys(userPassword);

	}

	public void clickLogin() {
		driver.findElement(resolveBy("login.button")).click();
	}

	public String getErrorMessage() {
		return driver.findElement(resolveBy("login.emailError")).getText();
	}

	public String getPasswordErrorMessage() {
		return driver.findElement(resolveBy("login.passwordError")).getText();
	}

	public void clickLogout() {
		driver.findElement(resolveBy("login.logout")).click();
	}
}