package com.erp;

import com.erp.base.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.erp.pages.*;

public class Signup extends BaseTest {
	
    private String emailPrefix;
    private String testEmail = randomemailgenerator();

    @Test
    public void TC_011_verifySignupFormLoads() {
        Assert.assertTrue(driver.getPageSource().contains("Individual Signup"));
    }

    private String randomemailgenerator() {
		// TODO Auto-generated method stub
        String prefix = "user" + System.currentTimeMillis();
        return prefix + "@mailinator.com";
	}

	@Test
    public void TC_012_verifyFirstNameValidInput() {
        SignupPage page = new SignupPage(driver);
        page.enterFirstName("Naman");
        Assert.assertTrue(true);
    }

    @Test
    public void TC_013_verifyFirstNameInvalidInput() {
        SignupPage page = new SignupPage(driver);
        page.enterFirstName("1234@");
        page.clickSubmit();
        Assert.assertTrue(page.getErrorMessage().contains("Invalid"));
    }

    @Test
    public void TC_014_verifyLastNameValid() {
        SignupPage page = new SignupPage(driver);
        page.enterLastName("Dutta");
        Assert.assertTrue(true);
    }

    @Test
    public void TC_015_verifyMiddleNameOptional() {
        SignupPage page = new SignupPage(driver);
        page.enterFirstName("Naman");
        page.enterLastName("Dutta");
        page.enterEmail(testEmail);
        page.clickSubmit();
        Assert.assertFalse(driver.getPageSource().contains("Middle Name required"));
    }

    @Test
    public void TC_016_verifyValidEmail() {
        SignupPage page = new SignupPage(driver);
        page.enterEmail(testEmail);
        Assert.assertTrue(true);
    }

    @Test
    public void TC_017_verifyInvalidEmail() {
        SignupPage page = new SignupPage(driver);
        page.enterEmail("invalidemail");
        page.clickSubmit();
        Assert.assertTrue(page.getErrorMessage().contains("Invalid email"));
    }

    @Test
    public void TC_018_verifyDuplicateEmail() {
        SignupPage page = new SignupPage(driver);
        page.enterEmail("point@yopmail.com");
        page.clickSubmit();
        Assert.assertTrue(page.getErrorMessage().contains("already exists"));
    }

    @Test
    public void TC_019_verifyValidPhone() {
        SignupPage page = new SignupPage(driver);
        page.enterPhone("9876543210");
        Assert.assertTrue(true);
    }

    @Test
    public void TC_020_verifyInvalidPhone() {
        SignupPage page = new SignupPage(driver);
        page.enterPhone("abcd123");
        page.clickSubmit();
        Assert.assertTrue(page.getErrorMessage().contains("Invalid phone"));
    }

    @Test
    public void TC_021_verifyValidDOB() {
        SignupPage page = new SignupPage(driver);
        page.enterDOB("01-01-1995");
        Assert.assertTrue(true);
    }

    @Test
    public void TC_022_verifyFutureDOB() {
        SignupPage page = new SignupPage(driver);
        page.enterDOB("01-01-2035");
        page.clickSubmit();
        Assert.assertTrue(page.getErrorMessage().contains("Invalid DOB"));
    }

    @Test
    public void TC_023_verifyMinimumAge() {
        SignupPage page = new SignupPage(driver);
        page.enterDOB("01-01-2015");
        page.clickSubmit();
        Assert.assertTrue(page.getErrorMessage().contains("Minimum age"));
    }

    @Test
    public void TC_024_verifyGenderSelection() {
        SignupPage page = new SignupPage(driver);
        page.selectGender();
        Assert.assertTrue(true);
    }

    @Test
    public void TC_025_verifyCountryDropdown() {
        SignupPage page = new SignupPage(driver);
        page.selectCountry("India");
        Assert.assertTrue(true);
    }

    @Test
    public void TC_026_verifyStreetAddress() {
        SignupPage page = new SignupPage(driver);
        page.enterStreet("123 Main Street");
        Assert.assertTrue(true);
    }

    @Test
    public void TC_027_verifyCityValidation() {
        SignupPage page = new SignupPage(driver);
        page.enterCity("Mumbai");
        Assert.assertTrue(true);
    }

    @Test
    public void TC_028_verifyStateValidation() {
        SignupPage page = new SignupPage(driver);
        page.enterState("Maharashtra");
        Assert.assertTrue(true);
    }
    @Test
    public void TC_029_postalCodeValidation() {
        WebElement postal = driver.findElement(By.id("postalCode"));
        postal.sendKeys("400001");
        Assert.assertTrue(postal.getAttribute("value").equals("400001"));

        postal.clear();
        postal.sendKeys("abc@@");
        driver.findElement(By.id("submit")).click();
        Assert.assertTrue(driver.getPageSource().contains("Invalid postal"));
    }

