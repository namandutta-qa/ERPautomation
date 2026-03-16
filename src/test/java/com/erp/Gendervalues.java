package com.erp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import com.erp.base.BaseTest;

public class Gendervalues extends BaseTest {

    // Locator for gender dropdown
    private By gender = By.xpath("(//select)[5]");

    @BeforeMethod
    public void setupRole() {
        goTo("/signup");

        // Click Organization button
        WebElement button = driver.findElement(By.xpath("//button[.//h3[normalize-space()='Organization']]"));
        button.click();

        // Click Continue button
        WebElement continueBtn = driver.findElement(By.xpath("//button[normalize-space()='Continue']"));
        continueBtn.click();
    }

    // Method to get all dropdown values
    public List<String> getGenderDropdownValues() {
        List<String> values = new ArrayList<>();

        WebElement genderElement = driver.findElement(gender);
        Select select = new Select(genderElement);

        for (WebElement option : select.getOptions()) {
            values.add(option.getText());
        }

        return values;
    }

    @Test
    public void printGenderDropdownValues() {
        List<String> genderOptions = getGenderDropdownValues();

        System.out.println("Gender Dropdown Values:");
        for (String value : genderOptions) {
            System.out.println(value);
        }

        // Optional: Add assertion if you want to check expected values
        // Assert.assertTrue(genderOptions.contains("Male"));
        // Assert.assertTrue(genderOptions.contains("Female"));
    }
}