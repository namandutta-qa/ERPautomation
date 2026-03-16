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
	        signupPage.enterEmail("test@.com");
	        signupPage.clickConfirm();
	        signupPage.getEmailError();
	    });
	}

	@Test
	public void TC_047_PasswordPolicyEnforcement() {
	    step("Enter weak password", () -> {
	        signupPage.enterPasswordl("12345");
	        signupPage.clickConfirm();
	        signupPage.getPasswordError();
	    });
	}

	@Test
	public void TC_048_DOBAgeRestriction() {
	    step("Enter underage DOB", () -> {
	        signupPage.enterDOB("March 1, 2010");
	        signupPage.clickConfirm();
	    });
	}

	@Test
	public void TC_049_InvalidEINFormat() {
	    step("Enter invalid EIN", () -> {
	        signupPage.enterEIN("123456789");
	        signupPage.clickConfirm();
	        signupPage.getEINError();
	    });
	}

	@Test
	public void TC_050_CountryStateDropdown() {
	    step("Verify country-state dependency", () -> {
	        signupPage.selectCountry("United States");
	        signupPage.selectState("Georgia"); // Verify only US states appear
	    });
	}


//	@Test
//	public void TC_052_OwnerInformationValidations() {
//	    step("Verify owner info validations", () -> {
//	        signupPage.enterownerFirstName("");
//	        signupPage.enterownerLastName("");
//	        signupPage.enteronweremail("invalidEmail");
//	        signupPage.clickConfirm();
//	        signupPage.verifyErrorMessage("Owner First Name is required");
//	        signupPage.verifyErrorMessage("Owner Last Name is required");
//	        signupPage.verifyErrorMessage("Invalid owner email format");
//	    });
//	}

	@Test
	public void TC_053_PhoneNumberValidation() {
	    step("Verify phone number format", () -> {
	        signupPage.enterPhone("12345");
	        signupPage.clickConfirm();
	        signupPage.getPhoneError();
	    });
	}

	@Test
	public void TC_054_GenderDropdownValidation() {
	    step("Verify gender dropdowns", () -> {
	        signupPage.selectgender("Male");
	        signupPage.selectPerGender("Female");
	    });
	}

//	@Test
//	public void TC_055_FileUploadValidation() {
//	    step("Verify valid and invalid file uploads", () -> {
//	        signupPage.SelectGovtID("/path/valid.pdf");
//	        signupPage.SelectAOI("/path/valid.jpg");
//	        signupPage.SelectIOD("/path/valid.png");
//	        
//	        signupPage.SelectGovtID("/path/invalid.exe");
//	        signupPage.verifyErrorMessage("Invalid file type");
//	        
//	        signupPage.SelectAOI("/path/largefile.pdf");
//	        signupPage.verifyErrorMessage("File size exceeds limit");
//	    });
//	}
//
//	@Test
//	public void TC_056_MultipleDocumentUploads() {
//	    step("Verify multiple document uploads", () -> {
//	        signupPage.SelectGovtID("/path/file1.pdf");
//	        signupPage.SelectGovtID("/path/file2.pdf");
//	        signupPage.verifyDocumentUploaded("/path/file2.pdf"); // Check replacement behavior
//	    });
//	}

//	@Test
//	public void TC_057_ReviewPageVerification() {
//	    step("Verify review page displays correct info", () -> {
//	        signupPage.clickConfirm();
//	        signupPage.verifyReviewPageField("First Name", "John");
//	        signupPage.verifyReviewPageField("Organization Name", "ABC Corporation");
//	        signupPage.verifyReviewPageField("Owner Email", testEmail);
//	    });
//	}
//
//	@Test
//	public void TC_058_EmptyReviewSubmission() {
//	    step("Verify review submission without documents", () -> {
//	        signupPage.clickConfirm();
//	        signupPage.verifyErrorMessage("Please upload all required documents");
//	    });
//	}

	protected String randomemailgenerator() {
		// TODO Auto-generated method stub
		String prefix = "user" + System.currentTimeMillis();
		return prefix + "@mailinator.com";
	}
}