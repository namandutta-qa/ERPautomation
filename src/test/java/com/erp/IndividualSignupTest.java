package com.erp;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.erp.base.BaseTest;
import com.erp.pages.IndividualSignUpPage;
import com.erp.pages.RoleSelectionPage;

public class IndividualSignupTest extends BaseTest {

	RoleSelectionPage rolePage;
	IndividualSignUpPage signupPage;
	private String testEmail;

	@BeforeMethod

	public void setupRole() {
		testEmail = generateRandomEmail();

		rolePage = new RoleSelectionPage(driver);
		signupPage = new IndividualSignUpPage(driver);
		goTo("/onboarding");

		rolePage.selectRoleAndContinue(RoleSelectionPage.INDIVIDUAL);

	}

//	@Test
//	public void TC_011_verifySignupFormLoads() {
//		Assert.assertFalse(driver.getPageSource().contains("Individual Signup"));
//	}

	@Test
	public void TC_016_to_TC_044_Valid_Data_EndtoEndflow() throws InterruptedException {

		// ---------- STEP 1: Enter personal info ----------
		step("Enter personal info", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("A");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2008");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm(); // Must click before next verification
		});

		// ---------- STEP 2: Contact & Address ----------
		step("Enter contact info & address", () -> {
			signupPage.enterPhone("9876543210");
			signupPage.selectPerGender("Female");
			try {
				signupPage.enterPeraddress("ABC Street, City");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // With arrow down + enter
			signupPage.clickConfirm();
		});

		// ---------- STEP 3: Upload documents ----------
		step("Upload GovtID", () -> {
			signupPage.SelectGovtID("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
			signupPage.clickConfirm();
		});
		
        step("Capture Selfie Verification", () -> {
            try {
				signupPage.captureSelfie();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   // example method
        });

		// ---------- STEP 4: Review Page Verification ----------
		step("Verify review page fields", () -> {
			signupPage.verifyReviewPageField("Full Name", "John A Doe");
			signupPage.verifyReviewPageField("Date of Birth", "March 1, 2008");
			signupPage.verifyReviewPageField("Gender", "Female");
			signupPage.verifyReviewPageField("Phone", "1-9876543210");
			signupPage.verifyReviewPageField("Email", testEmail);
			signupPage.clickSubmit();
		});
	}

	@Test

	public void TC_02_invalidEmailS() {
		// ---------- EDGE CASES ----------
		// Invalid email
		step("Invalid email test", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("A");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2008");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.enterEmail("invalid-email");
			signupPage.clickConfirm();
		});
	}

	@Test

	public void TC_03_mandatoryfields() {
		// Empty mandatory fields
		step("Empty mandatory fields test", () -> {
			signupPage.clickUsernameField();
			signupPage.enterEmail("");
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2008");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm(); // Must click before next verification
			signupPage.enterFirstName("");
			signupPage.enterLastName("");
			signupPage.clickConfirm();

		});
	}

	@Test
	public void TC_04_invalidDOBAndFile() {

		// Invalid DOB
		step("Invalid DOB test", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("A");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.enterDOB("Feb 30, 2008");
			signupPage.clickConfirm();
		});
	}

	@Test
	public void TC_05_invalidGovtID() {

		// Upload invalid file
		step("Upload invalid GovtID file", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("A");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2008");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm(); // Must click before next verification
			// ---------- STEP 2: Contact & Address ----------
			step("Enter contact info & address", () -> {
				signupPage.enterPhone("9876543210");
				signupPage.selectPerGender("Female");
				try {
					signupPage.enterPeraddress("ABC Street, City");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // With arrow down + enter
				signupPage.clickConfirm();
			});

			// ---------- STEP 3: Upload documents ----------
			step("Upload GovtID", () -> {
				signupPage.SelectGovtID("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
				signupPage.clickConfirm();
			});
			signupPage.clickConfirm();
		});
	}
}
