package com.erp.base;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import org.openqa.selenium.devtools.v143.network.Network;
import org.openqa.selenium.devtools.v143.network.model.Response;
import org.openqa.selenium.By;
import java.util.Optional;
import java.util.Random;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.erp.utils.ExtentManager;
import com.erp.utils.ScreenshotUtil;
import com.erp.utils.WaitUtils;
import com.erp.utils.ExtentTestNGListener;

// WebDriverManager import 
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;

@Listeners(ExtentTestNGListener.class)
public class BaseTest {

	public WebDriver driver;
	protected static ExtentReports extent;
	protected ExtentTest test;
	String parentWindow;
	// Centralized base URL; default can be overridden with -DbaseUrl=...
	protected String baseUrl;
	protected boolean isMultiUserTest = false;

	// Toggle to enable/disable attaching step-level screenshots to Extent steps.
	// Controlled via system property -DattachStepScreenshots=true|false (defaults
	// to true)
	protected boolean attachStepScreenshots = Boolean.parseBoolean(System.getProperty("attachStepScreenshots", "true"));
	protected DevTools devTools;
	protected AtomicBoolean apiCalled;
	protected List<String> failedApis;
	protected List<String> apiLogs; // Store all API logs for potential reporting

	// Initialize report once
	@BeforeSuite
	public void startReport() {
		extent = ExtentManager.getInstance();
		// initialize baseUrl from system property or default
		baseUrl = System.getProperty("baseUrl", "https://yodixa.lusites.xyz/app");
//		baseUrl = System.getProperty("baseUrl", "http://192.168.0.130:3000");

	}

	protected void captureParentWindow() {
		parentWindow = driver.getWindowHandle();
	}

