package com.erp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

import com.erp.utils.WaitUtils;

public class TrendingPage {

    WebDriver driver;
    WaitUtils wait;

    public TrendingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    private By trendingTab = By.id("trendingTab");
    private By trendingPosts = By.cssSelector(".trending-post");
    private By likeCount = By.cssSelector(".like-count");

    public void openTrendingTab() {
        wait.waitForClickability(trendingTab).click();
        wait.waitForVisibility(trendingPosts);
    }

    public boolean areTrendingPostsVisible() {
        List<WebElement> posts = driver.findElements(trendingPosts);
        return posts.size() > 0;
    }

    public boolean isEngagementVisible() {
        return wait.waitForVisibility(likeCount).isDisplayed();
    }

    public void scrollDown() {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
}