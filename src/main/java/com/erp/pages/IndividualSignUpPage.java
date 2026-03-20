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

public class IndividualSignUpPage extends BasePage {

	public IndividualSignUpPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	private By firstName = By.name("firstname");
	private By firstnameError = By.xpath("//p[text()='Last name must be at least 2 characters']");
	private By lastnameError = By.xpath("//p[text()='First name must be at least 2 characters']");
	private By middleName = By.name("middlename");
	private By lastName = By.name("lastname");
	private By email = By.xpath("//input[@placeholder='you@example.com']");
	private By password = By.xpath("//input[@placeholder='••••••••']");
	private By username = By.xpath("//input[@placeholder='Enter first & last name above to generate']");
	private By emailError = By.xpath("//p[text()='Please enter a valid email address']");
	private By passwordError = By.xpath("//p[text()='Password must be at least 8 characters']");
	private By phone = By.xpath("//input[@placeholder='Phone number']");
	private By phoneError = By.xpath("//p[text()='Please enter a valid phone number']");
	private By dob = By.xpath("//button[@name='dob']");
	private By DOBError = By.xpath("//p[text()='Date of birth is required']");
	private By terms = By.xpath("(//button[@role='checkbox'])[1]");
	private By privacy = By.xpath("(//button[@role='checkbox'])[2]");
	private By TermsLink = By.xpath("//a[normalize-space()='Terms of Service']");
	private By PrivacyLink = By.xpath("//a[normalize-space()='Privacy Policy']");
	private By Gender = By.xpath("(//select)[1]");
	private By confirmBtn = By.xpath("//button[contains(text(),'Confirm') or contains(text(),'Continue')]");
	private By Peraddress = By.xpath("//input[@placeholder='Enter proper starting address details']");
	private By GovtID = By.xpath("//input[@type='file' and @aria-label='Upload Government ID']");
	private By submitBtn = By.xpath("//button[normalize-space()='Submit for Verification']");
	private By startVerificationBtn = By.xpath("//button[contains(text(),'Start Verification')]");
	private By continueverificationBtn = By.xpath("//button[normalize-space()='Continue']");
	private By acceptTerms = By.xpath("//button[@aria-label='Agree and continue']");


	public String getFirstError() {
		return driver.findElement(firstnameError).getText();
	}

	public String getLastError() {
		return waitForVisible(lastnameError).getText();
	}

	public String getPasswordError() {
		return driver.findElement(passwordError).getText();
	}

	public String getdobError() {
		return driver.findElement(DOBError).getText();
	}

	public String getEmailError() {
		return driver.findElement(emailError).getText();
	}

	public String getPhoneError() {
		return driver.findElement(phoneError).getText();
	}

	public void clickUsernameField() {
		click(username);
	}

	public void enterusername(String value) {
		type(username, value);
	}

	public void enterMiddleName(String value) {
		type(middleName, value);
	}

	public void enterLastName(String value) {
		type(lastName, value);
	}

	public void enterEmail(String value) {

		type(email, value);
	}

	public String getEmail() {
		return driver.findElement(email).getAttribute("value");
	}

	public void enterPasswordl(String value) {
		type(password, value);
	}

	public void enterPhone(String value) {
		type(phone, value);
	}

	public void enterDOB(String value) {

		String[] parts = value.split(" ");

		String month = parts[0];
		String day = parts[1].replace(",", "");
		String year = parts[2];

		click(dob);

		// Select month
		driver.findElement(By.xpath("//button[contains(text(),'" + month + "')]")).click();
		driver.findElement(By.xpath("//button[text()='" + month + "']")).click();

		// Select year
		driver.findElement(By.xpath("//button[contains(text(),'" + year + "')]")).click();
		driver.findElement(By.xpath("//button[text()='" + year + "']")).click();

		// Wait until calendar is clickable
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement dayElement = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='day' and text()='" + day + "']")));

		dayElement.click();
	}

	public void enterFirstName(String value) {
		type(firstName, value);
	}

	public void clickTermsLink() {
		click(TermsLink);
	}

	public void clickPrivacyLink() {
		click(PrivacyLink);
	}

	public void acceptTerms() {
		click(terms);
	}

	public void acceptPrivacy() {
		click(privacy);
	}

	public void clickConfirm() {
		click(confirmBtn);
	}

	public void selectPerGender(String value) {

		selectDropdown(Gender, value);

	}

	public void selectDropdown(By locator, String value) {

		WebElement dropdown = driver.findElement(locator);
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
	}

	public void enterPeraddress(String value) throws InterruptedException {
		WebElement element = driver.findElement(Peraddress);
		element.clear();

		element.sendKeys(value);
		Thread.sleep(2000);
		element.sendKeys(Keys.ARROW_DOWN);
		element.sendKeys(Keys.ENTER);
	}

	public void SelectGovtID(String path) {
		driver.findElement(GovtID).sendKeys(path);
	}

	public By reviewFieldValue(String label) {
		return By.xpath("//p[normalize-space()='" + label + "']/following-sibling::p[contains(@class,'font-medium')]");
	}

	public String getReviewFieldValue(String label) {
		return waitForVisible(reviewFieldValue(label)).getText().trim();
	}

	public void verifyReviewPageField(String label, String expectedValue) {
		String actualValue = getReviewFieldValue(label);

		Assert.assertEquals(actualValue, expectedValue, "Mismatch for field: " + label);
	}
	public void clickSubmit() {
		click(submitBtn);
	}
	public void captureSelfie() throws IOException {

		waitForClickability(startVerificationBtn).click();
		driver.switchTo().frame(waitForVisible(By.cssSelector("iframe")));

		waitForClickability(continueverificationBtn).click();
		waitForClickability(acceptTerms).click();
		// 👉 Stop automation here for manual verification
		System.out.println("Complete Sumsub verification manually and press ENTER to continue...");
		System.in.read();

		// After manual verification, resume if needed

		driver.switchTo().defaultContent();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[normalize-space()='Verified']")));
		System.out.println("Resuming Automation");
	}
}