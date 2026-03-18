package com.erp.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.erp.utils.WaitUtils;

public class OrganizationSignupPage extends BasePage {
	private WaitUtils waitUtils;

	public OrganizationSignupPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	    this.waitUtils = new WaitUtils(driver); // Create WaitUtils instance

	}

	// Organization details
	private By legalName = By.xpath("//input[@placeholder='Acme Corp']");
	private By legalnameError = By.xpath("//p[text()='EIN must be in format XX-XXXXXXX']");
	private By ein = By.xpath("//input[@name='ein_tax_id']");
	private By einError = By.xpath("//p[text()='EIN must be in format XX-XXXXXXX']");
	private By address = By.xpath("//input[@placeholder='Enter proper starting address details'][1]");
	private By addressError = By.xpath("//p[text()='EIN must be in format XX-XXXXXXX']");
	private By postalCode = By.name("business_address_zip");
	private By postalcodeError = By.xpath("//p[text()='EIN must be in format XX-XXXXXXX']");
	public By selectIndustry = By.xpath("(//select)[1]");
	public By countryDropdown = By.xpath("(//select)[2]");
	public By stateDropdown = By.xpath("(//select)[3]");
	private By city = By.name("business_address_city");
	private By state = By.name("businessState");
	// Personal details
	private By firstName = By.name("firstname");
	private By firstnameError = By.xpath("//p[text()='Last name must be at least 2 characters']");
	private By lastnameError = By.xpath("//p[text()='First name must be at least 2 characters']");
	private By middleName = By.xpath("//input[@id='_r_2_-form-item']");
	private By lastName = By.name("lastname");
	public By email = By.xpath("//input[@placeholder='you@example.com']");
	private By password = By.xpath("//input[@placeholder='••••••••']");
	private By username = By.xpath("//input[@placeholder='Enter first & last name above to generate']");
	private By emailError = By.xpath("//p[text()='Please enter a valid email address']");
	private By passwordError = By.xpath("//p[text()='Password must be at least 8 characters']");
	private By phone = By.xpath("//input[@placeholder='Phone number']");
	private By phoneError = By.xpath("//p[text()='Please enter a valid phone number']");
	private By dob = By.xpath("//button[@name='dob']");
	private By ownerdob = By.xpath("//button[@name='owner_dob']");
	private By DOBError = By.xpath("//p[text()='Date of birth is required']");
	private By TermsLink = By.xpath("//a[normalize-space()='Terms of Service']");
	private By PrivacyLink = By.xpath("//a[normalize-space()='Privacy Policy']");

	public By Country = By.xpath("(//select)[4]");
	public By birthcountryDropdown = By.xpath("//button[@id='_r_n_-form-item']");
	private By designation = By.xpath("(//select)[5]");
	private By Gender = By.xpath("(//select)[6]");
