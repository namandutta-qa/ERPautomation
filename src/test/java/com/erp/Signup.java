package com.erp;

import com.erp.base.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.erp.pages.*;
import com.erp.pages.RoleSelectionPage;
import com.erp.utils.WaitUtils;

public class Signup extends BaseTest {

	RoleSelectionPage rolePage;
	OrganizationSignupPage signupPage;
	private String testEmail;

	@BeforeMethod

	public void setupRole() {
		testEmail = randomemailgenerator();

		rolePage = new RoleSelectionPage(driver);
		signupPage = new OrganizationSignupPage(driver);
		goTo("/onboarding");

		rolePage.selectRoleAndContinue(RoleSelectionPage.ORGANIZATION);

	}

//	@Test
//	public void TC_011_verifySignupFormLoads() {
//		Assert.assertFalse(driver.getPageSource().contains("Individual Signup"));
//	}

	@Test
	public void TC_016_to_TC_044_Valid_Data_EndtoEndflow() {

		step("Enter user info", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2008");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm();

		});
		step("Enter organization info", () -> {
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN("12-3456789");
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterAddress("New York Street");

		});
		step("Enter owner info", () -> {
			signupPage.enterownerFirstName("Mike");
			signupPage.enterownerLastName("Francis");
			signupPage.enteronweremail(testEmail);
			signupPage.selectgender("Male");
			signupPage.enterowneraddress("New York Street");

		});

		step("Enter current address info", () -> {
			signupPage.selectdesignation("Manager");
			signupPage.enterPhone("9876543210");
			signupPage.selectPerGender("Female");

			try {
				signupPage.enterPeraddress("ABC");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.clickConfirm();
		});

		step("Upload GovtID document", () -> {
			signupPage.SelectGovtID("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
		});

//        step("Capture Selfie Verification", () -> {
//            signupPage.captureSelfie();   // example method
//        });

		step("Upload Article document", () -> {
			signupPage.SelectAOI("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
		});
		step("Upload GovtID document", () -> {
			signupPage.SelectIOD("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
		});

		step("Proceed to review page", () -> {
			signupPage.clickConfirm();
		});

		step("Verify review page displays correct info", () -> {
			signupPage.verifyReviewPageField("Full Name", "Mike Francis");
			signupPage.verifyReviewPageField("Organization Name", "ABC Corporation");
			signupPage.verifyReviewPageField("Date of Birth", "March 1, 2008");
			signupPage.verifyReviewPageField("Gender", "Male");
			signupPage.verifyReviewPageField("Designation", "Manager");
			signupPage.verifyReviewPageField("Phone", "9876543210");
			signupPage.verifyReviewPageField("Gender", "MALE");
			signupPage.verifyReviewPageField("Email", testEmail);
			signupPage.clickSubmit();

		});

	}
	
	@Test
	public void TC_071_BackNavigationRedirectToDashboard() {
	    step("Verify user is redirected to Dashboard on browser back during signup", () -> {
	        
	        // Step 1: Start signup process
	        signupPage.enterFirstName("John");
	        signupPage.enterLastName("Doe");
	        signupPage.enterEmail(testEmail);
	        signupPage.enterPasswordl("Test@121");
	        signupPage.enterDOB("March 1, 2000");
	        signupPage.acceptTerms();
	        signupPage.acceptPrivacy();
	        signupPage.clickConfirm();

	        // Step 2: User is now on next step (Organization page)
	        
	        // Step 3: Click browser back button
	        driver.navigate().back();

	        WaitUtils waitutils = new WaitUtils(driver);
	     // Use your wait util
	        waitutils.waitForUrlContains("/login");

	        String currentUrl = driver.getCurrentUrl();

	        Assert.assertTrue(
	            currentUrl.contains("https://yodixa.lusites.xyz/login"),
	            "User is not redirected to Login page. Current URL: " + currentUrl
	        );

	        Assert.assertFalse(
	            currentUrl.contains("dashboard"),
	            "User incorrectly redirected to Dashboard"
	            );
	    });
	}
	@Test
	public void TC_072_TermsAndPrivacyLinksValidation() {
	    step("Verify Terms of Service and Privacy Policy links do not redirect to 404", () -> {

	        // Click Terms of Service
	        signupPage.clickTermsLink();

	        // Switch to new tab if opened
	        switchToNewTab();
	        WaitUtils waitutils = new WaitUtils(driver);

	        waitutils.waitForPageLoad();

	        String termsUrl = driver.getCurrentUrl();

	        Assert.assertFalse(
	            termsUrl.contains("404"),
	            "Terms of Service link redirected to 404 page"
	        );

	        Assert.assertFalse(
	            driver.getTitle().contains("404"),
	            "Terms page shows 404 title"
	        );

	        driver.close();
	        switchToMainTab();

	        // Click Privacy Policy
	        signupPage.clickPrivacyLink();

	        switchToNewTab();

	        waitutils.waitForPageLoad();

	        String privacyUrl = driver.getCurrentUrl();

	        Assert.assertFalse(
	            privacyUrl.contains("404"),
	            "Privacy Policy link redirected to 404 page"
	        );

	        Assert.assertFalse(
	            driver.getTitle().contains("404"),
	            "Privacy page shows 404 title"
	        );

	        driver.close();
	        switchToMainTab();
	    });
	}

	@Test
	public void TC_045_MandatoryFieldValidations() {
		step("Verify mandatory field validations", () -> {
			signupPage.clickConfirm();
			signupPage.getFirstError();
			signupPage.getLastError();
			signupPage.getEmailError();
			signupPage.getPasswordError();
			signupPage.getdobError();
		});
	}

	@Test
	public void TC_046_InvalidEmailFormat() {
		step("Enter invalid email", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2008");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm();
			signupPage.enterEmail("test@.com");
			signupPage.clickConfirm();
			signupPage.getEmailError();
		});
	}

	@Test
	public void TC_047_PasswordPolicyEnforcement() {
		step("Enter weak password", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterDOB("March 1, 2008");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm();
			signupPage.enterPasswordl("12345");
			signupPage.clickConfirm();
			signupPage.getPasswordError();
		});
	}

	@Test
	public void TC_048_DOBAgeRestriction() {
		step("Enter underage DOB", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm();
			signupPage.enterDOB("March 1, 2010");
			signupPage.clickConfirm();
		});
	}

