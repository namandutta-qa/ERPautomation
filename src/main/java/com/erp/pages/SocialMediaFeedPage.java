package com.erp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SocialMediaFeedPage {

    WebDriver driver;

    public SocialMediaFeedPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators (Update with real IDs)
    By likeButton = By.id("likeBtn");
    By commentBox = By.id("commentInput");
    By commentSubmit = By.id("commentSubmit");
    By repostButton = By.id("repostBtn");
    By saveButton = By.id("saveBtn");
    By sendButton = By.id("sendBtn");

    // Actions
    public void clickLike() {
        driver.findElement(likeButton).click();
    }

    public void enterComment(String comment) {
        driver.findElement(commentBox).sendKeys(comment);
    }

    public void submitComment() {
        driver.findElement(commentSubmit).click();
    }

    public void clickRepost() {
        driver.findElement(repostButton).click();
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
    }

    public void clickSend() {
        driver.findElement(sendButton).click();
    }
}