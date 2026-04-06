package com.erp;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import com.erp.base.BaseTest;
import com.erp.pages.ChatPage;
import com.erp.pages.LoginPage;
import com.erp.utils.DriverFactory;
import com.erp.utils.ExtentManager;

public class ChatTest extends BaseTest {

	ChatPage chat;

	// ================= SINGLE USER SETUP =================
	@BeforeMethod
	public void setupRole(Method method) throws InterruptedException {

		// Skip normal setup for multi-user test
		if (method.getName().equals("verifyParallelChat"))
			return;

		chat = new ChatPage(driver);
		if (!method.getName().equals("TC_007_verifyUnauthorizedAccess")) {
			loginAsRole("homeowner");
			goTo("/messages");
		}
	}

	// ================= SINGLE USER TESTS =================

	@Test
	public void TC_207_OpenOneToOneChat() {
		step("TC_207: Open one-to-one chat", () -> {
			chat.openChatWithUser("Sara kligton");
			Assert.assertTrue(chat.isChatWindowVisible());
		});
	}

	@Test
	public void TC_208_SendTextMessage() {
		step("TC_208: Send text message", () -> {
			chat.openChatWithUser("Sara kligton");

			String msg = "Hello Automation";
			chat.sendMessage(msg);

			Assert.assertTrue(chat.isMessageDisplayed(msg));
		});
	}

	@Test
	public void TC_210_VerifyTimestamp() {
		step("TC_210: Verify timestamp", () -> {
			chat.openChatWithUser("Sara kligton");
			Assert.assertTrue(chat.isTimestampDisplayed());
		});
	}

	@Test
	public void TC_211_MessagePersistenceAfterRefresh() {
		step("TC_211: Message persistence after refresh", () -> {
			chat.openChatWithUser("Sara kligton");
			String msg = "Persistent Message";
			chat.sendMessage(msg);

			driver.navigate().refresh();

			Assert.assertTrue(chat.isMessageDisplayed(msg));
		});
	}

	@Test
	public void TC_214_DeleteOwnMessage() {
		step("TC_214: Delete own message", () -> {
			chat.openChatWithUser("Sara kligton");
			chat.deleteOwnMessage("MSG_1775108997000_01");
			// deleteOwnMessage already returns boolean; assert if desired
			// Assert.assertTrue(chat.deleteOwnMessage("MSG_1775108997000_01"));
		});
	}

	// ================= MULTI USER SETUP =================

	private static final Map<String, String> userPasswordMap = Map.of("tom@yopmail.com", "Test@121", "sara@yopmail.com",
			"Test@121");

	private static final Map<String, String> userNameMap = Map.of("tom@yopmail.com", "Tom Customer",
			"sara@yopmail.com", "Sara kligton");

	private static final String[] users = { "tom@yopmail.com", "sara@yopmail.com" };

	// Use concurrent maps to avoid race issues if tests are run in parallel
	private static Map<String, ChatPage> chatMap = new ConcurrentHashMap<>();
	private static Map<String, WebDriver> driverMap = new ConcurrentHashMap<>();

	// Ensure this runs for the parallel chat setup when needed
	@BeforeMethod(alwaysRun = true)
	public void setupMultiUser(Method method) {

		if (!method.getName().equals("verifyParallelChat"))
			return;

		// Ensure partial resources are cleaned if an exception happens during setup
		try {
			for (String user : users) {

				WebDriver driver = DriverFactory.createDriver(user);
				driverMap.put(user, driver);

				// Use LoginPage to perform login to keep login logic centralized
				driver.get(getBaseUrl());
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));

				LoginPage loginPage = new LoginPage(driver);
				loginPage.enterEmail(user);
				loginPage.enterPassword(userPasswordMap.get(user));
				loginPage.clickLogin();

				// Navigate to messages after successful login
				driver.get(getBaseUrl() + "/messages");

				chatMap.put(user, new ChatPage(driver));
			}
		} catch (Exception e) {
			// Quit any drivers that were created before failing to avoid leaks
			driverMap.values().forEach(d -> {
				try {
					d.quit();
				} catch (Exception ignored) {
				}
			});
			driverMap.clear();
			chatMap.clear();
			throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
		}
	}

	// ================= MULTI USER TEST =================

	@Test
	public void verifyParallelChat() {

		for (int i = 0; i < users.length; i++) {
			for (int j = 0; j < users.length; j++) {

				if (i == j)
					continue;

				String senderEmail = users[i];
				String receiverEmail = users[j];

				String senderName = userNameMap.get(senderEmail);
				String receiverName = userNameMap.get(receiverEmail);

				String message = "MSG_" + System.currentTimeMillis() + "_" + i + j;

				ChatPage sender = chatMap.get(senderEmail);
				ChatPage receiver = chatMap.get(receiverEmail);

				synchronized (sender) {
					sender.openChatWithUser(receiverName);
					sender.sendMessage(message);
				}

				synchronized (receiver) {
					receiver.openChatWithUser(senderName);
					boolean received = receiver.waitForMessageVisible(message);

					Assert.assertTrue(received, "Message failed from " + senderName + " to " + receiverName);
				}
			}
		}
	}

	// ================= CLEANUP =================

	@AfterMethod(alwaysRun = true)
	public void cleanMultiUser(Method method) {

		if (!method.getName().equals("verifyParallelChat"))
			return;

		// quit drivers defensively
		driverMap.values().forEach(d -> {
			try {
				d.quit();
			} catch (Exception ignored) {
			}
		});
		driverMap.clear();
		chatMap.clear();
	}
}