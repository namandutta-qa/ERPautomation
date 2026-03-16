package com.erp.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * BasePage provides common helper methods using explicit waits.
 * All page objects should extend this class.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        // default timeout 10s - fine tune per project
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        WebElement el = waitForVisible(locator);
        try {
            el.click();
        } catch (Exception e) {
            // fallback to JS click for stubborn elements
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    protected void type(By locator, String text) {
        WebElement el = waitForVisible(locator);
        el.click();
        el.sendKeys(Keys.CONTROL + "a");
        el.sendKeys(Keys.DELETE);// select all existing text);
        el.sendKeys(text);
    }
    public void clickdropdown(By locator, String text) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        element.click();
    }

    protected void selectByVisibleText(By locator, String visibleText) {
        WebElement el = waitForVisible(locator);
        new Select(el).selectByVisibleText(visibleText);
    }

    protected String getText(By locator) {
        try {
            return waitForVisible(locator).getText();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return "";
        }
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