	@Test
	public void TC_056_DuplicateEmailRegistration() {
		step("Verify duplicate email is not allowed", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterLastName("Doe");
			signupPage.enterEmail("existing@email.com");
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2000");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm();

			signupPage.getEmailDuplicateError();
		});
	}

	@Test
	public void TC_057_UsernameUniqueness() {
		step("Verify username uniqueness", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();

			signupPage.clickConfirm();
			signupPage.enterusername("John");
		});
	}

	@Test
	public void TC_059_SpecialCharactersInName() {
		step("Verify special characters in name fields", () -> {
			signupPage.enterFirstName("@@@@");
			signupPage.enterLastName("####");
			signupPage.clickConfirm();

		});
	}

	@Test
	public void TC_060_MaxLengthValidation() {
		step("Verify max length for fields", () -> {
			signupPage.enterFirstName("A".repeat(256));
			signupPage.enterLastName("B".repeat(256));
			signupPage.clickConfirm();

			signupPage.getFirstError();
			signupPage.getLastError();
		});
	}

	@Test
	public void TC_063_MultipleSubmitClicks() {
		step("Verify multiple clicks do not create duplicate requests", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterLastName("Doe");

			signupPage.clickConfirm();
			signupPage.clickConfirm();
			signupPage.clickConfirm();

			Assert.assertTrue(signupPage.isSubmitButtonDisabled(),
					"Submit button is still enabled → multiple submissions possible");
		});
	}

	@Test
	public void TC_064_NetworkFailureHandling() {

	    networkHelper networkHelper = new networkHelper((ChromeDriver) driver);

	    step("Verify behavior on network failure", () -> {
	        signupPage.enterFirstName("John");
	        signupPage.enterLastName("Doe");

	        networkHelper.disableNetwork();   // ✅ now valid

	        signupPage.clickConfirm();

	        networkHelper.enableNetwork();    // ✅ now valid
	    });
	}


	@Test
	public void TC_069_XSSInjection() {
		step("Verify XSS is not executed", () -> {
			signupPage.enterFirstName("<script>alert('XSS')</script>");
			signupPage.clickConfirm();

		});
	}

	@Test
	public void TC_070_SQLInjection() {
		step("Verify SQL injection prevention", () -> {
			signupPage.enterEmail("' OR '1'='1");
			signupPage.clickConfirm();

		});
	}

	@Test
	public void TC_049_InvalidEINFormat() {
		step("Enter invalid EIN", () -> {

			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2008");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm();
			signupPage.enterLegalName("ABC Corporation");
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterAddress("New York Street");
			signupPage.enterEIN("123456789");
			signupPage.clickConfirm();
			signupPage.getEINError();
		});
	}

	@Test
	public void TC_050_CountryStateDropdown() {
		step("Verify country-state dependency", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2008");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm();
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN("12-3456789");
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterAddress("New York Street");

		});
	}

	@Test
	public void TC_052_OwnerInformationValidations() {
		step("Verify owner info validations", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2008");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm();
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN("12-3456789");
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterAddress("New York Street");
			signupPage.enterownerFirstName("");
			signupPage.enterownerLastName("");
			signupPage.enteronweremail("invalidEmail");
			signupPage.clickConfirm();
			signupPage.getFirstError();
			signupPage.getLastError();
			signupPage.getEmailError();
		});
	}

	@Test
	public void TC_053_PhoneNumberValidation() {
		step("Verify phone number format", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2008");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm();
			signupPage.enterPhone("12345");
			signupPage.clickConfirm();
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN("12-3456789");
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterAddress("New York Street");
			signupPage.getPhoneError();
		});
	}

	@Test
	public void TC_054_GenderDropdownValidation() {
		step("Verify gender dropdowns", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2008");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm();
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN("12-3456789");
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterAddress("New York Street");
			signupPage.selectgender("Male");
			signupPage.selectPerGender("Female");
		});
	}

	@Test
	public void TC_055_FileUploadValidation() {
		step("Verify valid and invalid file uploads", () -> {
			signupPage.SelectGovtID("/path/valid.pdf");
			signupPage.SelectAOI("/path/valid.jpg");
			signupPage.SelectIOD("/path/valid.png");

			signupPage.SelectGovtID("/home/lz-2/Downloads/1mb.exe");
			signupPage.SelectAOI("/path/largefile.pdf");
			Assert.assertFalse(signupPage.isFileUploaded(), "Large file should not be uploaded");
		});
	}

	@Test
	public void TC_056_MultipleDocumentUploads() {
		step("Verify multiple document uploads", () -> {
			signupPage.SelectGovtID("/path/file1.pdf");
			signupPage.SelectGovtID("/path/file2.pdf");
		});
	}

	protected String randomemailgenerator() {
		// TODO Auto-generated method stub
		String prefix = "user" + System.currentTimeMillis();
		return prefix + "@mailinator.com";
	}

}