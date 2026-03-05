package com.erp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    By email = By.id("email");
    By password = By.id("password");
    By loginBtn = By.id("loginBtn");
    By errorMsg = By.id("error");
    By logoutBtn = By.id("logout");

    // Actions
    public void enterEmail(String userEmail) {
        driver.findElement(email).clear();
        driver.findElement(email).sendKeys(userEmail);
    }

    public void enterPassword(String userPassword) {
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(userPassword);
    }

    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMsg).getText();
    }

    public void clickLogout() {
        driver.findElement(logoutBtn).click();
    }
}