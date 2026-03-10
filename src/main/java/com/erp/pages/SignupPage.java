package com.erp.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class SignupPage extends BasePage {

    WebDriver driver;

    public SignupPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    // Locators (Modify as per actual DOM)
    private By firstName = By.id("firstName");
    private By middleName = By.id("middleName");
    private By lastName = By.id("lastName");
    private By email = By.id("email");
    private By phone = By.id("phone");
    private By dob = By.id("dob");
    private By genderMale = By.id("male");
    private By country = By.id("country");
    private By street = By.id("street");
    private By city = By.id("city");
    private By state = By.id("state");
    private By submitBtn = By.id("submit");
    private By errorMsg = By.xpath("//span[@class='error']");

    // Password locators
    private By password = By.id("password");
    private By confirmPassword = By.id("confirmPassword");
    private By passwordToggle = By.cssSelector(".password-toggle, .toggle-password");

    // Additional locators to support tests
    private By postalCode = By.id("postalCode");
    private By countryOfBirthInput = By.id("currentCountry");
    private By govtIdUpload = By.id("govtIdUpload");
    private By identitySubmit = By.id("identitySubmit");
    private By removeFile = By.id("removeFile");
    private By previewSection = By.id("previewSection");
    private By biometricSection = By.id("biometricSection");
    private By terms = By.id("terms");
    private By privacy = By.id("privacy");
    private By finalSubmit = By.id("finalSubmit");

    // Actions

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

    public void enterPhone(String value) {
        type(phone, value);
    }

    public void enterDOB(String value) {
        type(dob, value);
    }

    public void selectGender() {
        click(genderMale);
    }

    public void selectCountry(String countryName) {
        selectByVisibleText(country, countryName);
    }

    public void enterStreet(String value) {
        type(street, value);
    }

    public void enterCity(String value) {
        type(city, value);
    }

    public void enterState(String value) {
        type(state, value);
    }

    public void clickSubmit() {
        click(submitBtn);
    }

    public String getErrorMessage() {
        return getText(errorMsg);
    }

    // New helper interactions used by tests
    public void enterPostalCode(String code) {
        type(postalCode, code);
    }

    public void selectCountryOfBirth(String countryName) {
        // countryOfBirthInput behaves like an input; set value then blur
        type(countryOfBirthInput, countryName);
    }

    public void uploadGovtId(String path) {
        waitForVisible(govtIdUpload).sendKeys(path);
    }

    public void clickIdentitySubmit() {
        click(identitySubmit);
    }

    public void clickRemoveFile() {
        click(removeFile);
    }

    public boolean isPreviewDisplayed() {
        return isDisplayed(previewSection);
    }

    public boolean isBiometricEnabled() {
        try {
            return waitForVisible(biometricSection).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public void acceptTerms() {
        click(terms);
    }

    public void acceptPrivacy() {
        click(privacy);
    }

    public void clickFinalSubmit() {
        click(finalSubmit);
    }

    // New visibility helpers for tests
    public boolean isTermsVisible() {
        return isDisplayed(terms);
    }

    public boolean isPrivacyVisible() {
        return isDisplayed(privacy);
    }

    // Password helpers added for test coverage
    public void enterPassword(String pwd) {
        type(password, pwd);
    }

    public void enterConfirmPassword(String pwd) {
        type(confirmPassword, pwd);
    }

    public void togglePasswordVisibility() {
        try {
            click(passwordToggle);
        } catch (Exception e) {
            // ignore - toggle might not exist in all layouts
        }
    }

    public String getPasswordFieldType() {
        try {
            return waitForVisible(password).getAttribute("type");
        } catch (Exception e) {
            return "";
        }
    }

    // New convenience method to check if a country exists in the select
    public boolean isCountryAvailable(String countryName) {
        try {
            WebElement el = waitForVisible(country);
            Select sel = new Select(el);
            return sel.getOptions().stream().anyMatch(o -> o.getText().trim().equalsIgnoreCase(countryName));
        } catch (Exception e) {
            return false;
        }
    }

}