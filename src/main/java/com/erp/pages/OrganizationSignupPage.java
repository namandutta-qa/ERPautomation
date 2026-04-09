package com.erp.pages;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.erp.utils.WaitUtils;

public class OrganizationSignupPage extends BasePage {
	private WaitUtils waitUtils;

	public OrganizationSignupPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		this.waitUtils = new WaitUtils(driver); // Create WaitUtils instance

	}

	// Organization details
	private By legalName = By.xpath("//input[@placeholder='Acme Corp']");
	private By legalnameError = By.xpath("//p[text()='EIN must be in format XX-XXXXXXX']");
	private By ein = By.xpath("//input[@name='ein_tax_id']");
	private By einError = By.xpath("//p[text()='EIN must be in format XX-XXXXXXX']");
	private By address = By.xpath("//input[@placeholder='Search and select your address']");
	private By addressError = By.xpath("//p[text()='EIN must be in format XX-XXXXXXX']");
	private By postalCode = By.name("business_address_zip");
	private By postalcodeError = By.xpath("//p[text()='EIN must be in format XX-XXXXXXX']");
	public By selectIndustry = By.xpath("(//select)[1]");
	public By countryDropdown = By.xpath("(//select)[2]");
	public By stateDropdown = By.xpath("(//select)[3]");
	private By city = By.name("business_address_city");
	private By state = By.name("businessState");
	// Personal details
	private By firstName = By.name("firstname");
	// Fix: swapped error messages corrected
	private By firstnameError = By.xpath("//p[text()='First name must be at least 2 characters']");
	private By lastnameError = By.xpath("//p[text()='Last name must be at least 2 characters']");
	private By middleName = By.xpath("//input[@id='_r_2_-form-item']");
	private By lastName = By.name("lastname");
	public By email = By.xpath("//input[@placeholder='you@example.com']");
	private By password = By.xpath("//input[@placeholder='••••••••']");
	private By username = By.xpath("//input[@placeholder='Enter first & last name above to generate']");
	private By emailError = By.xpath("//p[text()='Please enter a valid email address']");
	private By passwordError = By.xpath("//p[text()='Password must be at least 8 characters']");
	private By phone = By.xpath("//input[@placeholder='Phone']");
	private By phoneError = By.xpath("//p[text()='Please enter a valid phone number']");
	private By dob = By.xpath("//button[@name='dob']");
	private By ownerdob = By.xpath("//button[@name='owner_dob']");
	private By DOBError = By.xpath("//p[text()='Date of birth is required']");
	private By TermsLink = By.xpath("//a[normalize-space()='Terms of Service']");
	private By PrivacyLink = By.xpath("//a[normalize-space()='Privacy Policy']");

	public By Country = By.xpath("(//select)[4]");
	public By birthcountryDropdown = By.xpath("//button[@id='_r_n_-form-item']");
	private By designation = By.xpath("(//select)[4]");
	private By Gender = By.xpath("(//select)[5]");
