package com.erp.utils;

import org.openqa.selenium.WebElement;

public class CommonUtils {

    public static boolean validateColor(WebElement element, String expectedColor) {
        return element.getCssValue("color").equals(expectedColor);
    }

    public static boolean validateFontFamily(WebElement element, String expectedFont) {
        return element.getCssValue("font-family").contains(expectedFont);
    }
}