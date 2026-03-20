package com.erp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.lang.reflect.Method;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.erp.base.BaseTest;
import com.erp.pages.AffiliatePage;
import com.erp.pages.OrganizationSignupPage;

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

	@Test
	public void TC_001_verifyAffiliatePageLoads() {
		Assert.assertFalse(driver.getPageSource().contains("Individual Signup"));
	}

	@Test
	public void TC_002_affiliate() {

		String affiliateUrl = affiliate.handleTermsAndGetLink();

		System.out.println("Affiliate Link: " + affiliateUrl);

		Assert.assertNotNull(affiliateUrl);
	}

//	@Test
//	public void TC_003_validateReferralSignupFlow() {
//
//		String referralLink = affiliate.handleTermsAndGetLink();
//
//		// Open new tab
//		((JavascriptExecutor) driver).executeScript("window.open()");
//		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
//		driver.switchTo().window(tabs.get(1));
//
//		driver.get(referralLink);
//
//		OrganizationSignupPage signup = new OrganizationSignupPage(driver);
//		step("Enter user info", () -> {
//			signup.enterFirstName("John");
//			signup.enterMiddleName("");
//			signup.enterLastName("Doe");
//			signup.clickUsernameField();
//			signup.enterEmail(generateRandomEmail());
//			signup.enterPasswordl("Test@121");
//			signup.enterDOB("March 1, 2008");
//			signup.acceptTerms();
//			signup.acceptPrivacy();
//			signup.clickConfirm();
//
//		});
//		step("Enter organization info", () -> {
//			signup.enterLegalName("ABC Corporation");
//			signup.enterEIN(generateRandomEIN());
//			signup.selectIndustryDropdown("Technology");
//			signup.selectCountry("United States");
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			signup.selectState("Georgia");
//			signup.enterAddress("New York Street");
//
//		});
//		step("Enter owner info", () -> {
//			signup.enterownerFirstName("Mike");
//			signup.enterownerLastName("Francis");
//			signup.enteronweremail(generateRandomEmail());
//			signup.ownerenterDOB("March 3, 2008");
//			signup.selectgender("Male");
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			signup.enterowneraddress("New York Street");
//
//		});
//
//		step("Enter current address info", () -> {
//			signup.selectdesignation("Manager");
//			signup.enterPhone("9876543210");
//			signup.selectPerGender("Female");
//
//			try {
//				signup.enterPeraddress("ABC");
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			signup.clickConfirm();
//		});
//
//		step("Upload GovtID document", () -> {
//			signup.SelectGovtID("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
//		});
//
//		step("Capture Selfie Verification", () -> {
//			try {
//				signup.captureSelfie();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} // example method
//		});
//
//		step("Upload Article document", () -> {
//			signup.SelectAOI("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
//		});
//		step("Upload GovtID document", () -> {
//			signup.SelectIOD("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
//		});
//
////		step("Verify Biometric", () -> {
////			signup.captureSelfie();
////			Assert.assertFalse(signup.isFileUploaded(), "Large file should not be uploaded");
////		});
//
//		step("Proceed to review page", () -> {
//			signup.clickConfirm();
//		});
//
//		step("Verify review page displays correct info", () -> {
//			signup.verifyReviewPageField("Full Name", "Mike Francis");
//			signup.verifyReviewPageField("Legal Name", "ABC Corporation");
//			signup.verifyReviewPageField("Date of Birth", "March 3, 2008");
//			signup.verifyReviewPageField("Gender", "Male");
//			signup.verifyReviewPageField("Designation", "Manager");
//			signup.verifyReviewPageField("Phone", "1-9876543210");
//			signup.verifyReviewPageField("Gender", "Male");
//			signup.verifyReviewPageField("Email", generateRandomEmail());
//			signup.clickSubmit();
//
//		});
//
//	}

	// ✅ TC_003 - Link Persistence
	@Test
	public void TC_004_verifyLinkConsistencyAfterRefresh() {

		String link1 = affiliate.handleTermsAndGetLink();
		driver.navigate().refresh();
		String link2 = affiliate.handleTermsAndGetLink();

		Assert.assertEquals(link1, link2);
	}

	// ✅ TC_004 - Copy Functionality
	@Test
	public void TC_005_verifyCopyFunctionality() {

		String link = affiliate.handleTermsAndGetLink();
		affiliate.clickcopy();

		String copied = (String) ((JavascriptExecutor) driver).executeScript("return navigator.clipboard.readText();");

		Assert.assertTrue(copied.contains("/ref/"));
	}

	// ✅ TC_006 - Invalid Referral
	@Test
	public void TC_006_verifyInvalidReferralCode() {

		driver.get("https://yodixa-dev.lusites.xyz/ref/INVALID123");

		Assert.assertTrue(driver.getPageSource().contains("Invalid"));
	}

	// ✅ TC_007 - Role Restriction
	@Test
	public void TC_007_verifyUnauthorizedAccess() {

		driver.manage().deleteAllCookies();
		goTo("/affiliates");

		Assert.assertTrue(driver.getCurrentUrl().contains("login"));
	}

	public String generateRandomEIN() {
		Random random = new Random();

		int prefix = 10 + random.nextInt(90); // ensures 2 digits (10–99)
		int suffix = 1000000 + random.nextInt(9000000); // ensures 7 digits

		return prefix + "-" + suffix;
	}

}