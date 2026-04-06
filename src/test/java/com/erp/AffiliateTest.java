package com.erp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.lang.reflect.Method;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.erp.base.BaseTest;
import com.erp.pages.AffiliatePage;
import com.erp.pages.IndividualSignUpPage;
import com.erp.utils.NetworkLogger;
import org.openqa.selenium.devtools.DevTools;

public class AffiliateTest extends BaseTest {

	AffiliatePage affiliate;

	@BeforeMethod

	public void setupRole(Method method) throws InterruptedException {
		affiliate = new AffiliatePage(driver);

	    if (!method.getName().equals("TC_007_verifyUnauthorizedAccess")) {
	        loginAsRole("homeowner");
	        goTo("/affiliates");
	    }

	}

//	@Test
//	public void TC_001_verifyAffiliatePageLoads() {
//		Assert.assertFalse(driver.getPageSource().contains("Individual Signup"));
//	}
//
//	@Test
//	public void TC_002_affiliate() {
//
//		String affiliateUrl = affiliate.handleTermsAndGetLink();
//
//		System.out.println("Affiliate Link: " + affiliateUrl);
//
//		Assert.assertNotNull(affiliateUrl);
//	}

	@Test
	public void TC_003_validateReferralSignupFlow() {

		String referralLink = affiliate.handleTermsAndGetLink();

	    // ✅ Create new browser session using raw ChromeDriver but attach DevTools logger
	    WebDriver newDriver = new ChromeDriver(); // or use your driver factory
	    newDriver.manage().window().maximize();

	    // Attach DevTools & NetworkLogger to newDriver so button clicks are correlated to network
	    NetworkLogger logger = null;
	    try {
	        DevTools devTools = ((ChromeDriver) newDriver).getDevTools();
	        logger = new NetworkLogger(devTools);
	        // inject action correlation script
	        try {
	            ((JavascriptExecutor) newDriver).executeScript(NetworkLogger.getActionCorrelationScript());
	        } catch (Exception ignore) {
	        }
	    } catch (Exception e) {
	        System.err.println("[TC_003] Failed to attach NetworkLogger to newDriver: " + e.getMessage());
	    }

	    newDriver.get(referralLink);

	    IndividualSignUpPage signup = new IndividualSignUpPage(newDriver);
		step("Enter personal info", () -> {
			signup.enterFirstName("John");
			signup.enterMiddleName("A");
			signup.enterLastName("Doe");
			signup.clickUsernameField();
			signup.enterEmail(generateRandomEmail());
			signup.enterPasswordl("Test@121");
			signup.enterDOB("March 1, 2008");
			signup.acceptTerms();
			signup.acceptPrivacy();
			signup.clickConfirm(); // Must click before next verification
		});

		// ---------- STEP 2: Contact & Address ----------
		step("Enter contact info & address", () -> {
			signup.enterPhone("9876543210");
			signup.selectPerGender("Female");
			try {
				signup.enterPeraddress("ABC Street, City");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // With arrow down + enter
			signup.clickConfirm();
		});

		// ---------- STEP 3: Upload documents ----------
		step("Upload GovtID", () -> {
			signup.SelectGovtID("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
			
		});
		
        step("Capture Selfie Verification", () -> {
            try {
				signup.captureSelfie();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   // example method
        });
        signup.clickConfirm();
        

		// ---------- STEP 4: Review Page Verification ----------
		step("Verify review page fields", () -> {
			signup.verifyReviewPageField("Full Name", "John A Doe");
			signup.verifyReviewPageField("Date of Birth", "March 1, 2008");
			signup.verifyReviewPageField("Gender", "Female");
			signup.verifyReviewPageField("Phone", "1-9876543210");
//			signup.verifyReviewPageField("Email", generateRandomEmail());
			signup.clickSubmit();
		});

		// After submit, if we have a logger attached verify at least one API was called
		if (logger != null) {
			// wait briefly for network events to be captured
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ignore) {
			}

			int calls = logger.getApiLogs().size();
			System.out.println("[TC_003] Network calls captured by logger: " + calls);
			Assert.assertTrue(calls > 0, "Expected at least one API call after submit");
		}
	}

//	// ✅ TC_003 - Link Persistence
//	@Test
//	public void TC_004_verifyLinkConsistencyAfterRefresh() {
//
//		String link1 = affiliate.handleTermsAndGetLink();
//		driver.navigate().refresh();
//		String link2 = affiliate.handleTermsAndGetLink();
//
//		Assert.assertEquals(link1, link2);
//	}
//
//	// ✅ TC_004 - Copy Functionality
//	@Test
//	public void TC_005_verifyCopyFunctionality() {
//
//		String link = affiliate.handleTermsAndGetLink();
//		affiliate.clickcopy();
//
//		String copied = (String) ((JavascriptExecutor) driver).executeScript("return navigator.clipboard.readText();");
//
//		Assert.assertTrue(copied.contains("/ref/"));
//	}
//
//	// ✅ TC_006 - Invalid Referral
//	@Test
//	public void TC_006_verifyInvalidReferralCode() {
//
//		driver.get("https://yodixa-dev.lusites.xyz/ref/INVALID123");
//
//		Assert.assertTrue(driver.getPageSource().contains("Invalid"));
//	}
//
//	// ✅ TC_007 - Role Restriction
//	@Test
//	public void TC_007_verifyUnauthorizedAccess() {
//
//		driver.manage().deleteAllCookies();
//		goTo("/affiliates");
//
//		Assert.assertTrue(driver.getCurrentUrl().contains("login"));
//	}
//
	public String generateRandomEIN() {
		Random random = new Random();

		int prefix = 10 + random.nextInt(90); // ensures 2 digits (10–99)
		int suffix = 1000000 + random.nextInt(9000000); // ensures 7 digits

		return prefix + "-" + suffix;
	}

}
