package com.erp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    By email = By.name("email");
    By password = By.name("password");
    By loginBtn = By.xpath("//button[normalize-space()='Sign in']");
    By emailerrorMsg = By.xpath("//p[text()='Please enter a valid email address']");
    By passworderrorMsg = By.xpath("//p[text()='Password is required']");
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
        return driver.findElement(emailerrorMsg).getText();
    }

    public void clickLogout() {
        driver.findElement(logoutBtn).click();
    }
}