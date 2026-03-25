package com.erp.pages;

import org.openqa.selenium.*;
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
	private By chatWindow = By.id("chatWindow");
	private By messageInput = By.id("chatInput");
	private By sendBtn = By.id("sendBtn");
	private By messages = By.cssSelector(".chat-message");
	private By deleteBtn = By.cssSelector(".delete-msg");
	private By timestamp = By.cssSelector(".timestamp");
	private By emojiBtn = By.id("emojiBtn");

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
		List<WebElement> allMessages = driver.findElements(messages);
		return allMessages.stream().anyMatch(e -> e.getText().contains(text));
	}

	public boolean isTimestampDisplayed() {
		return driver.findElements(timestamp).size() > 0;
	}

	public void deleteOwnMessage() {
		waitForClickability(deleteBtn).click();
	}

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

	// ✅ Open Chat User
	public void openChatWithUser(String name) {

		By user = By.xpath(String.format("//button[.//span[normalize-space()='%s']]", name));

		waitForClickability(user).click();

		ExtentManager.getTest().info("Opened chat with user: " + name);
	}

	// ✅ Send Message
	public void sendMessage(String text) {
		waitForVisible(messageInput).sendKeys(text);
		waitForClickability(sendBtn).click();

		ExtentManager.getTest().info("Sent message: " + text);
	}

	// ✅ Wait for Message
	public boolean waitForMessage(String text) {
		return new WebDriverWait(driver, Duration.ofSeconds(20))
				.until(d -> driver.findElements(messages).stream().anyMatch(e -> e.getText().contains(text)));
	}
}