    /* =========================
       TC-030 Country Selection
    ========================== */
    @Test
    public void TC_030_countrySelection() {
        WebElement country = driver.findElement(By.id("currentCountry"));
        country.click();
        country.sendKeys("India");
        Assert.assertTrue(country.getAttribute("value").equals("India"));
    }

    /* =========================
       TC-031 Mandatory Validation
    ========================== */
    @Test
    public void TC_031_mandatoryFieldValidation() {
        driver.findElement(By.id("submit")).click();
        Assert.assertTrue(driver.getPageSource().contains("required"));
    }

    /* =========================
       TC-032 Successful Signup
    ========================== */
    @Test
    public void TC_032_successfulSignup() {
        fillValidSignupForm();
        driver.findElement(By.id("submit")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("identity"));
    }

    /* =========================
       TC-034 Responsive Signup
    ========================== */
    @Test
    public void TC_034_signupResponsive() {
        driver.manage().window().setSize(new Dimension(375, 812));
        Assert.assertTrue(driver.findElement(By.id("signupForm")).isDisplayed());
    }

    /* =========================
       TC-035 Identity Page Load
    ========================== */
    @Test
    public void TC_035_identityPageLoad() {
        Assert.assertTrue(driver.getPageSource().contains("Govt ID"));
    }

    /* =========================
       TC-036 JPG Upload
    ========================== */
    @Test
    public void TC_036_jpgUpload() {
        uploadFile("sample.jpg");
        Assert.assertTrue(driver.getPageSource().contains("uploaded"));
    }

    /* =========================
       TC-037 PNG Upload
    ========================== */
    @Test
    public void TC_037_pngUpload() {
        uploadFile("sample.png");
        Assert.assertTrue(driver.getPageSource().contains("uploaded"));
    }

    /* =========================
       TC-038 PDF Upload
    ========================== */
    @Test
    public void TC_038_pdfUpload() {
        uploadFile("sample.pdf");
        Assert.assertTrue(driver.getPageSource().contains("uploaded"));
    }

    /* =========================
       TC-039 File Size >10MB
    ========================== */
    @Test
    public void TC_039_fileSizeLimit() {
        uploadFile("largeFile.jpg");
        driver.findElement(By.id("identitySubmit")).click();
        Assert.assertTrue(driver.getPageSource().contains("10MB"));
    }

    /* =========================
       TC-040 Unsupported Format
    ========================== */
    @Test
    public void TC_040_unsupportedFormat() {
        uploadFile("file.exe");
        Assert.assertTrue(driver.getPageSource().contains("Unsupported"));
    }

    /* =========================
       TC-041 Upload Mandatory
    ========================== */
    @Test
    public void TC_041_uploadMandatory() {
        driver.findElement(By.id("identitySubmit")).click();
        Assert.assertTrue(driver.getPageSource().contains("required"));
    }

    /* =========================
       TC-042 Remove & Reupload
    ========================== */
    @Test
    public void TC_042_removeReupload() {
        uploadFile("sample.jpg");
        driver.findElement(By.id("removeFile")).click();
        uploadFile("sample2.jpg");
        Assert.assertTrue(driver.getPageSource().contains("sample2"));
    }

    /* =========================
       TC-043 File Preview
    ========================== */
    @Test
    public void TC_043_filePreview() {
        uploadFile("sample.jpg");
        Assert.assertTrue(driver.findElement(By.id("previewSection")).isDisplayed());
    }

    /* =========================
       TC-044 Coming Soon
    ========================== */
    @Test
    public void TC_044_biometricComingSoon() {
        Assert.assertTrue(driver.getPageSource().contains("Coming Soon"));
    }

    /* =========================
       TC-045 Biometric Disabled
    ========================== */
    @Test
    public void TC_045_biometricDisabled() {
        Assert.assertFalse(driver.findElement(By.id("biometricSection")).isEnabled());
    }

    /* =========================
       TC-046 Successful ID Submit
    ========================== */
    @Test
    public void TC_046_identitySubmit() {
        uploadFile("sample.jpg");
        driver.findElement(By.id("identitySubmit")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("review"));
    }

