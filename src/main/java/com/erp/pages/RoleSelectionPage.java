package com.erp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RoleSelectionPage extends BasePage {

    public static final String INDIVIDUAL = "individual";
    public static final String ORGANIZATION = "organization";

    public RoleSelectionPage(WebDriver driver) {
        super(driver);
    }


    // Role locators
    private By individualRole = By.xpath("//button[.//h3[normalize-space()='Individual']]");
    private By organizationRole = By.xpath("//button[.//h3[normalize-space()='Organization']]");

    // Continue button
    private By continueBtn = By.xpath("//button[normalize-space()='Continue']");

    /**
     * Check role selection page visibility


    /**
     * Select role based on parameter
     */
    public void selectRoleAndContinue(String role) {

        if(role.equalsIgnoreCase(INDIVIDUAL)) {
            click(individualRole);
        }
        else if(role.equalsIgnoreCase(ORGANIZATION)) {
            click(organizationRole);
            System.out.println("Selected role: " + role);
        }
        else {
            throw new RuntimeException("Invalid role provided: " + role);
        }

        click(continueBtn);
    }
}