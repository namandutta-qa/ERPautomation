package com.erp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.erp.utils.WaitUtils;

public class FollowPage {

    WebDriver driver;
    WaitUtils wait;

    public FollowPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    // ================= LOCATORS =================

    private By followButton = By.xpath("//button[contains(text(),'Follow')]");
    private By followingButton = By.xpath("//button[contains(text(),'Following')]");
    private By followerCount = By.id("followerCount");   // Replace with actual locator
    private By followingCount = By.id("followingCount"); // Replace with actual locator
    private By suggestionSection = By.id("suggestedUsers"); // Replace if needed

    // ================= ACTIONS =================

    public boolean isFollowButtonVisible() {
        return driver.findElement(followButton).isDisplayed();
    }

    public void clickFollow() {
        wait.waitForClickability(followButton);
        driver.findElement(followButton).click(); 
    }

    public void clickUnfollow() {
        wait.waitForVisibility(followingButton);
        driver.findElement(followingButton).click();
    }

    public boolean isFollowingDisplayed() {
        return driver.findElement(followingButton).isDisplayed();
    }

    public int getFollowerCount() {
        String count = driver.findElement(followerCount).getText();
        return Integer.parseInt(count);
    }

    public int getFollowingCount() {
        String count = driver.findElement(followingCount).getText();
        return Integer.parseInt(count);
    }

    public boolean isSuggestionSectionVisible() {
        return driver.findElement(suggestionSection).isDisplayed();
    }
}