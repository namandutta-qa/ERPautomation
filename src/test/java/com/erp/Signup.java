package com.erp;

import com.erp.base.*;

import java.io.IOException;
import java.util.Random;

import org.openqa.selenium.By;
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
	String designation = "Owner"; // Change to "Owner" to test owner flow
	String Govt = "/home/lz-2/Downloads/Yodixa/ID Photo/download.jpeg";

	@BeforeMethod

	public void setupRole() {
		testEmail = generateRandomEmail();

		rolePage = new RoleSelectionPage(driver);
		signupPage = new OrganizationSignupPage(driver);
		goTo("/onboarding");

		rolePage.selectRoleAndContinue(RoleSelectionPage.ORGANIZATION);

	}

	@Test
	public void TC_001_verifySignupFormLoads() {
		Assert.assertFalse(driver.getPageSource().contains("Individual Signup"));
	}

	@Test
	public void TC_002_Valid_Data_EndtoEndflow() {

		step("Enter user info", () -> {
			signupPage.enterFirstName("Mike");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Francis");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterPhone("9876543210");
			signupPage.enterDOB("March 1, 2008");
			signupPage.enterAddress("New York Street");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.selectgender("Male");
			signupPage.clickConfirm();

		});

		step("Enter organization info", () -> {
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN(generateRandomEIN());
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterowneraddress("New York Street");

			signupPage.selectdesignation(designation);
			// System.out.println("Selected designation: " + designation);

		});

		if (!designation.trim().equalsIgnoreCase("Owner")) {
			step("Enter owner info", () -> {
				signupPage.enterownerFirstName("Mike");
				signupPage.enterownerLastName("Francis");
				signupPage.enteronweremail(testEmail);
				signupPage.ownerenterDOB("March 3, 2008");
				signupPage.selectPerGender("Male");
				signupPage.enterPhone("9876543210");
				signupPage.enterPeraddress("New York Street");

			});
		}

		step("Click on Confirm", () -> {
			signupPage.clickConfirm();
		});

//
//		step("Enter current address info", () -> {
//			signupPage.selectdesignation("Manager");
//			signupPage.enterPhone("9876543210");
//			signupPage.selectPerGender("Female");
//
//			try {
//				signupPage.enterPeraddress("ABC");
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
//			signupPage.clickConfirm();
//			Assert.assertTrue(driver.getCurrentUrl().contains("information"), "Back navigation failed");
//
//		});
//
		step("Veify bussiness type", () -> {
			signupPage.selectBusinessType("Contractor");
			signupPage.clickConfirm();
		});

		step("Upload GovtID document", () -> {
signupPage.SelectGovtID(Govt);
		});

		step("Capture Selfie Verification", () -> {
			try {
				signupPage.captureSelfie();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // example method
		});

		step("Upload Article document", () -> {
			signupPage.SelectAOI(Govt);
		});
		step("Upload Insurance document", () -> {
			signupPage.SelectIOD(Govt);
		});

		step("Proceed to review page", () -> {
			signupPage.clickConfirm();
		});
		step("Verify review page displays correct info", () -> {
			signupPage.verifyReviewPageField("Full Name", "Mike Francis");
			signupPage.verifyReviewPageField("Legal Name", "ABC Corporation");
			signupPage.verifyReviewPageField("Date of Birth", "March 1, 2008");
			signupPage.verifyReviewPageField("Gender", "Male");
			signupPage.verifyReviewPageField("Designation", designation);
			signupPage.verifyReviewPageField("Phone", "1-9876543210");
			signupPage.verifyReviewPageField("Gender", "Male");
			signupPage.verifyReviewPageField("Email", testEmail);
			signupPage.clickSubmit();

		});
	}

	@Test
	public void TC_003_Back_navigation_from_businesstype() {

		step("Enter user info", () -> {
			signupPage.enterFirstName("Mike");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Francis");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterPhone("9876543210");
			signupPage.enterDOB("March 1, 2008");
			signupPage.enterAddress("New York Street");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.selectgender("Male");
			signupPage.clickConfirm();

		});

		step("Enter organization info", () -> {
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN(generateRandomEIN());
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterowneraddress("New York Street");

			signupPage.selectdesignation(designation);
			// System.out.println("Selected designation: " + designation);

		});

		if (!designation.trim().equalsIgnoreCase("Owner")) {
			step("Enter owner info", () -> {
				signupPage.enterownerFirstName("Mike");
				signupPage.enterownerLastName("Francis");
				signupPage.enteronweremail(testEmail);
				signupPage.ownerenterDOB("March 3, 2008");
				signupPage.selectPerGender("Male");
				signupPage.enterPhone("9876543210");
				signupPage.enterPeraddress("New York Street");

			});
		}

		step("Click on Confirm", () -> {
			signupPage.clickConfirm();
		});

//
//		step("Enter current address info", () -> {
//			signupPage.selectdesignation("Manager");
//			signupPage.enterPhone("9876543210");
//			signupPage.selectPerGender("Female");
//
//			try {
//				signupPage.enterPeraddress("ABC");
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
//			signupPage.clickConfirm();
//			Assert.assertTrue(driver.getCurrentUrl().contains("information"), "Back navigation failed");
//
//		});
//
		step("Veify bussiness type", () -> {
			signupPage.selectBusinessType("Contractor");
			signupPage.clickConfirm();
		});

	}

	@Test
	public void TC_004_BackNavigationRedirectToDashboard() {
		step("Verify user is redirected to Dashboard on browser back during signup", () -> {

			signupPage.enterFirstName("Mike");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Francis");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterPhone("9876543210");
			signupPage.enterDOB("March 1, 2008");
			signupPage.enterAddress("New York Street");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.selectgender("Male");
			signupPage.clickConfirm();

		});

		step("Enter organization info", () -> {
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN(generateRandomEIN());
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterowneraddress("New York Street");

			signupPage.selectdesignation(designation);
			// System.out.println("Selected designation: " + designation);

		});

		if (!designation.trim().equalsIgnoreCase("Owner")) {
			step("Enter owner info", () -> {
				signupPage.enterownerFirstName("Mike");
				signupPage.enterownerLastName("Francis");
				signupPage.enteronweremail(testEmail);
				signupPage.ownerenterDOB("March 3, 2008");
				signupPage.selectPerGender("Male");
				signupPage.enterPhone("9876543210");
				signupPage.enterPeraddress("New York Street");

			});
		}

		step("Click on Confirm", () -> {
			signupPage.clickConfirm();
		});

//
//		step("Enter current address info", () -> {
//			signupPage.selectdesignation("Manager");
//			signupPage.enterPhone("9876543210");
//			signupPage.selectPerGender("Female");
//
//			try {
//				signupPage.enterPeraddress("ABC");
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
//			signupPage.clickConfirm();
//			Assert.assertTrue(driver.getCurrentUrl().contains("information"), "Back navigation failed");
//
//		});
//
		step("Veify bussiness type", () -> {
			signupPage.selectBusinessType("Contractor");
		});
		// Step 3: Click browser back button
		driver.navigate().back();

		WaitUtils waitutils = new WaitUtils(driver);
		// Use your wait util
		waitutils.waitForUrlContains("/login");

		String currentUrl = driver.getCurrentUrl();

		Assert.assertTrue(currentUrl.contains("https://yodixa.lusites.xyz/login"),
				"User is not redirected to Login page. Current URL: " + currentUrl);

		Assert.assertFalse(currentUrl.contains("dashboard"), "User incorrectly redirected to Dashboard");
	}

	@Test
	public void TC_005_TermsAndPrivacyLinksValidation() {
		step("Verify Terms of Service and Privacy Policy links do not redirect to 404", () -> {

			// Click Terms of Service
			signupPage.clickTermsLink();

			// Switch to new tab if opened
			switchToNewTab();
			WaitUtils waitutils = new WaitUtils(driver);

			waitutils.waitForPageLoad();

			String termsUrl = driver.getCurrentUrl();

			Assert.assertFalse(termsUrl.contains("404"), "Terms of Service link redirected to 404 page");

			Assert.assertFalse(driver.getTitle().contains("404"), "Terms page shows 404 title");

			driver.close();
			switchToMainTab();

			// Click Privacy Policy
			signupPage.clickPrivacyLink();

			switchToNewTab();

			waitutils.waitForPageLoad();

			String privacyUrl = driver.getCurrentUrl();

			Assert.assertFalse(privacyUrl.contains("404"), "Privacy Policy link redirected to 404 page");

			Assert.assertFalse(driver.getTitle().contains("404"), "Privacy page shows 404 title");

			driver.close();
			switchToMainTab();
		});
	}

	@Test
	public void TC_006_MandatoryFieldValidations() {
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
	public void TC_007_InvalidEmailFormat() {
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
	public void TC_008_PasswordPolicyEnforcement() {
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
	public void TC_009_DOBAgeRestriction() {
		step("Enter underage DOB", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Doe");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.enterDOB("March 1, 2010");
			signupPage.clickConfirm();
		});
	}

	@Test
	public void TC_010_DuplicateEmailRegistration() {
		step("Verify duplicate email is not allowed", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterLastName("Doe");
			signupPage.enterEmail("tom@yopmail.com");
			signupPage.enterPasswordl("Test@121");
			signupPage.enterDOB("March 1, 2000");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.clickConfirm();

			signupPage.getEmailDuplicateError();
		});
	}

//	@Test
//	public void TC_057_UsernameUniqueness() {
//		step("Verify username uniqueness", () -> {
//			signupPage.enterFirstName("John");
//			signupPage.enterLastName("Doe");
//			signupPage.clickUsernameField();
//
//			signupPage.clickConfirm();
//			signupPage.enterusername("John");
//		});
//	}
//
	@Test
	public void TC_011_SpecialCharactersInName() {
		step("Verify special characters in name fields", () -> {
			signupPage.enterFirstName("@@@@");
			signupPage.enterLastName("####");
			signupPage.clickConfirm();

		});
	}

	@Test
	public void TC_012_MaxLengthValidation() {
		step("Verify max length for fields", () -> {
			signupPage.enterFirstName("A".repeat(256));
			signupPage.enterLastName("B".repeat(256));
			signupPage.clickConfirm();

			signupPage.getFirstError();
			signupPage.getLastError();
		});
	}

	@Test
	public void TC_013_MultipleSubmitClicks() {
		step("Verify multiple clicks do not create duplicate requests", () -> {
			signupPage.enterFirstName("Mike");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Francis");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterPhone("9876543210");
			signupPage.enterDOB("March 1, 2008");
			signupPage.enterAddress("New York Street");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.selectgender("Male");
			signupPage.clickConfirm();

		});
		step("Enter organization info", () -> {
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN(generateRandomEIN());
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
			signupPage.ownerenterDOB("March 3, 2008");
			signupPage.selectgender("Male");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.enterowneraddress("New York Street");

		});

		step("Enter current address info", () -> {
			signupPage.selectdesignation("Manager");
			signupPage.enterPhone("9876543210");
			signupPage.selectPerGender("Female");

			signupPage.enterPeraddress("ABC");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.clickConfirm();
		});

		step("Click bussiness type and click back navigation", () -> {
			signupPage.selectBusinessType("Contractor");
			signupPage.clickConfirm();
		});

		step("Upload GovtID document", () -> {
			signupPage.SelectGovtID("/home/lz-2/Downloads/Yodixa/ID Photo/download.jpg");
		});
		step("Capture Selfie Verification", () -> {
			try {
				signupPage.captureSelfie();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // example method
		});

		step("Upload Article document", () -> {
			signupPage.SelectAOI(Govt);
		});
		step("Upload Insurance document", () -> {
			signupPage.SelectIOD(Govt);
		});

		step("Proceed to review page", () -> {
			signupPage.clickConfirm();
		});

		step("Verify review page displays correct info", () -> {
			signupPage.verifyReviewPageField("Full Name", "Mike Francis");
			signupPage.verifyReviewPageField("Legal Name", "ABC Corporation");
			signupPage.verifyReviewPageField("Date of Birth", "March 3, 2008");
			signupPage.verifyReviewPageField("Gender", "Male");
			signupPage.verifyReviewPageField("Designation", "Manager");
			signupPage.verifyReviewPageField("Phone", "1-9876543210");
			signupPage.verifyReviewPageField("Gender", "Male");
			signupPage.verifyReviewPageField("Email", testEmail);
			signupPage.clickSubmit();

		});

		signupPage.clickConfirm();
		signupPage.clickConfirm();
		signupPage.clickConfirm();

		Assert.assertTrue(signupPage.isSubmitButtonDisabled(),
				"Submit button is still enabled → multiple submissions possible");
	}

	@Test
	public void TC_014_NetworkFailureHandling() {

		networkHelper networkHelper = new networkHelper((ChromeDriver) driver);

		step("Verify behavior on network failure", () -> {
			signupPage.enterFirstName("John");
			signupPage.enterLastName("Doe");

			networkHelper.disableNetwork(); // ✅ now valid

			signupPage.clickConfirm();

			networkHelper.enableNetwork(); // ✅ now valid
		});
	}

	@Test
	public void TC_015_XSSInjection() {
		step("Verify XSS is not executed", () -> {
			signupPage.enterFirstName("<script>alert('XSS')</script>");
			signupPage.clickConfirm();

		});
	}

	@Test
	public void TC_016_SQLInjection() {
		step("Verify SQL injection prevention", () -> {
			signupPage.enterEmail("' OR '1'='1");
			signupPage.clickConfirm();

		});
	}

	@Test
	public void TC_017_InvalidEINFormat() {
		step("Enter invalid EIN", () -> {
			testEmail = randomemailgenerator();

			step("Enter user info", () -> {
				signupPage.enterFirstName("Mike");
				signupPage.enterMiddleName("");
				signupPage.enterLastName("Francis");
				signupPage.clickUsernameField();
				signupPage.enterEmail(testEmail);
				signupPage.enterPasswordl("Test@121");
				signupPage.enterPhone("9876543210");
				signupPage.enterDOB("March 1, 2008");
				signupPage.enterAddress("New York Street");
				signupPage.acceptTerms();
				signupPage.acceptPrivacy();
				signupPage.selectgender("Male");
				signupPage.clickConfirm();

			});

			step("Enter organization info", () -> {
				signupPage.enterLegalName("ABC Corporation");
				signupPage.enterEIN("2133241344");
				signupPage.selectIndustryDropdown("Technology");
				signupPage.selectCountry("United States");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				signupPage.selectState("Georgia");
				signupPage.enterowneraddress("New York Street");

				signupPage.selectdesignation(designation);
				// System.out.println("Selected designation: " + designation);

			});

			if (!designation.trim().equalsIgnoreCase("Owner")) {
				step("Enter owner info", () -> {
					signupPage.enterownerFirstName("Mike");
					signupPage.enterownerLastName("Francis");
					signupPage.enteronweremail(testEmail);
					signupPage.ownerenterDOB("March 3, 2008");
					signupPage.selectPerGender("Male");
					signupPage.enterPhone("9876543210");
					signupPage.enterPeraddress("New York Street");

				});
			}

			step("Click on Confirm", () -> {
				signupPage.clickConfirm();
			});

	//
//			step("Enter current address info", () -> {
//				signupPage.selectdesignation("Manager");
//				signupPage.enterPhone("9876543210");
//				signupPage.selectPerGender("Female");
	//
//				try {
//					signupPage.enterPeraddress("ABC");
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				signupPage.clickConfirm();
//				Assert.assertTrue(driver.getCurrentUrl().contains("information"), "Back navigation failed");
	//
//			});
	//

			signupPage.getEINError();
		});
	}


	@Test
	public void TC_018_OwnerInformationValidations() {
		step("Enter user info", () -> {
			signupPage.enterFirstName("Mike");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Francis");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterPhone("9876543210");
			signupPage.enterDOB("March 1, 2008");
			signupPage.enterAddress("New York Street");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.selectgender("Male");
			signupPage.clickConfirm();

		});

		step("Enter organization info", () -> {
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN(generateRandomEIN());
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterowneraddress("New York Street");
			this.designation = "Manager";
			signupPage.selectdesignation("Manager");

		});

		if (this.designation != null && !this.designation.trim().equalsIgnoreCase("Owner")) {
			step("Enter owner info", () -> {
				signupPage.enterownerFirstName("");
				signupPage.enterownerLastName("");
				signupPage.enteronweremail(testEmail);
				signupPage.ownerenterDOB("March 3, 2008");
				signupPage.selectPerGender("Male");
				signupPage.enterPhone("9876543210");
				signupPage.enterPeraddress("New York Street");
				signupPage.clickConfirm();

			});
		}
	}

	@Test
	public void TC_019_PhoneNumberValidation() {
		step("Verify phone number format", () -> {
			testEmail = randomemailgenerator();

			signupPage.enterFirstName("Mike");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Francis");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterPhone("98765432103");
			signupPage.enterDOB("March 1, 2008");
			signupPage.enterAddress("New York Street");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.selectgender("Male");
			signupPage.clickConfirm();

		});
	}



	@Test
	public void TC_020_GenderDropdownValidation() {
		step("Enter user info", () -> {
			signupPage.enterFirstName("Mike");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Francis");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterPhone("9876543210");
			signupPage.enterDOB("March 1, 2008");
			signupPage.enterAddress("New York Street");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.selectgender("Male");
			signupPage.clickConfirm();

		});

		step("Enter organization info", () -> {
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN(generateRandomEIN());
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterowneraddress("New York Street");

			signupPage.selectdesignation("Manager");

		});
		 System.out.println("Selected designation: " + designation);

		if (designation != null && !designation.trim().equalsIgnoreCase("Owner")) {
			step("Enter owner info", () -> {
				signupPage.enterownerFirstName("Mike");
				signupPage.enterownerLastName("Francis");
				signupPage.enteronweremail(testEmail);
				signupPage.ownerenterDOB("March 3, 2008");
				signupPage.selectPerGender("Male");
				signupPage.enterPhone("9876543210");
				signupPage.enterPeraddress("New York Street");
				signupPage.clickConfirm();

			});
		}

//		step("Click on Confirm", () -> {
//			signupPage.clickConfirm();
//		});

//
//		step("Enter current address info", () -> {
//			signupPage.selectdesignation("Manager");
//			signupPage.enterPhone("9876543210");
//			signupPage.selectPerGender("Female");
//
//			try {
//				signupPage.enterPeraddress("ABC");
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
//			signupPage.clickConfirm();
//			Assert.assertTrue(driver.getCurrentUrl().contains("information"), "Back navigation failed");
//
//		});
//
		step("Veify bussiness type", () -> {
			signupPage.selectBusinessType("Contractor");
			signupPage.clickConfirm();
		});

	}

	@Test
	public void TC_021_FileUploadValidation() {
		step("Enter user info", () -> {
			signupPage.enterFirstName("Mike");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Francis");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterPhone("9876543210");
			signupPage.enterDOB("March 1, 2008");
			signupPage.enterAddress("New York Street");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.selectgender("Male");
			signupPage.clickConfirm();

		});

		step("Enter organization info", () -> {
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN(generateRandomEIN());
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterowneraddress("New York Street");

			signupPage.selectdesignation(designation);
			// System.out.println("Selected designation: " + designation);

		});

		if (!designation.trim().equalsIgnoreCase("Owner")) {
			step("Enter owner info", () -> {
				signupPage.enterownerFirstName("Mike");
				signupPage.enterownerLastName("Francis");
				signupPage.enteronweremail(testEmail);
				signupPage.ownerenterDOB("March 3, 2008");
				signupPage.selectPerGender("Male");
				signupPage.enterPhone("9876543210");
				signupPage.enterPeraddress("New York Street");

			});
		}

		step("Click on Confirm", () -> {
			signupPage.clickConfirm();
		});

//
//		step("Enter current address info", () -> {
//			signupPage.selectdesignation("Manager");
//			signupPage.enterPhone("9876543210");
//			signupPage.selectPerGender("Female");
//
//			try {
//				signupPage.enterPeraddress("ABC");
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
//			signupPage.clickConfirm();
//			Assert.assertTrue(driver.getCurrentUrl().contains("information"), "Back navigation failed");
//
//		});
//
		step("Veify bussiness type", () -> {
			signupPage.selectBusinessType("Contractor");
			signupPage.clickConfirm();
		});

		step("Upload GovtID document", () -> {
			signupPage.SelectGovtID(Govt);
		});

		step("Verify valid and invalid file uploads", () -> {

			signupPage.SelectGovtID(Govt);
			signupPage.SelectAOI(Govt);
			signupPage.SelectIOD(Govt);

			signupPage.SelectGovtID(Govt);
			signupPage.SelectAOI(Govt);
			Assert.assertFalse(signupPage.isFileUploaded(), "Large file should not be uploaded");
		});
	}

	@Test
	public void TC_022_MultipleDocumentUploads() {
		step("Enter user info", () -> {
			
			testEmail = randomemailgenerator();
			signupPage.enterFirstName("Mike");
			signupPage.enterMiddleName("");
			signupPage.enterLastName("Francis");
			signupPage.clickUsernameField();
			signupPage.enterEmail(testEmail);
			signupPage.enterPasswordl("Test@121");
			signupPage.enterPhone("9876543210");
			signupPage.enterDOB("March 1, 2008");
			signupPage.enterAddress("New York Street");
			signupPage.acceptTerms();
			signupPage.acceptPrivacy();
			signupPage.selectgender("Male");
			signupPage.selectgender("Female");
			signupPage.clickConfirm();

		});

		step("Enter organization info", () -> {
			signupPage.enterLegalName("ABC Corporation");
			signupPage.enterEIN(generateRandomEIN());
			signupPage.selectIndustryDropdown("Technology");
			signupPage.selectCountry("United States");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			signupPage.selectState("Georgia");
			signupPage.enterowneraddress("New York Street");

			signupPage.selectdesignation(designation);
			// System.out.println("Selected designation: " + designation);

		});

		if (!designation.trim().equalsIgnoreCase("Owner")) {
			step("Enter owner info", () -> {
				signupPage.enterownerFirstName("Mike");
				signupPage.enterownerLastName("Francis");
				signupPage.enteronweremail(testEmail);
				signupPage.ownerenterDOB("March 3, 2008");
				signupPage.selectPerGender("Male");
				signupPage.enterPhone("9876543210");
				signupPage.enterPeraddress("New York Street");

			});
		}

			step("Click on Confirm", () -> {
				signupPage.clickConfirm();
			});

	//
//			step("Enter current address info", () -> {
//				signupPage.selectdesignation("Manager");
//				signupPage.enterPhone("9876543210");
//				signupPage.selectPerGender("Female");
	//
//				try {
//					signupPage.enterPeraddress("ABC");
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				signupPage.clickConfirm();
//				Assert.assertTrue(driver.getCurrentUrl().contains("information"), "Back navigation failed");
	//
//			});
	//
			step("Veify bussiness type", () -> {
				signupPage.selectBusinessType("Contractor");
				signupPage.clickConfirm();
			});


		step("Upload GovtID document", () -> {
			signupPage.SelectGovtID(Govt);
		});

		step("Verify multiple document uploads", () -> {
			signupPage.SelectGovtID(Govt);
			signupPage.SelectGovtID(Govt);
		});
	}

	protected String randomemailgenerator() {
		// TODO Auto-generated method stub
		String prefix = "user" + System.currentTimeMillis();
		return prefix + "@mailinator.com";
	}

	public String generateRandomEIN() {
		Random random = new Random();

		int prefix = 10 + random.nextInt(90); // ensures 2 digits (10–99)
		int suffix = 1000000 + random.nextInt(9000000); // ensures 7 digits

		return prefix + "-" + suffix;
	}
}