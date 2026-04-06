package com.erp.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.support.ui.WebDriverWait;
import com.erp.utils.ExtentManager;

public class ChatPage extends BasePage {

	private WebDriver driver;

	public ChatPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// ===== Common Locators =====
	private By messageBtn = By.id("messageBtn");
	private By userName = By.xpath("//span[@class='truncate text-sm font-semibold' and normalize-space()='%s']");
	private By chatWindow = By.xpath("//div[contains(@class,'overflow-y-auto') and contains(@class,'flex-1')]");
	private By messageInput = By.xpath("//textarea[@placeholder='Type a message...']");
	private By sendBtn = By.xpath("//button[@aria-label='Send message']");
	private By getMessages(String text) {
	    return By.xpath("//div[contains(@class,'relative')][.//span[normalize-space()='" + text + "']]");
	}
//	private By deleteBtn = By.xpath("//div[@role='menuitem' and normalize-space()='Delete']");
	private By timestamp = By.xpath("//div[contains(@class,'relative')]//time");
	private By emojiBtn = By.id("emojiBtn");
	private By threeDotMenu = By.id("radix-_r_1t_");
	// ===== Group Locators =====
	private By createGroupBtn = By.id("createGroupBtn");
	private By groupNameInput = By.id("groupName");
	private By addMemberInput = By.id("addMember");
	private By saveGroupBtn = By.id("saveGroup");
	private By removeMemberBtn = By.cssSelector(".remove-member");

	// ✅ Locators
	private By chatItems = By.xpath("//div[contains(@class,'chat-item')]");
	private By emptyState = By.xpath("//h2[normalize-space()='Select a message']");
	private By connectBtn = By.xpath("//button[normalize-space()='Send Request']");

	public boolean isChatEmpty() {

		waitForCondition(
				driver -> driver.findElements(chatItems).size() > 0 || driver.findElements(emptyState).size() > 0);

		boolean isEmpty = driver.findElements(chatItems).isEmpty();

		ExtentManager.getTest().info("Chat empty status: " + isEmpty);

		return isEmpty;
	}

	public void sendConnectionRequest(String userName) {

		// ✅ Step 1: Search / Navigate to user profile
		By userCard = By.xpath("/span[normalize-space()='" + userName + "']]");

		waitForClickability(userCard).click();

		ExtentManager.getTest().info("Navigated to user profile: " + userName);

		// ✅ Step 2: Wait for profile page to load (important)
		waitForVisible(connectBtn);

		// ✅ Step 3: Click Send Request
		waitForClickability(connectBtn).click();

		ExtentManager.getTest().info("Clicked on Send Request button");
	}

	public void handleEmptyChatFlow() {

		if (isChatEmpty()) {
			ExtentManager.getTest().info("Chat is empty → triggering request flow");
			sendConnectionRequest("");
		} else {
			ExtentManager.getTest().info("Chat already exists → skipping request flow");
		}
	}

	public void sendAndAcceptConnectionRequest(String userBEmail, String userBPassword) {

		// ✅ Step 1: Send request from User A
		sendConnectionRequest("");
		ExtentManager.getTest().info("User A sent connection request");

		String parentWindow = driver.getWindowHandle();

		// ✅ Step 2: Open new tab
		((JavascriptExecutor) driver).executeScript("window.open()");

		Set<String> allWindows = driver.getWindowHandles();

		for (String win : allWindows) {
			if (!win.equals(parentWindow)) {
				driver.switchTo().window(win);
				break;
			}
		}

		ExtentManager.getTest().info("Switched to User B window");

		// ✅ Step 3: Navigate
		driver.get("YOUR_APP_URL");

		// ✅ Step 4: Login as User B
		login(userBEmail, userBPassword);

		// ✅ Step 5: Navigate to Requests
		navigateToRequestsPage();

		// ✅ Step 6: Accept Request (FIXED)
		By acceptBtn = By.xpath("//button[normalize-space()='Accept']");
		waitForClickability(acceptBtn).click();

		ExtentManager.getTest().pass("User B accepted connection request");

		// ✅ Step 7: Switch back
		driver.switchTo().window(parentWindow);

		ExtentManager.getTest().info("Switched back to User A");
	}

