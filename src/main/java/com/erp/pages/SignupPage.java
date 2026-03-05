package com.erp.pages;

import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.Select;

public class SignupPage {

    WebDriver driver;

    public SignupPage(WebDriver driver) {
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

    // Actions

    public void enterFirstName(String value) {
        driver.findElement(firstName).clear();
        driver.findElement(firstName).sendKeys(value);
    }

    public void enterMiddleName(String value) {
        driver.findElement(middleName).clear();
        driver.findElement(middleName).sendKeys(value);
    }

    public void enterLastName(String value) {
        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys(value);
    }

    public void enterEmail(String value) {
        driver.findElement(email).clear();
        driver.findElement(email).sendKeys(value);
    }

    public void enterPhone(String value) {
        driver.findElement(phone).clear();
        driver.findElement(phone).sendKeys(value);
    }

    public void enterDOB(String value) {
        driver.findElement(dob).sendKeys(value);
    }

    public void selectGender() {
        driver.findElement(genderMale).click();
    }

    public void selectCountry(String countryName) {
        new Select(driver.findElement(country)).selectByVisibleText(countryName);
    }

    public void enterStreet(String value) {
        driver.findElement(street).sendKeys(value);
    }

    public void enterCity(String value) {
        driver.findElement(city).sendKeys(value);
    }

    public void enterState(String value) {
        driver.findElement(state).sendKeys(value);
    }

    public void clickSubmit() {
        driver.findElement(submitBtn).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMsg).getText();
    }
}