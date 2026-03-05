package com.erp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SocialMediaCreatePostPage {

    WebDriver driver;

    public SocialMediaCreatePostPage(WebDriver driver) {
        this.driver = driver;
    } 

    // Locators
    By createPostMenu = By.id("createPostMenu");
    By imageUpload = By.id("imageUpload");
    By captionField = By.id("caption");
    By dateField = By.id("postDate");
    By locationField = By.id("location");
    By submitBtn = By.id("postSubmit");
    By errorMsg = By.id("error");

    // Actions
    public void openCreatePostPage() {
        driver.findElement(createPostMenu).click();
    }

    public void uploadImage(String path) {
        driver.findElement(imageUpload).sendKeys(path);
    }

    public void enterCaption(String text) {
        driver.findElement(captionField).clear();
        driver.findElement(captionField).sendKeys(text);
    }

    public void selectDate(String date) {
        WebElement dateInput = driver.findElement(dateField);
        dateInput.clear();
        dateInput.sendKeys(date);
    }

    public void enterLocation(String location) {
        driver.findElement(locationField).clear();
        driver.findElement(locationField).sendKeys(location);
    }

    public void clickSubmit() {
        driver.findElement(submitBtn).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMsg).getText();
    }
}