//	private By PerCountry = By.xpath("(//select)[13]");
	private By Peraddress = By.xpath("(//input[@placeholder='Enter proper starting address details'])[3]");
	private By PeraddressCountry = By.xpath("(//select)[12]");
	private By Perstate = By.xpath("(//select)[13]");
	private By PerCity = By.name("city");
	private By PerZip = By.name("zip");

	// Owner details
	private By ownerfirstName = By.name("owner_firstname");
	private By ownerlastName = By.name("owner_lastname");
	private By owneremail = By.name("owner_email");
	private By gender = By.xpath("(//select)[4]");
	private By ownercountrybirth = By
			.xpath("(//cd /home/lz-2/IdeaProjects/erp-automation && mvn -Dtest=com.erp.Signup testselect)[7]");
	private By owneraddress = By.xpath("(//input[@placeholder='Enter proper starting address details'])[2]");
	private By ownercountry = By.xpath("(//select)[7]");
	private By ownerstate = By.xpath("(//select)[8]");
	private By ownercity = By.name("owner_city");
	private By ownerpostalcode = By.name("owner_zip");
	// Current Address
	private By Currentaddress = By.name("street");
	private By CurrentpostalCode = By.name("postalCode");
	public By CurrentStateDropdown = By.xpath("//input[@placeholder='Search business address...']");
	private By Currentcity = By.name("city");
	private By Currentstate = By.name("state");
	// Document upload
	private By GovtID = By.xpath("//input[@type='file' and @aria-label='Upload Government ID']");
	private By AOI = By.xpath(
			"//h3[text()='Articles of Incorporation']/ancestor::div[contains(@class,'glass-card')]//input[@type='file']");
	private By IOD = By.xpath(
			"//h3[text()='Insurance Documentation']/ancestor::div[contains(@class,'glass-card')]//input[@type='file']");
	private By startCameraBtn = By.xpath("//button[contains(text(),'Start') or contains(text(),'Capture')]");
	private By captureBtn = By.xpath("//button[contains(text(),'Capture') or contains(text(),'Take photo')]");
	private By confirmBtn = By.xpath("//button[contains(text(),'Confirm') or contains(text(),'Continue')]");

	// Navigationcd /home/lz-2/IdeaProjects/erp-automation && mvn
	// -Dtest=com.erp.Signup test
	private By nextBtn = By.xpath("//button[normalize-space()='Create account']");

	// Review
	private By terms = By.xpath("//button[@role='checkbox']");
	private By privacy = By.xpath("//button[@id='_r_9_-form-item']");
	private By submitBtn = By.xpath("//button[normalize-space()='Submit for Verification']");
	private By confirmation = By.id("confirmationMsg");
	private By requiredErrors = By.xpath("//p[text()='Required']");
	private By uploadedFileName = By.xpath("//p[contains(text(),'.pdf')]");

	public String getEmailDuplicateError() {
		By toast = By.xpath("//section[@aria-label='Notifications alt+T']//*[@role='alert']");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(toast));

		wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, "")));

		String message = element.getText();
		System.out.println("Toast Message: " + message);

		return message;
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

	public String getEINError() {
		return waitForVisible(einError).getText();
	}

	public String getEmailError() {
		return driver.findElement(emailError).getText();
	}

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
		return waitForVisible(phoneError).getText();
	}

	public By nameValue(String name) {
		return By.xpath("//p[@class='font-medium' and contains(text(),'" + name + "')]");
	}

	public boolean isNameDisplayed(String expectedName) {
		try {
			return waitForVisible(nameValue(expectedName)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
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

	// *[@id="_r_4_-form-item"]
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

	public void selectdesignation(String value) {

		selectDropdown(designation, value);
	}

	public void selectPerGender(String value) {

		selectDropdown(Gender, value);

	}

//	public void selectPerCountry(String value) {
//
//		selectDropdown(PerCountry, value);
//	}

	public void selectPerAddresscountry(String value) {

		selectDropdown(PeraddressCountry, value);
	}

	public void selectPerstate(String value) {

		selectDropdown(Perstate, value);

	}

	public void selectCurrentstateDropdown(String value) {

		selectDropdown(CurrentStateDropdown, value);

	}

	public void enterPeraddress(String value) throws InterruptedException {
//		type(Peraddress, value);
		WebElement element = driver.findElement(Peraddress);
		element.clear();

		element.sendKeys(value);
		Thread.sleep(2000);
		element.sendKeys(Keys.ARROW_DOWN);
		element.sendKeys(Keys.ENTER);
	}

	public void enterPercity(String value) {
		type(PerCity, value);
	}

	public void enterPerZip(String value) {
		type(PerZip, value);
	}

	public void enterownerFirstName(String value) {
		type(ownerfirstName, value);
	}

	public void enterownerLastName(String value) {
		type(ownerlastName, value);
	}

	public void enteronweremail(String value) {
		type(owneremail, value);
	}

	public void enterowneraddress(String value) {
		type(owneraddress, value);
		WebElement element = driver.findElement(owneraddress);
		element.clear();

		element.sendKeys(value);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		element.sendKeys(Keys.ARROW_DOWN);
		element.sendKeys(Keys.ENTER);
	}

	public void enterownercity(String value) {
		type(ownercity, value);
	}

	public void enterownerzip(String value) {
		type(ownerpostalcode, value);
	}

	public void selectgender(String value) {

		selectDropdown(gender, value);

	}

	public void selectownercountry(String value) {

		selectDropdown(ownercountry, value);
	}

	public void selectownerState(String value) {

		selectDropdown(ownerstate, value);
	}

	public void selectownercountrybirth(String value) {

		selectDropdown(ownercountrybirth, value);

	}

	public void enterAddress(String value) {
		type(address, value);
		WebElement element = driver.findElement(address);
		element.clear();
		element.sendKeys(value);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		element.sendKeys(Keys.ARROW_DOWN);
		element.sendKeys(Keys.ENTER);

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

	public void selectIndustryDropdown(String value) {

		selectDropdown(selectIndustry, value);
	}

	public void enterFirstName(String value) {
		type(firstName, value);
	}

	@FindBy(name = "username")
	WebElement usernameField;

	public String waitForAutoUsername() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.attributeToBeNotEmpty(usernameField, "value"));

		return usernameField.getAttribute("value");
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
	public void ownerenterDOB(String value) {

		String[] parts = value.split(" ");

		String month = parts[0];
		String day = parts[1].replace(",", "");
		String year = parts[2];

		click(ownerdob);

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

	public void selectcountry(String value) {

		selectDropdown(Country, value);
	}

	public void selectbirthcountry(String value) {

		selectDropdown(birthcountryDropdown, value);
	}

	public boolean SelectGovtID(String path) {
		// Wait until the GovtID upload element is visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement uploadElement = wait.until(ExpectedConditions.presenceOfElementLocated(GovtID));

		// Send the file path once visible
		uploadElement.sendKeys(path);
		return true; 
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

	public void clickTermsLink() {
		click(TermsLink);
	}

	public void clickPrivacyLink() {
		click(PrivacyLink);
	}

	public boolean isFileUploaded() {
		try {
			return waitForVisible(uploadedFileName).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickConfirm() {
	    WebElement btn = waitUtils.waitForClickability(confirmBtn); // confirmBtnLocator = By locator of button
	    btn.click();
	}

	public boolean isSubmitButtonDisabled() {
		click(confirmBtn);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(submitBtn));
		return !button.isEnabled();
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