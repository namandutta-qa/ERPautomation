package com.erp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

import com.erp.utils.WaitUtils;

public class VerifiedPage {

    WebDriver driver;
    WaitUtils wait;

    public VerifiedPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    private By verifiedTab = By.id("verifiedTab");
    private By verifiedPosts = By.cssSelector(".verified-post");
    private By verifiedBadge = By.cssSelector(".verified-badge");
    private By likeButton = By.cssSelector(".like-btn");
    private By commentBox = By.cssSelector(".comment-input");
    private By repostButton = By.cssSelector(".repost-btn");
    private By bookmarkButton = By.cssSelector(".bookmark-btn");

    public void openVerifiedTab() {
        wait.waitForClickability(verifiedTab).click();
        wait.waitForVisibility(verifiedPosts);
    }

    public boolean areVerifiedPostsVisible() {
        List<WebElement> posts = driver.findElements(verifiedPosts);
        return posts.size() > 0;
    }

    public boolean isVerifiedBadgeVisible() {
        return wait.waitForVisibility(verifiedBadge).isDisplayed();
    }

    public void likePost() {
        wait.waitForClickability(likeButton).click();
    }

    public void commentOnPost(String comment) {
        wait.waitForVisibility(commentBox).sendKeys(comment);
        driver.findElement(commentBox).submit();
    }

    public void repostPost() {
        wait.waitForClickability(repostButton).click();
    }

    public void bookmarkPost() {
        wait.waitForClickability(bookmarkButton).click();
    }
}