	protected void switchToNewTab() {
		Set<String> allWindows = driver.getWindowHandles();

		for (String window : allWindows) {
			if (!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}
	}

	protected void switchToMainTab() {
		driver.switchTo().window(parentWindow);
	}

	@Parameters({ "browser" })
	@BeforeMethod
	public void setup(@org.testng.annotations.Optional("chrome") String browser, Method method) {
		if (method.getName().equals("verifyParallelChat")) {
			return;
		}

		String br = (browser == null) ? "chrome" : browser.toLowerCase();

		ChromeOptions chromeOptions = new ChromeOptions();
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		EdgeOptions edgeOptions = new EdgeOptions();

		boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
		if (headless) {
//			chromeOptions.addArguments("--headless=new");
//			firefoxOptions.addArguments("-headless");
//			edgeOptions.addArguments("--headless=new");
		}

//		chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu");
		chromeOptions.addArguments("--disable-autofill", "--disable-password-manager");
		chromeOptions.addArguments("--remote-allow-origins=*");
//		chromeOptions.addArguments("--use-fake-ui-for-media-stream");
//		chromeOptions.addArguments("--use-fake-device-for-media-stream");

		firefoxOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage");
		edgeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage");

		switch (br) {
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(firefoxOptions);
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver(edgeOptions);
			break;

		case "chrome":
		default:
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(chromeOptions);
			break;
		}

		try {
			driver.manage().window().maximize();
		} catch (Exception ignore) {
		}

		// Extent Report
		if (ExtentManager.getTest() == null) {
			ExtentManager.createTest(method.getName());
		}

		// 🔥 DevTools Setup
		// Enable DevTools logging only when explicitly requested to avoid noisy infinite
		// network logs (default: disabled)
		boolean enableDevTools = Boolean.parseBoolean(System.getProperty("enableDevTools", "false"));
		if (enableDevTools && driver instanceof ChromeDriver) {

			devTools = ((ChromeDriver) driver).getDevTools();
			devTools.createSession();

			devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
					Optional.empty()));

			// Reset tracking
			apiCalled = new AtomicBoolean(false);
			failedApis = new ArrayList<>();

			// Store all API logs dynamically
			List<String> apiLogs = new ArrayList<>();

			// Map to correlate requestId -> request payload (postData)
			final Map<String, String> requestPayloads = new HashMap<>();

			// Map to correlate requestId -> action id (from page header)
			final Map<String, String> requestActionIds = new HashMap<>();

			// Listener for request -> capture payload (if any)
			devTools.addListener(Network.requestWillBeSent(), request -> {
				try {
					String reqId = request.getRequestId().toString();
					String url = request.getRequest().getUrl();
					String httpMethod = request.getRequest().getMethod();
					String postData = "";
					if (request.getRequest().getPostData().isPresent()) {
						postData = request.getRequest().getPostData().get();
					}

					// capture test action id from headers if present
					String actionId = "";
					try {
						Map<String, Object> headers = request.getRequest().getHeaders();
						if (headers != null && headers.containsKey("X-Test-Action-Id")) {
							actionId = String.valueOf(headers.get("X-Test-Action-Id"));
						}
					} catch (Exception ignore) {
					}

					// store for correlation with response
					requestPayloads.put(reqId, postData);
					requestActionIds.put(reqId, actionId);

					// Print concise request info
					if (postData == null || postData.isEmpty()) {
						if (actionId == null || actionId.isEmpty()) {
							System.out.println("[API REQUEST] " + httpMethod + " " + url + " (no payload)");
						} else {
							System.out.println("[API REQUEST] " + httpMethod + " " + url + " (no payload) [actionId=" + actionId + "]");
						}
					} else {
						if (actionId == null || actionId.isEmpty()) {
							System.out.println("[API REQUEST] " + httpMethod + " " + url + " PAYLOAD: " + postData);
						} else {
							System.out.println("[API REQUEST] " + httpMethod + " " + url + " PAYLOAD: " + postData + " [actionId=" + actionId + "]");
						}
					}
				} catch (Exception e) {
					System.err.println("[DevTools] Error in requestWillBeSent listener: " + e.getMessage());
				}
			});

			devTools.addListener(Network.responseReceived(), response -> {

				String url = response.getResponse().getUrl();
				int status = response.getResponse().getStatus();

				try {
					Network.GetResponseBodyResponse body = devTools
							.send(Network.getResponseBody(response.getRequestId()));

					String responseBody = body.getBody();

					String log = url + " | " + status + " | " + responseBody;

					apiLogs.add(log);

					// Retrieve request payload if captured
					String reqId = response.getRequestId().toString();
					String requestPayload = requestPayloads.getOrDefault(reqId, "");
					String actionId = requestActionIds.getOrDefault(reqId, "");

					// Print request + response details
					if (status >= 400) {
						if (actionId == null || actionId.isEmpty()) {
							System.out.println("[API RESPONSE] " + url + " | Status: " + status);
						} else {
							System.out.println("[API RESPONSE] " + url + " | Status: " + status + " [actionId=" + actionId + "]");
						}
						if (!requestPayload.isEmpty()) {
							System.out.println("  -> Request payload: " + requestPayload);
						}
						System.out.println("  -> Response body: " + responseBody);
					} else {
						// For non-error responses print concise line + payload if present
						if (requestPayload.isEmpty()) {
							if (actionId == null || actionId.isEmpty()) {
								System.out.println("[API] " + url + " | Status: " + status + " (no payload)");
							} else {
								System.out.println("[API] " + url + " | Status: " + status + " (no payload) [actionId=" + actionId + "]");
							}
						} else {
							if (actionId == null || actionId.isEmpty()) {
								System.out.println("[API] " + url + " | Status: " + status + " | Payload: " + requestPayload);
							} else {
								System.out.println("[API] " + url + " | Status: " + status + " | Payload: " + requestPayload + " [actionId=" + actionId + "]");
							}
						}
					}

					apiCalled.set(true);

					// Capture failures dynamically
					if (status >= 400) {
						failedApis.add(log);
					}

					// cleanup correlated payload
					requestPayloads.remove(reqId);
					requestActionIds.remove(reqId);

				} catch (Exception e) {
					System.out.println("[DevTools] ⚠️ Failed to read response body: " + url + " -> " + e.getMessage());
				}
			});

			// Optional: expose logs globally
			this.apiLogs = apiLogs;
		}
	}

	// Handle result after each test
	@AfterMethod
	public void tearDown(ITestResult result) {

		String screenshotPath = null;

		try {
			if (driver != null) {
				// capture file-based screenshot (for archival) but prefer Base64 for embedding
				screenshotPath = ScreenshotUtil.captureScreenshot(driver,
						result.getName() + "_" + System.currentTimeMillis());
			}
		} catch (Exception e) {
			// Don't fail the tearDown on screenshot errors
			System.err.println("[BaseTest] Failed to capture test-level screenshot: " + e.getMessage());
			e.printStackTrace();
		}

		if (result.getStatus() == ITestResult.FAILURE) {
			if (ExtentManager.getTest() != null) {
				try {
					// Prefer embedding base64 (ensures image shows up in HTML report regardless of
					// path)
					String base64 = null;
					try {
						base64 = ScreenshotUtil.captureScreenshotAsBase64(driver);
					} catch (Exception ignore) {
						// ignore base64 capture error - we'll try file path
					}

					if (base64 != null) {
						ExtentManager.getTest().fail(result.getThrowable(),
								MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
						// also log file path if available so user can open it directly
						if (screenshotPath != null) {
							ExtentManager.getTest().info("Screenshot saved to: " + screenshotPath);
						}
					} else if (screenshotPath != null) {
						try {
							ExtentManager.getTest().fail(result.getThrowable(),
									MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
							ExtentManager.getTest().info("Screenshot saved to: " + screenshotPath);
						} catch (Exception e) {
							// final fallback
							ExtentManager.getTest().fail(result.getThrowable());
						}
					} else {
						ExtentManager.getTest().fail(result.getThrowable());
					}
				} catch (Exception e) {
					ExtentManager.getTest().fail(result.getThrowable());
				}
			}
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			if (ExtentManager.getTest() != null) {
				try {
					String base64 = null;
					try {
						base64 = ScreenshotUtil.captureScreenshotAsBase64(driver);
					} catch (Exception ignore) {
					}

					if (base64 != null) {
						ExtentManager.getTest().pass("Test Passed",
								MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
						if (screenshotPath != null) {
							ExtentManager.getTest().info("Screenshot saved to: " + screenshotPath);
						}
					} else if (screenshotPath != null) {
						try {
							ExtentManager.getTest().pass("Test Passed",
								MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
							ExtentManager.getTest().info("Screenshot saved to: " + screenshotPath);
						} catch (Exception e) {
							ExtentManager.getTest().pass("Test Passed");
						}
					} else {
						ExtentManager.getTest().pass("Test Passed");
					}
				} catch (Exception e) {
					ExtentManager.getTest().pass("Test Passed");
				}
			}
		} else if (result.getStatus() == ITestResult.SKIP) {
			if (ExtentManager.getTest() != null) {
				ExtentManager.getTest().skip("Test Skipped");
			}
		}

		if (driver != null) {
			driver.quit();
		}

		// remove thread-local test
		ExtentManager.removeTest();
	}

	// Flush report once after entire suite
	@AfterSuite
	public void endReport() {
		extent.flush();
	}

	// Navigation helper: accepts either absolute URL or relative path (e.g.,
	// "/login" or "login")
	protected void goTo(String pathOrUrl) {
		String url;
		if (pathOrUrl == null) {
			url = baseUrl;
		} else if (pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://")) {
			url = pathOrUrl;
		} else {
			url = baseUrl + (pathOrUrl.startsWith("/") ? pathOrUrl : "/" + pathOrUrl);
		}

		driver.get(url);
		try {
			ExtentManager.getTest().info("Navigated to: " + url);
		} catch (Exception e) {
			// If extent test not ready, ignore logging to avoid breaking navigation
		}

		// If DevTools is enabled attempt to inject a small script that tags clicks with
		// an action id and patches fetch/XHR to add header X-Test-Action-Id so we can
		// correlate UI actions with network calls.
		try {
			if (devTools != null && driver instanceof JavascriptExecutor) {
				injectActionCorrelationScript();
			}
		} catch (Exception ignore) {
		}
	}

	protected String getBaseUrl() {
		return baseUrl;
	}

	// Login methods
	public void login(String email, String password) {
		goTo("/login");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailField.sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).click();
	}

	public void loginAsRole(String role) throws InterruptedException {

		switch (role.toLowerCase()) {

		case "homeowner":
			login("tom@yopmail.com", "Test@121");
			break;

		case "contractor":
			login("contractor@mailinator.com", "Password123");
			break;

		case "admin":
			login("admin@mailinator.com", "Password123");
			break;

		default:
			throw new IllegalArgumentException("Invalid role: " + role);
		}
		Thread.sleep(2000);
	}

	/**
	 * Utility to execute a test step with Extent reporting. Example usage:
	 * step("Enter first name", () -> page.enterFirstName("Naman")); The method will
	 * log the step, mark it passed on success, or failed on exception and rethrow.
	 */
	protected void step(String name, Runnable action) {
		ExtentManager.getTest().info("STEP: " + name);
		try {
			action.run();

			// Capture screenshot after successful step and attach to report (if enabled and
			// driver available)
			if (attachStepScreenshots) {
				try {
					if (driver != null) {
						// prefer base64 embedding for step screenshots as well
						String base64 = null;
						String stepPath = null;
						try {
							base64 = ScreenshotUtil.captureScreenshotAsBase64(driver);
						} catch (Exception ignore) {
						}
						try {
							stepPath = ScreenshotUtil.captureScreenshot(driver, name);
						} catch (Exception ignore) {
						}

						if (base64 != null) {
							ExtentManager.getTest().pass("PASS: " + name,
									MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
							if (stepPath != null) {
								ExtentManager.getTest().info("Step screenshot saved to: " + stepPath);
							}
						} else if (stepPath != null) {
							ExtentManager.getTest().pass("PASS: " + name,
									MediaEntityBuilder.createScreenCaptureFromPath(stepPath).build());
							ExtentManager.getTest().info("Step screenshot saved to: " + stepPath);
						} else {
							ExtentManager.getTest().pass("PASS: " + name);
						}
					} else {
						ExtentManager.getTest().pass("PASS: " + name);
					}
				} catch (Exception e) {
					// If screenshot capture/attach fails, still mark the step as passed with
					// message
					ExtentManager.getTest().pass("PASS: " + name + " (screenshot failed: " + e.getMessage() + ")");
				}
			} else {
				ExtentManager.getTest().pass("PASS: " + name);
			}

		} catch (Throwable t) {
			// Attempt to capture a screenshot on step failure and attach (if enabled)
			if (attachStepScreenshots) {
				try {
					if (driver != null) {
						String base64 = null;
						String stepPath = null;
						try {
							base64 = ScreenshotUtil.captureScreenshotAsBase64(driver);
						} catch (Exception ignore) {
						}
						try {
							stepPath = ScreenshotUtil.captureScreenshot(driver, name + "-failed");
						} catch (Exception ignore) {
						}

						if (base64 != null) {
							ExtentManager.getTest().fail("FAIL: " + name + " - " + t.getMessage(),
									MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
							if (stepPath != null) {
								ExtentManager.getTest().info("Step screenshot saved to: " + stepPath);
							}
						} else if (stepPath != null) {
							ExtentManager.getTest().fail("FAIL: " + name + " - " + t.getMessage(),
									MediaEntityBuilder.createScreenCaptureFromPath(stepPath).build());
							ExtentManager.getTest().info("Step screenshot saved to: " + stepPath);
						} else {
							ExtentManager.getTest().fail("FAIL: " + name + " - " + t.getMessage());
						}
					} else {
						ExtentManager.getTest().fail("FAIL: " + name + " - " + t.getMessage());
					}
				} catch (Exception e2) {
					ExtentManager.getTest().fail(
							"FAIL: " + name + " - " + t.getMessage() + " (screenshot failed: " + e2.getMessage() + ")");
				}
			} else {
				ExtentManager.getTest().fail("FAIL: " + name + " - " + t.getMessage());
			}
			throw t;
		}
	}

	protected String generateRandomEmail() {
		String[] firstNames = { "john", "jane", "alex", "mike", "sara", "david", "emma" };
		String[] lastNames = { "smith", "johnson", "brown", "williams", "jones", "davis" };

		Random random = new Random();

		String firstName = firstNames[random.nextInt(firstNames.length)];
		String lastName = lastNames[random.nextInt(lastNames.length)];

		long timestamp = System.currentTimeMillis();

		String email = firstName + "." + lastName + timestamp + "@mailinator.com";
		return email.toLowerCase();
	}

	public void validateApis() {

		if (!failedApis.isEmpty()) {
			throw new AssertionError("❌ API Failures: " + failedApis);
		}

		if (!apiCalled.get()) {
			throw new AssertionError("❌ No API was triggered!");
		}
	}

	public void waitForCondition(Function<WebDriver, Boolean> condition) {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(condition);
	}

	/**
	 * Injects a small script into the current page that tags click actions with a
	 * generated action id and patches fetch + XMLHttpRequest to append a header
	 * 'X-Test-Action-Id'. This header is then captured by DevTools listeners so
	 * we can correlate UI actions with network requests.
	 */
	private void injectActionCorrelationScript() {
		try {
			String script = "(function(){"
				+ "if(window.__testActionTrackerInstalled) return;"
				+ "window.__testActionTrackerInstalled=true;"
				+ "window.__testActionIdCounter=0;"
				+ "window.__nextActionId=function(){return Date.now()+'-'+(++window.__testActionIdCounter);};"
				+ "window.__currentActionId='';"
				+ "document.addEventListener('click', function(e){try{window.__currentActionId = window.__nextActionId();}catch(e){}}, true);"
				+ "if(window.fetch){const _fetch = window.fetch.bind(window);window.fetch = function(input, init){try{var action = window.__currentActionId || ''; if(!init) init = {}; if(!init.headers) init.headers = {}; init.headers['X-Test-Action-Id']=action;}catch(e){}; return _fetch(input, init);} }"
				+ "(function(){ var origOpen = XMLHttpRequest.prototype.open; var origSend = XMLHttpRequest.prototype.send; XMLHttpRequest.prototype.open = function(method, url, async, user, password){ this.__url = url; return origOpen.apply(this, arguments); }; XMLHttpRequest.prototype.send = function(body){ try{ var action = window.__currentActionId || ''; this.setRequestHeader && this.setRequestHeader('X-Test-Action-Id', action);}catch(e){} return origSend.apply(this, arguments); }; })();"
				+ "})();";

			((JavascriptExecutor) driver).executeScript(script);
		} catch (Exception e) {
			// don't fail navigation if injection fails
			System.err.println("[DevTools] Failed to inject action-correlation script: " + e.getMessage());
		}
	}

}
