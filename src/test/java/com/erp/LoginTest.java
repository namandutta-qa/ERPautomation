package com.erp;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.erp.base.*;
import com.erp.pages.LoginPage;

public class LoginTest extends BaseTest {
	LoginPage loginPage;

	
	@BeforeMethod

	public void setupRole() {
		
//		rolePage = new RoleSelectionPage(driver);
		loginPage = new LoginPage(driver);
		goTo("/login");

//		rolePage.selectRoleAndContinue(RoleSelectionPage.ORGANIZATION);

	}

	/* ========================= TC-080 Login Page Load ==========================*/
	
	@Test
	public void TC_080_loginPageLoad() {
		goTo("/login");
		Assert.assertTrue(driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).isDisplayed());
	}

	/* ========================= TC-081 Valid Login ==========================*/
	@Test
	public void TC_081_validLogin() {

		System.out.println("Current URL before login: " + driver.getCurrentUrl());
		
		loginPage.enterEmail("test@mailinator.com");
		loginPage.enterPassword("Password123");
		loginPage.clickLogin();
 
		Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
	}

	/* ========================= TC-082 Invalid Password ==========================*/
	@Test
	public void TC_082_invalidPassword() {

		loginPage.enterEmail("test@mailinator.com");
		loginPage.enterPassword("WrongPass");
		loginPage.clickLogin();

		Assert.assertTrue(loginPage.getErrorMessage().contains("Invalid"));
	}

	/* ========================= TC-083 Unregistered Email ========================== */
	@Test
	public void TC_083_unregisteredEmail() {
		LoginPage page = new LoginPage(driver);

		page.enterEmail("unknown@mailinator.com");
		page.enterPassword("Password123");
		page.clickLogin();

		Assert.assertTrue(page.getErrorMessage().contains("not found"));
	}

	/* ========================= TC-084 Mandatory Fields ==========================*/
	@Test
	public void TC_084_mandatoryValidation() {
		LoginPage page = new LoginPage(driver);

		page.clickLogin();
		Assert.assertTrue(driver.getPageSource().contains("required"));
	}

	/*========================= TC-085 Dashboard Restriction==========================*/
	@Test
	public void TC_085_dashboardRestriction() {
		goTo("/dashboard");
		Assert.assertTrue(driver.getCurrentUrl().contains("login"));
	}

	/*========================= TC-086 Logout ==========================*/
	@Test
	public void TC_086_logout() {
		LoginPage page = new LoginPage(driver);

		page.enterEmail("test@mailinator.com");
		page.enterPassword("Password123");
		page.clickLogin();

		page.clickLogout();

		Assert.assertTrue(driver.getCurrentUrl().contains("login"));
	}
}