	public void navigateToRequestsPage() {

		By requestsTab = By.xpath("//a[contains(text(),'Requests') or contains(text(),'Connections')]");

		waitForClickability(requestsTab).click();

		ExtentManager.getTest().info("Navigated to Requests page");
	}

	public void login(String email, String password) {

		waitForVisible(By.id("email")).sendKeys(email);
		waitForVisible(By.id("password")).sendKeys(password);

		waitForClickability(By.xpath("//button[normalize-space()='Login']")).click();

		waitForCondition(driver -> driver.getCurrentUrl().contains("dashboard"));

		ExtentManager.getTest().info("Login successful");
	}
	// ===== One-to-One =====

	public void openChat() {
		waitForClickability(messageBtn).click();
		waitForVisible(chatWindow);
	}

	public boolean isMessageDisplayed(String text) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'relative')][.//span[normalize-space()='" + text + "']]")
	        ));

	        return message.isDisplayed();

	    } catch (Exception e) {
	        return false;
	    }
	}

	public boolean isTimestampDisplayed() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(timestamp));
			return driver.findElements(timestamp).size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

//	public void deleteOwnMessage() {
//		waitForClickability(deleteBtn).click();
//	}

	public boolean isChatWindowVisible() {
		return driver.findElements(chatWindow).size() > 0;
	}

	// ===== Group =====

	public void createGroup(String groupName, String member) {
		waitForClickability(createGroupBtn).click();
		waitForVisible(groupNameInput).sendKeys(groupName);
		waitForVisible(addMemberInput).sendKeys(member);
		waitForClickability(saveGroupBtn).click();
	}

	public void removeMember() {
		waitForClickability(removeMemberBtn).click();
	}

	public boolean isGroupCreated(String groupName) {
		return driver.getPageSource().contains(groupName);
	}

	public void waitForChatInput() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//textarea[@placeholder='Type a message...']")));
	}

	public void openChatWithUser(String name) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// 🔥 Flexible locator (handles spacing/case issues)
		By userLocator = By.xpath("//button[.//span[contains(normalize-space(),'" + name + "')]]");

		// ✅ Wait for presence first
		WebElement user = wait.until(ExpectedConditions.presenceOfElementLocated(userLocator));

		// ✅ Scroll into view (important for long lists)
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", user);

		// ✅ Wait until clickable
		wait.until(ExpectedConditions.elementToBeClickable(user)).click();

		ExtentManager.getTest().info("Opened chat with user: " + name);
	}

	// ✅ Send Message
	public void sendMessage(String text) {
		waitForVisible(messageInput).sendKeys(text);
		waitForClickability(sendBtn).click();

		ExtentManager.getTest().info("Sent message: " + text);
	}

	public boolean deleteOwnMessage(String msg) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        // 1. Locate message container (parent div)
	        WebElement messageContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//span[normalize-space()='" + msg + "']/ancestor::div[contains(@class,'relative')]")
	        ));

	        // 2. Hover on message (to reveal 3 dots)
	        new Actions(driver).moveToElement(messageContainer).perform();

	        // 3. Find 3-dot button INSIDE that message only
	        WebElement threeDots = messageContainer.findElement(
	                By.xpath(".//button[@aria-label='Message actions']")
	        );

	        // 4. Click 3 dots
	        threeDots.click();

	        // 5. Click Delete option
	        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//div[@role='menuitem' and normalize-space()='Delete']")
	        ));

	        deleteBtn.click();

	        System.out.println("Message deleted: " + msg);
	        return true;

	    } catch (Exception e) {
	        System.out.println("Error deleting message: " + e.getMessage());
	        return false;
	    }
	}

	public boolean waitForMessageVisible(String msg) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//span[normalize-space()='" + msg + "']")
	        ));

	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
}