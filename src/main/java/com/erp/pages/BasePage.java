package com.erp.pages;

import java.time.Duration;
import java.util.function.Function;

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

import com.erp.utils.LocatorRepository;
import com.erp.utils.LocatorResolver;

/**
 * BasePage provides common helper methods using explicit waits.
 * All page objects should extend this class.
 *
 * This class now supports resolving locators from a central `locators.properties`
 * via `resolveBy("some.key")`. This makes locator changes centralized and
 * reduces the amount of Page Object edits required when the AUT changes.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected LocatorResolver locatorResolver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        // default timeout 10s - fine tune per project
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // init repository/resolver (cheap to construct)
        this.locatorResolver = new LocatorResolver(new LocatorRepository());
    }

 // Custom condition wait
    public void waitForCondition(Function<WebDriver, Boolean> condition) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(condition);
    }
    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Helper to resolve a locator key from locators.properties
    protected By resolveBy(String key) {
        return locatorResolver.resolve(key);
    }

	public WebElement waitForClickability(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
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