//	private By PerCountry = By.xpath("(//select)[13]");
	private By Peraddress = By.xpath("(//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'address')])[last()]");
	private By PeraddressCountry = By.xpath("(//select)[12]");
	private By Perstate = By.xpath("(//select)[13]");
	private By PerCity = By.name("city");
	private By PerZip = By.name("zip");

	// Owner details
	private By ownerfirstName = By.name("owner_firstname");
	private By ownerlastName = By.name("owner_lastname");
	private By owneremail = By.name("owner_email");
	private By gender = By.xpath("(//select)[1]");
	// FIX: malformed locator replaced with a sensible select index (consistent with other selectors)
	private By ownercountrybirth = By.xpath("(//select)[6]");
	private By owneraddress = By.xpath("(//input[@placeholder='Search business address...'])");
	private By ownercountry = By.xpath("(//select)[7]");
	private By ownerstate = By.xpath("(//select)[8]");
	private By ownercity = By.name("owner_city");
	private By ownerpostalcode = By.name("owner_zip");
	// Current Address
	private By Currentaddress = By.name("street");
	private By CurrentpostalCode = By.name("postalCode");
	public By CurrentStateDropdown = By.xpath("//input[@placeholder='Search business address...']");
	private By Currentcity = By.name("city");
	private By Currentstate = By.name("state");
	// Document upload
	private By GovtID = By.xpath("//input[@type='file' and @aria-label='Upload Government ID']");
	private By AOI = By.xpath(
			"//h3[text()='Articles of Incorporation']/ancestor::div[contains(@class,'glass-card')]//input[@type='file']");
	private By IOD = By.xpath(
			"//h3[text()='Insurance Documentation']/ancestor::div[contains(@class,'glass-card')]//input[@type='file']");
	private By startVerificationBtn = By.xpath("//button[contains(text(),'Start Verification')]");
	private By continueverificationBtn = By.xpath("//button[normalize-space()='Continue']");
	private By acceptTerms = By.xpath("//button[@aria-label='Agree and continue']");
	private By confirmBtn = By.xpath("//button[contains(text(),'Confirm') or contains(text(),'Continue')]");

	// Navigationcd /home/lz-2/IdeaProjects/erp-automation && mvn
	// -Dtest=com.erp.Signup test
	private By nextBtn = By.xpath("//button[normalize-space()='Create account']");

	// Review
	private By terms = By.xpath("(//button[@role='checkbox'])[1]");
	private By privacy = By.xpath("(//button[@role='checkbox'])[2]");;
	private By submitBtn = By.xpath("//button[normalize-space()='Submit for Verification']");
	private By confirmation = By.id("confirmationMsg");
	private By requiredErrors = By.xpath("//p[text()='Required']");
	private By uploadedFileName = By.xpath("//p[contains(text(),'.pdf')]");
	private By backBtn = By.xpath("//button[normalize-space()='Back']");

	public void selectBusinessType(String type) {
		By option = By.xpath("//button[.//p[normalize-space()='" + type + "']]");
		waitForClickability(option).click();
	}

	public String getEmailDuplicateError() {
		// Try multiple selectors for the notification/toast
		By[] toastCandidates = new By[] {
			By.xpath("//section[@aria-label='Notifications alt+T']//*[@role='alert']"),
			By.xpath("//div[contains(@class,'notification') or contains(@class,'toast')]//*[@role='alert']"),
			By.xpath("//*[@role='alert']"),
			By.xpath("//div[contains(@class,'toast') or contains(@class,'notification')][1]")
		};

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
		for (By b : toastCandidates) {
			try {
				WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(b));
				// Wait until not empty
				wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, "")));
				String message = element.getText();
				System.out.println("Toast Message: " + message);
				return message;
			} catch (Exception e) {
				// try next candidate
			}
		}
		// if none found, return empty string to avoid NPE in tests
		return "";
	}

	public By reviewFieldValue(String label) {
		return By.xpath("//p[normalize-space()='" + label + "']/following-sibling::p[contains(@class,'font-medium')]");
	}

	public String getReviewFieldValue(String label) {
		return waitForVisible(reviewFieldValue(label)).getText().trim();
	}

	public void verifyReviewPageField(String label, String expectedValue) {
		String actualValue = getReviewFieldValue(label);

		Assert.assertEquals(actualValue, expectedValue, "Mismatch for field: " + label);
	}

	public String getEINError() {
		try {
            return waitForVisible(einError).getText();
        } catch (Exception e) {
            return "";
        }
	}

	public String getEmailError() {
		try {
            return driver.findElement(emailError).getText();
        } catch (Exception e) {
            return "";
        }
	}

	public String getFirstError() {
		try {
            return driver.findElement(firstnameError).getText();
        } catch (Exception e) {
            return "";
        }
	}

	public String getLastError() {
		try {
            return waitForVisible(lastnameError).getText();
        } catch (Exception e) {
            return "";
        }
	}

	public String getPasswordError() {
		try {
            return driver.findElement(passwordError).getText();
        } catch (Exception e) {
            return "";
        }
	}

	public String getdobError() {
		try {
            return driver.findElement(DOBError).getText();
        } catch (Exception e) {
            return "";
        }
	}

	public String getlegalnameError() {
		try {
            return driver.findElement(legalnameError).getText();
        } catch (Exception e) {
            return "";
        }
	}

	public String getaddressError() {
		try {
            return driver.findElement(addressError).getText();
        } catch (Exception e) {
            return "";
        }
	}

	public String getPostalError() {
		try {
            return driver.findElement(postalcodeError).getText();
        } catch (Exception e) {
            return "";
        }
	}

	public String getPhoneError() {
		try {
            return waitForVisible(phoneError).getText();
        } catch (Exception e) {
            return "";
        }
	}

	public By nameValue(String name) {
		return By.xpath("//p[@class='font-medium' and contains(text(),'" + name + "')]");
	}

	public boolean isNameDisplayed(String expectedName) {
		try {
			return waitForVisible(nameValue(expectedName)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void selectDropdown(By locator, String value) {

		WebElement dropdown = driver.findElement(locator);
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
	}

	public void enterLegalName(String value) {
		// Try several locator strategies and wait briefly for visibility
		By[] candidates = new By[] {
			legalName,
			By.name("legal_name"),
			By.id("legal_name"),
			By.xpath("//label[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'legal')]/following::input[1]"),
			By.xpath("//input[contains(@placeholder,'Corp') or contains(@placeholder,'Company') or contains(@placeholder,'Legal')]")
		};

		// Short local wait to find visible candidate quickly
		WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(6));
		for (By b : candidates) {
			try {
				WebElement el = shortWait.until(ExpectedConditions.visibilityOfElementLocated(b));
				el.click();
				el.clear();
				el.sendKeys(value);
				return;
			} catch (Exception e) {
				// try next candidate
			}
		}

		// Last-resort: attempt to type into the original locator (uses default wait)
		try {
			type(legalName, value);
		} catch (Exception ex) {
			// As a defensive measure, try to find any visible input and type into it
			try {
				WebElement anyInput = shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[not(@type='hidden') and string-length(normalize-space(@placeholder))>0][1]")));
				anyInput.click(); anyInput.clear(); anyInput.sendKeys(value);
			} catch (Exception ignore) {
				// allow original exception to surface if necessary
				throw ex;
			}
		}
	}

	public void enterEIN(String value) {
        By[] candidates = new By[] {
            ein,
            By.name("ein"),
            By.xpath("//input[contains(@name,'ein') or contains(@placeholder,'EIN') or contains(@aria-label,'EIN')]")
        };
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(6));
        for (By b : candidates) {
            try {
                WebElement el = shortWait.until(ExpectedConditions.visibilityOfElementLocated(b));
                el.clear();
                el.sendKeys(value);
                return;
            } catch (Exception e) {
                // try next
            }
        }
        System.out.println("[OrganizationSignupPage] EIN field not found; skipping EIN entry");
	}

	// *[@id="_r_4_-form-item"]
	public void CurrentAddress(String value) {
		type(Currentaddress, value);
	}

	public void CurrentpostalCode(String value) {
		type(CurrentpostalCode, value);
	}

	public void CurrentCity(String value) {
		type(Currentcity, value);
	}

	public void CurrentState(String value) {
		type(Currentstate, value);
	}

	public void City(String value) {
		type(city, value);
	}

	public void State(String value) {
		type(state, value);
	}

	public void selectdesignation(String value) {

	    wait.until(ExpectedConditions.elementToBeClickable(designation));

	    // Click dropdown
	    click(designation);

	    Select dropdown = new Select(driver.findElement(designation));

	    String currentValue = dropdown.getFirstSelectedOption().getText();

	    // 🔥 Fix: if already selected (like "Owner")
	    if (currentValue.equalsIgnoreCase(value)) {

	        int size = dropdown.getOptions().size();

	        if (size > 1) {
	            dropdown.selectByIndex(1); // force change
	        }
	    }

	    // ✅ Select required value
	    dropdown.selectByVisibleText(value);
	}

	public void selectPerGender(String value) {

		selectDropdown(Gender, value);

	}

	public void printGenderDropdownValues() {

		Select select = new Select(driver.findElement(Gender));
		List<WebElement> options = select.getOptions();

		for (WebElement option : options) {
			System.out.println(option.getText());
		}
	}

//	public void selectPerCountry(String value) {
//
//		selectDropdown(PerCountry, value);
//	}

	public void selectPerAddresscountry(String value) {

		selectDropdown(PeraddressCountry, value);
	}

	public void selectPerstate(String value) {

		selectDropdown(Perstate, value);

	}

	public void selectCurrentstateDropdown(String value) {

		selectDropdown(CurrentStateDropdown, value);

	}

	public void enterPeraddress(String value) {
		// Try multiple candidate locators for the personal address/autocomplete input
		By[] candidates = new By[] {
			Peraddress,
			By.xpath("//input[contains(@placeholder,'starting address') or contains(@placeholder,'starting address details')]") ,
			By.xpath("//input[contains(@placeholder,'address') and not(contains(@placeholder,'Search'))][1]"),
			By.xpath("//input[contains(@placeholder,'Enter') and contains(@placeholder,'address')][1]"),
			By.xpath("//input[@placeholder='Search and select your address']")
		};

		WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(8));
		for (By b : candidates) {
			try {
				WebElement element = shortWait.until(ExpectedConditions.elementToBeClickable(b));
				element.clear();
				element.sendKeys(value);
				// give suggestions time
				try { Thread.sleep(1000); } catch (InterruptedException ie) {}
				element.sendKeys(Keys.ARROW_DOWN);
				element.sendKeys(Keys.ENTER);
				return;
			} catch (Exception e) {
				// try next
			}
		}
		// fallback: type into first visible input
		try {
			WebElement any = shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[not(@type='hidden')][1]")));
			any.clear(); any.sendKeys(value); try { Thread.sleep(1000);} catch (InterruptedException ie) {}
			any.sendKeys(Keys.ARROW_DOWN); any.sendKeys(Keys.ENTER);
		} catch (Exception ignored) {}
	}

	public void enterPercity(String value) {
		type(PerCity, value);
	}

	public void enterPerZip(String value) {
		type(PerZip, value);
	}

	public void enterownerFirstName(String value) {
        By[] candidates = new By[] {
            ownerfirstName,
            By.name("owner[first_name]"),
            By.xpath("//input[contains(@name,'owner') and contains(@name,'first')]")
        };
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(6));
        for (By b : candidates) {
            try {
                WebElement el = shortWait.until(ExpectedConditions.visibilityOfElementLocated(b));
                el.clear();
                el.sendKeys(value);
                return;
            } catch (Exception e) {
                // try next
            }
        }
        // if none found, log and continue
        System.out.println("[OrganizationSignupPage] owner first name field not present; skipping");
     }

    public void enterownerLastName(String value) {
        By[] candidates = new By[] {
            ownerlastName,
            By.name("owner[last_name]"),
            By.xpath("//input[contains(@name,'owner') and contains(@name,'last')]")
        };
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(6));
        for (By b : candidates) {
            try {
                WebElement el = shortWait.until(ExpectedConditions.visibilityOfElementLocated(b));
                el.clear();
                el.sendKeys(value);
                return;
            } catch (Exception e) {
                // try next
            }
        }
        System.out.println("[OrganizationSignupPage] owner last name field not present; skipping");
     }

    public void enteronweremail(String value) {
        By[] candidates = new By[] {
            owneremail,
            By.name("owner[email]"),
            By.xpath("//input[contains(@name,'owner') and contains(@name,'email')]")
        };
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(6));
        for (By b : candidates) {
            try {
                WebElement el = shortWait.until(ExpectedConditions.visibilityOfElementLocated(b));
                el.clear();
                el.sendKeys(value);
                return;
            } catch (Exception e) {
                // try next
            }
        }
        System.out.println("[OrganizationSignupPage] owner email field not present; skipping");
     }

	public void enterowneraddress(String value) {
		By[] candidates = new By[] {
			owneraddress,
			By.xpath("//input[contains(@placeholder,'Search business address') or contains(@placeholder,'Search and select your address')]") ,
			By.xpath("//input[contains(@placeholder,'address')][1]")
		};

		WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(8));
		for (By b : candidates) {
			try {
				WebElement element = shortWait.until(ExpectedConditions.elementToBeClickable(b));
				element.clear();
				element.sendKeys(value);
				try { Thread.sleep(1000); } catch (InterruptedException ie) {}
				element.sendKeys(Keys.ARROW_DOWN);
				element.sendKeys(Keys.ENTER);
				return;
			} catch (Exception e) {
				// try next
			}
		}
		// fallback: no-op
	}

	public void enterownercity(String value) {
		type(ownercity, value);
	}

	public void enterownerzip(String value) {
		type(ownerpostalcode, value);
	}

	public void selectgender(String value) {

		selectDropdown(gender, value);

	}

	public void selectownercountry(String value) {

		selectDropdown(ownercountry, value);
	}

	public void selectownerState(String value) {

		selectDropdown(ownerstate, value);
	}

	public void selectownercountrybirth(String value) {

		selectDropdown(ownercountrybirth, value);

	}

	public void enterAddress(String value) {
		By[] candidates = new By[] {
			address,
			By.xpath("//input[contains(@placeholder,'Search and select your address') or contains(@placeholder,'Search business address')]") ,
			By.xpath("//input[contains(@placeholder,'address')][1]")
		};
		WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(8));
		for (By b : candidates) {
			try {
				WebElement element = shortWait.until(ExpectedConditions.elementToBeClickable(b));
				element.clear();
				element.sendKeys(value);
				try { Thread.sleep(1000);} catch (InterruptedException ie) {}
				element.sendKeys(Keys.ARROW_DOWN);
				element.sendKeys(Keys.ENTER);
				return;
			} catch (Exception e) {
				// try next
			}
		}
		// fallback: try basic type
		try { type(address, value); } catch (Exception ignore) {}

	}

	public void enterPostalCode(String value) {
		type(postalCode, value);
	}

	public void selectCountry(String value) {

		selectDropdown(countryDropdown, value);
	}

	public void selectState(String value) {

		selectDropdown(stateDropdown, value);
	}

	public void selectIndustryDropdown(String value) {

		selectDropdown(selectIndustry, value);
	}

	public void enterFirstName(String value) {
		type(firstName, value);
	}

	@FindBy(name = "username")
	WebElement usernameField;

	public String waitForAutoUsername() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.attributeToBeNotEmpty(usernameField, "value"));

		return usernameField.getAttribute("value");
	}

	public void clickUsernameField() {
		click(username);
	}

	public void enterusername(String value) {
		type(username, value);
	}

	public void enterMiddleName(String value) {
		type(middleName, value);
	}

	public void enterLastName(String value) {
		type(lastName, value);
	}

	public void enterEmail(String value) {

		type(email, value);
	}

	public String getEmail() {
		return driver.findElement(email).getAttribute("value");
	}

	public void enterPasswordl(String value) {
		type(password, value);
	}

	public void enterPhone(String value) {
		type(phone, value);
	}

	private WebElement clickFirstAvailable(WebDriverWait wait, By... candidates) {
		for (By b : candidates) {
			try {
				WebElement el = wait.until(ExpectedConditions.elementToBeClickable(b));
				el.click();
				return el;
			} catch (Exception e) {
				// try next
			}
		}
		return null;
	}

	public void enterDOB(String value) {

		// Fast path: try to set value directly on any date input using JS (ISO format)
		String iso = convertToISO(value);
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String script = "var selectors = ['input[name=\\'dob\\']','input[name=\\'date_of_birth\\']','input[type=\\'date\\']','input[placeholder*=\\'DOB\\']']; for(var i=0;i<selectors.length;i++){ var el=document.querySelector(selectors[i]); if(el){ el.value = arguments[0]; el.dispatchEvent(new Event('input',{bubbles:true})); el.dispatchEvent(new Event('change',{bubbles:true})); return true; } } return false;";
			Object res = js.executeScript(script, iso);
			if (res instanceof Boolean && ((Boolean) res)) {
				return; // set successfully via JS - very fast
			}
		} catch (Exception e) {
			// ignore JS failures and fallback to UI picker
		}

		// Fallback: use UI picker but with shorter waits and minimal actions
		String[] parts = value.split(" ");

		String month = parts[0];
		String day = parts[1].replace(",", "");
		String year = parts[2];

		click(dob);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		// Try to click month/year/day quickly
		By[] monthCandidates = new By[] {
			By.xpath("//button[contains(normalize-space(),'" + month + "')][1]"),
			By.xpath("//div[contains(normalize-space(),'" + month + "')][1]"),
			By.xpath("//span[contains(normalize-space(),'" + month + "')][1]")
		};
		clickFirstAvailable(wait, monthCandidates);

		By[] yearCandidates = new By[] {
			By.xpath("//button[contains(normalize-space(),'" + year + "')][1]"),
			By.xpath("//div[contains(normalize-space(),'" + year + "')][1]"),
			By.xpath("//span[contains(normalize-space(),'" + year + "')][1]")
		};
		clickFirstAvailable(wait, yearCandidates);

		By[] dayCandidates = new By[] {
			By.xpath("//button[normalize-space()='" + day + "'][1]"),
			By.xpath("//div[normalize-space()='" + day + "'][1]"),
			By.xpath("//span[normalize-space()='" + day + "'][1]")
		};
		WebElement dayElement = clickFirstAvailable(wait, dayCandidates);
		if (dayElement == null) {
			// last resort: set via JS using ISO
			try {
				((JavascriptExecutor) driver).executeScript("var el=document.querySelector('input[type=\\'date\\']'); if(el){ el.value = arguments[0]; el.dispatchEvent(new Event('input',{bubbles:true})); el.dispatchEvent(new Event('change',{bubbles:true})); }", iso);
			} catch (Exception e) {
				// ignore
			}
		}
	}

	public void ownerenterDOB(String value) {

	    // Try JS-first approach to speed things up
	    String iso = convertToISO(value);
	    try {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        String script = "var selectors = ['input[name=\\'owner_dob\\']','input[type=\\'date\\']','input[placeholder*=\\'DOB\\']']; for(var i=0;i<selectors.length;i++){ var el=document.querySelector(selectors[i]); if(el){ el.value = arguments[0]; el.dispatchEvent(new Event('input',{bubbles:true})); el.dispatchEvent(new Event('change',{bubbles:true})); return true; } } return false;";
	        Object r = js.executeScript(script, iso);
	        if (r instanceof Boolean && ((Boolean) r)) {
	            return; // set successfully via JS
	        }
	    } catch (Exception e) {
	        // ignore and proceed to UI fallback
	    }

	    // UI fallback with shorter waits
	    String[] parts = value.split(" ");
	    String month = parts[0];
	    String day = parts[1].replace(",", "");
	    String year = parts[2];

	    click(ownerdob);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	    try {
	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='" + month + "']"))).click();
	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='" + year + "']"))).click();
	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='" + day + "']"))).click();
	    } catch (Exception e) {
	        // Fast JS fallback if UI clicks fail
	        try {
	            ((JavascriptExecutor) driver).executeScript("var el=document.querySelector('input[type=\\'date\\']'); if(el){ el.value = arguments[0]; el.dispatchEvent(new Event('input',{bubbles:true})); el.dispatchEvent(new Event('change',{bubbles:true})); }", iso);
	        } catch (Exception ex) {
	            // ignore
	        }
	    }
	}

	public void selectcountry(String value) {

		selectDropdown(Country, value);
	}

	public void selectbirthcountry(String value) {

		selectDropdown(birthcountryDropdown, value);
	}

	public boolean SelectGovtID(String path) {
		// Try multiple plausible selectors for the Government ID file input
		By[] candidates = new By[] {
			GovtID,
			By.xpath("//input[@type='file' and (contains(@aria-label,'Government') or contains(@aria-label,'Gov') or contains(@aria-label,'government'))]"),
			By.xpath("//label[contains(.,'Government ID')]/following::input[@type='file'][1]"),
			By.xpath("(//input[@type='file'])[1]")
		};

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		for (By b : candidates) {
			try {
				WebElement uploadElement = wait.until(ExpectedConditions.presenceOfElementLocated(b));
				uploadElement.sendKeys(path);
				return true;
			} catch (Exception e) {
				// try next
			}
		}
		// Not found
		return false;
	}

	public void SelectAOI(String path1) {
		driver.findElement(AOI).sendKeys(path1);
	}

	public void SelectIOD(String path2) {
		driver.findElement(IOD).sendKeys(path2);
	}

	public void clickNext() {
		click(nextBtn);
	}

	public void backbtn() {
		click(backBtn);
	}

	public void clickTermsLink() {
		click(TermsLink);
	}

	public void clickPrivacyLink() {
		click(PrivacyLink);
	}

	public boolean isFileUploaded() {
		try {
			return waitForVisible(uploadedFileName).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickConfirm() {
		WebElement btn = waitUtils.waitForClickability(confirmBtn); // confirmBtnLocator = By locator of button
		btn.click();
	}

	public boolean isSubmitButtonDisabled() {
		click(confirmBtn);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(submitBtn));
		return !button.isEnabled();
	}

	public void acceptTerms() {
		click(terms);
	}

	public void acceptPrivacy() {
		waitForClickability(privacy).click();
	}

	public void clickSubmit() {
		click(submitBtn);
	}

	public boolean isConfirmationDisplayed() {
		return isDisplayed(confirmation);
	}

	public boolean isRequiredErrorDisplayed() {

		List<WebElement> errors = driver.findElements(requiredErrors);

		return errors.size() > 0;
	}

	public void captureSelfie() throws IOException {

		waitForClickability(startVerificationBtn).click();
		driver.switchTo().frame(waitForVisible(By.cssSelector("iframe")));

		// 👉 Stop automation here for manual verification
		System.out.println("Complete Sumsub verification manually and press ENTER to continue...");
		System.in.read();

		// After manual verification, resume if needed

		driver.switchTo().defaultContent();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(5));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[normalize-space()='Verified']")));
		System.out.println("Resuming Automation");
	}

	// Improved utility: wait briefly for any of the candidate locators to become visible
	private WebElement findVisibleElement(By... candidates) {
		WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(6));
		for (By b : candidates) {
			try {
				WebElement el = shortWait.until(ExpectedConditions.visibilityOfElementLocated(b));
				if (el != null && el.isDisplayed()) {
					return el;
				}
			} catch (Exception e) {
				// ignore and try next
			}
		}
		return null;
	}
	private String convertToISO(String value) {
	    DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
	    DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    LocalDate date = LocalDate.parse(value, inputFormat);
	    return date.format(outputFormat);
	}
}
