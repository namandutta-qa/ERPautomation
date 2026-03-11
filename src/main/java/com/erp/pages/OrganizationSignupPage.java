package com.erp.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class OrganizationSignupPage extends BasePage {

	public OrganizationSignupPage(WebDriver driver) {
		super(driver);
	}

	// Organization details
	private By legalName = By.xpath("//input[@name='organizationName']");
	private By legalnameError = By.xpath("//p[text()='EIN must be in format XX-XXXXXXX']");
	private By ein = By.xpath("//input[@name='ein']");
	private By einError = By.xpath("//p[text()='EIN must be in format XX-XXXXXXX']");
	private By address = By.name("businessStreet");
	private By addressError = By.xpath("//p[text()='EIN must be in format XX-XXXXXXX']");
	private By postalCode = By.name("businessPostalCode");
	private By postalcodeError = By.xpath("//p[text()='EIN must be in format XX-XXXXXXX']");
	public By countryDropdown = By.xpath("(//select)[2]");
	public By stateDropdown = By.xpath("(//select)[1]");
	private By city = By.name("businessCity");
	private By state = By.name("businessState");
	// Owner details
	private By firstName = By.name("firstname");
	private By middleName = By.name("middlename");
	private By lastName = By.name("lastname");
	private By email = By.name("email");
	private By password = By.name("password");

	private By emailError = By.xpath("//p[text()='Please enter a valid email address']");
	private By phone = By.name("phone");
	private By phoneError = By.xpath("//p[text()='Please enter a valid phone number']");
	private By dob = By.name("dateOfBirth");
	public By gender = By.xpath("(//select)[3]");
	public By birthcountryDropdown = By.xpath("//button[@id='_r_n_-form-item']");
    // Current Address
	private By Currentaddress = By.name("street");
	private By CurrentpostalCode = By.name("postalCode");
	public By CurrentcountryDropdown = By.xpath("(//select)[5]");
	private By Currentcity = By.name("city");
	private By Currentstate = By.name("state");

	// Document upload
	private By GovtID = By.xpath("//input[@type='file' and @accept='.jpg,.jpeg,.png,.pdf'][1]");
	private By AOI = By.xpath(
			"//h3[text()='Articles of Incorporation']/ancestor::div[contains(@class,'glass-card')]//input[@type='file']");
	private By IOD = By.xpath(
			"//h3[text()='Insurance Documentation']/ancestor::div[contains(@class,'glass-card')]//input[@type='file']");
	private By startCameraBtn = By.xpath("//button[contains(text(),'Start') or contains(text(),'Capture')]");
	private By captureBtn = By.xpath("//button[contains(text(),'Capture') or contains(text(),'Take photo')]");
	private By confirmBtn = By.xpath("//button[contains(text(),'Confirm') or contains(text(),'Continue')]");

	// Navigation
	private By nextBtn = By.xpath("//button[normalize-space()='Create account']");

	// Review
	private By terms = By.xpath("//button[@id='_r_p_-form-item']");
	private By privacy = By.xpath("//button[@id='privacy']");
	private By submitBtn = By.xpath("//button[normalize-space()='Submit for Verification']");
	private By confirmation = By.id("confirmationMsg");
	private By requiredErrors = By.xpath("//p[text()='Required']");

	public String getEINError() {
		return driver.findElement(einError).getText();
	}

	public String getEmailError() {
		return driver.findElement(emailError).getText();
	}

	public String getlegalnameError() {
		return driver.findElement(legalnameError).getText();
	}

	public String getaddressError() {
		return driver.findElement(addressError).getText();
	}

	public String getPostalError() {
		return driver.findElement(postalcodeError).getText();
	}

	public String getPhoneError() {
		return driver.findElement(phoneError).getText();
	}

	public void selectDropdown(By locator, String value) {

		WebElement dropdown = driver.findElement(locator);
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

	}

	public void enterLegalName(String value) {
		type(legalName, value);
	}

	public void enterEIN(String value) {
		type(ein, value);
	}

	public void CurrentAddress(String value) {
		type(Currentaddress, value);
	}

	public void CurrentpostalCode(String value) {
		type(CurrentpostalCode, value);
	}

	public void CurrentCity(String value) {
		type(Currentcity, value);
	}

	public void CurrentState(String value) {
		type(Currentstate, value);
	}

	public void City(String value) {
		type(city, value);
	}

	public void State(String value) {
		type(state, value);
	}

	public void selectCurrentCountryDropdown(String value) {

		selectDropdown(CurrentcountryDropdown, value);
	}

	public void enterAddress(String value) {
		type(address, value);
	}

	public void enterPostalCode(String value) {
		type(postalCode, value);
	}

	public void selectCountry(String value) {

		selectDropdown(countryDropdown, value);
	}

	public void selectState(String value) {

		selectDropdown(stateDropdown, value);
	}

	public void enterFirstName(String value) {
		type(firstName, value);
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
	public void enterPasswordl(String value) {
		type(password, value);
	}
	public void enterPhone(String value) {
		type(phone, value);
	}

	public void enterDOB(String value) {
		type(dob, value);
	}

	public void selectgender(String value) {

		selectDropdown(gender, value);
	}

	public void selectbirthcountry(String value) {

		selectDropdown(birthcountryDropdown, value);
	}

	public void SelectGovtID(String path) {
		driver.findElement(GovtID).sendKeys(path);
	}

	public void SelectAOI(String path1) {
		driver.findElement(AOI).sendKeys(path1);
	}

	public void SelectIOD(String path2) {
		driver.findElement(IOD).sendKeys(path2);
	}

	public void clickNext() {
		click(nextBtn);
	}

	public void acceptTerms() {
		click(terms);
	}

	public void acceptPrivacy() {
		click(privacy);
	}

	public void clickSubmit() {
		click(submitBtn);
	}

	public boolean isConfirmationDisplayed() {
		return isDisplayed(confirmation);
	}
	public boolean isRequiredErrorDisplayed() {

	    List<WebElement> errors = driver.findElements(requiredErrors);

	    return errors.size() > 0;
	}
	public void captureSelfie() {


	    driver.switchTo().frame(driver.findElement(By.cssSelector("iframe")));

	    wait.until(ExpectedConditions.elementToBeClickable(startCameraBtn)).click();
	    wait.until(ExpectedConditions.elementToBeClickable(captureBtn)).click();
	    wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();

	    driver.switchTo().defaultContent();
	}
}