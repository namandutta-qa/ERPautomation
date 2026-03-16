package com.erp;

import com.erp.base.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.erp.pages.*;
import com.erp.pages.RoleSelectionPage;

public class Signup extends BaseTest {

	RoleSelectionPage rolePage;
	OrganizationSignupPage signupPage;
	private String emailPrefix;
	private String testEmail = randomemailgenerator();

	@BeforeMethod

	public void setupRole() {

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
	public void TC_016_to_TC_044_OrganizationDetails() {

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
//			signupPage.enterPostalCode("10001");
//			signupPage.City("Los Angeles");
//			signupPage.selectcountry("United States");
//			signupPage.selectCurrentstateDropdown("Georgia");

		});
		step("Enter owner info", () -> {
			signupPage.enterownerFirstName("Mike");
			signupPage.enterownerLastName("Francis");
			signupPage.enteronweremail(testEmail);
			signupPage.selectgender("Male");
//			signupPage.selectownerState("Georgia");
			signupPage.enterowneraddress("New York Street");
//			signupPage.enterownerzip("10001");
//			signupPage.enterownercity("Los Angeles");
//			signupPage.selectownercountry("United States");
//			signupPage.selectownercountrybirth("Georgia");

		});

		step("Enter current address info", () -> {
			signupPage.selectdesignation("Manager");
			signupPage.enterPhone("9876543210");
//			signupPage.enterDOB("01/01/1990");
			signupPage.selectPerGender("Female");
//			signupPage.selectPerCountry("United States");
//			signupPage.enterPeraddress("sgdf");
//			signupPage.enterPerZip("45435");
//			signupPage.enterPercity("fdsh");
//            signupPage.selectPerstate("Alabama");
//			signupPage.CurrentState("Alabama");
			try {
				signupPage.enterPeraddress("ABC");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			signupPage.enterPercity("ABC");

			signupPage.clickNext();
		});
//
//        step("Upload GovtID document", () -> {
//            signupPage.SelectGovtID("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
//        });
//        
//        step("Capture Selfie Verification", () -> {
//            signupPage.captureSelfie();   // example method
//        });
//
//        step("Upload Article document", () -> {
//            signupPage.SelectAOI("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
//        });
//        step("Upload GovtID document", () -> {
//            signupPage.SelectIOD("/home/lz-2/Downloads/Joy of cleaning/Amazon Order PDF Test File.pdf");
//        });
//        
//        step("Proceed to review page", () -> {
//            signupPage.clickNext();
//        });
//
//        step("Accept consent", () -> {
//            signupPage.acceptTerms();
////            signupPage.acceptPrivacy();
//        });
//
//        step("Submit application", () -> {
//            signupPage.clickSubmit();
//        });
//
//        step("Verify confirmation", () -> {
//            Assert.assertTrue(signupPage.isConfirmationDisplayed());
//        });

	}
//	@Test
//	public void TC_046_verifyInvalidEIN() {
//
//	    step("Enter invalid EIN", () -> {
//	        signupPage.enterLegalName("ABC Corporation");
//	        signupPage.enterEIN("123");
//	    });
//
//	    step("Click Next", () -> {
//	        signupPage.clickNext();
//	    });
//
//	    step("Verify EIN error message", () -> {
//	        Assert.assertTrue(signupPage.getEINError().contains("Invalid EIN"));
//	    });
//	}

//	@Test
//	public void TC_047_verifyInvalidOwnerEmail() {
//
//	    step("Enter invalid email", () -> {
//	        signupPage.enterEmail("john.com");
//	    });
//
//	    step("Click Next", () -> {
//	        signupPage.clickNext();
//	    });
//
//	    step("Verify email validation", () -> {
//	        Assert.assertTrue(signupPage.getEmailError().contains("Invalid email"));
//	    });
//	}
//	@Test
//	public void TC_048_verifyInvalidPhoneNumber() {
//
//	    step("Enter invalid phone", () -> {
//	        signupPage.enterPhone("123");
//	    });
//
//	    step("Click Next", () -> {
//	        signupPage.clickNext();
//	    });
//
//	    step("Verify phone error", () -> {
//	        Assert.assertTrue(signupPage.getPhoneError().contains("Invalid phone"));
//	    });
//	}
//	@Test
//	public void TC_049_verifyRequiredFieldsValidation() {
//
//	    step("Leave all fields empty", () -> {
//	        signupPage.enterLegalName("");
//	        signupPage.enterEIN("");
//	        signupPage.enterAddress("");
//	    });
//
//	    step("Click Next", () -> {
//	        signupPage.clickNext();
//	    });
//
//	    step("Verify required field errors", () -> {
//	        Assert.assertTrue(signupPage.isRequiredErrorDisplayed());
//	    });
//	}

	protected String randomemailgenerator() {
		// TODO Auto-generated method stub
		String prefix = "user" + System.currentTimeMillis();
		return prefix + "@mailinator.com";
	}
}