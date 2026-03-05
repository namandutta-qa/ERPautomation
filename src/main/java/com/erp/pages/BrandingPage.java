package com.erp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BrandingPage {

    WebDriver driver;

    public BrandingPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By logo = By.xpath("//img[contains(@alt,'Yodixa')]");
    private By headingText = By.tagName("h1");

    // Methods

    public boolean isLogoDisplayed() {
        return driver.findElement(logo).isDisplayed();
    }

    public int getLogoWidth() {
        return driver.findElement(logo).getSize().getWidth();
    }

    public int getLogoHeight() {
        return driver.findElement(logo).getSize().getHeight();
    }

    public String getLogoBackgroundColor() {
        WebElement element = driver.findElement(logo);
        return element.getCssValue("background-color");
    }

    public String getHeadingFontFamily() {
        return driver.findElement(headingText).getCssValue("font-family");
    }

    public String getHeadingFontWeight() {
        return driver.findElement(headingText).getCssValue("font-weight");
    }

    public String getPrimaryColorOfElement() {
        return driver.findElement(headingText).getCssValue("color");
    }
}