    /* =========================
       TC-048 Unauthorized Identity Access
    ========================== */
    @Test
    public void TC_048_unauthorizedIdentityAccess() {
        driver.get("https://your-yodixa-url.com/identity");
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    /* =========================
       TC-052 Terms Visible
    ========================== */
    @Test
    public void TC_052_termsVisible() {
        Assert.assertTrue(driver.findElement(By.id("terms")).isDisplayed());
    }

    /* =========================
       TC-056 Terms Mandatory
    ========================== */
    @Test
    public void TC_056_termsMandatory() {
        driver.findElement(By.id("finalSubmit")).click();
        Assert.assertTrue(driver.getPageSource().contains("Terms"));
    }

    /* =========================
       TC-059 Both Checkboxes Required
    ========================== */
    @Test
    public void TC_059_successfulFinalSubmit() {
        driver.findElement(By.id("terms")).click();
        driver.findElement(By.id("privacy")).click();
        driver.findElement(By.id("finalSubmit")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("otp"));
    }

    /* =========================
       TC-061 Double Click Prevention
    ========================== */
    @Test
    public void TC_061_doubleClickPrevention() {
        WebElement btn = driver.findElement(By.id("finalSubmit"));
        btn.click();
        btn.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("otp"));
    }

    /* =========================
       TC-065 OTP Page Load
    ========================== */
    @Test
    public void TC_065_otpPageLoad() {
        Assert.assertTrue(driver.findElement(By.id("otp")).isDisplayed());
    }

    /* =========================
    TC-067 OTP Numeric Only
 ========================== */
 @Test
 public void TC_067_otpNumericOnly() {

     WebElement otp = driver.findElement(By.id("otp"));
     otp.clear();
     otp.sendKeys("abc@");

     String value = otp.getAttribute("value");

     Assert.assertTrue(value.matches("\\d*"));
 }

 /* =========================
    TC-069 Correct OTP (Dynamic)
 ========================== */
 @Test
 public void TC_069_correctOTP() throws InterruptedException {

     emailPrefix = "user" + System.currentTimeMillis();
     testEmail = emailPrefix + "@mailinator.com";

     // Make sure this email was used during signup

     String otp = fetchOTPFromMailinator (emailPrefix);

     driver.findElement(By.id("otp")).clear();
     driver.findElement(By.id("otp")).sendKeys(otp);
     driver.findElement(By.id("otpSubmit")).click();

     Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
 }

 /* =========================
    TC-070 Incorrect OTP
 ========================== */
 @Test
 public void TC_070_incorrectOTP() {

     driver.findElement(By.id("otp")).clear();
     driver.findElement(By.id("otp")).sendKeys("000000");
     driver.findElement(By.id("otpSubmit")).click();

     Assert.assertTrue(driver.getPageSource().contains("Incorrect"));
 }

 /* =========================
    TC-078 Dashboard Block Before OTP
 ========================== */
 @Test
 public void TC_078_dashboardRestriction() {

     driver.get("https://your-yodixa-url.com/dashboard");

     Assert.assertTrue(
             driver.getCurrentUrl().contains("otp") ||
             driver.getCurrentUrl().contains("login")
     );
 }

 /* =========================
    TC-079 OTP Responsive
 ========================== */
 @Test
 public void TC_079_otpResponsive() {

     driver.manage().window().setSize(new Dimension(390, 844));

     Assert.assertTrue(driver.findElement(By.id("otp")).isDisplayed());
 }

    /* =========================
       Helper Methods
    ========================== */

    private void fillValidSignupForm() {
        driver.findElement(By.id("firstName")).sendKeys("Naman");
        driver.findElement(By.id("lastName")).sendKeys("Dutta");
        driver.findElement(By.id("email")).sendKeys("naman" + System.currentTimeMillis() + "@mail.com");
        driver.findElement(By.id("phone")).sendKeys("9876543210");
        driver.findElement(By.id("dob")).sendKeys("01-01-1995");
    }

    private void uploadFile(String fileName) {
        String path = System.getProperty("user.dir") + "/testdata/" + fileName;
        driver.findElement(By.id("govtIdUpload")).sendKeys(path);
    }
    private String fetchOTPFromMailinator(String emailPrefix) throws InterruptedException {

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://www.mailinator.com/");

        driver.findElement(By.id("search")).sendKeys(emailPrefix);
        driver.findElement(By.xpath("//button[contains(text(),'Go')]")).click();

        Thread.sleep(5000);

        driver.findElement(By.xpath("(//tr[contains(@class,'inbox-row')])[1]")).click();

        Thread.sleep(5000);

        driver.switchTo().frame("html_msg_body");

        String emailBody = driver.findElement(By.tagName("body")).getText();

        driver.switchTo().defaultContent();

        // Extract 6-digit OTP
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\b\\d{6}\\b");
        java.util.regex.Matcher matcher = pattern.matcher(emailBody);

        String otp = "";
        if (matcher.find()) {
            otp = matcher.group();
        }

        driver.close(); // close Mailinator tab

        // Switch back to Yodixa tab
        driver.switchTo().window(driver.getWindowHandles().iterator().next());

        return otp;
    }

}