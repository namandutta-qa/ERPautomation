package com.erp;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import com.erp.base.BaseTest;
import com.erp.pages.ChatPage;
import com.erp.utils.DriverFactory;
import com.erp.utils.ExtentManager;

public class ChatTest extends BaseTest {

	ChatPage chat;

	// ================= SINGLE USER SETUP =================
	@BeforeMethod
	public void setupRole(Method method) throws InterruptedException {

		// 🚫 Skip normal setup for multi-user test
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
		ExtentManager.getTest().info("TC_207: Open one-to-one chat");
		chat.openChatWithUser("Sara kligton");
		Assert.assertTrue(chat.isChatWindowVisible());

		ExtentManager.getTest().pass("Chat window opened and visible");
	}

	@Test
	public void TC_208_SendTextMessage() {
		
		chat.openChatWithUser("Sara kligton");
		ExtentManager.getTest().info("TC_208: Send text message");

		String msg = "Hello Automation";
		chat.sendMessage(msg);

		Assert.assertTrue(chat.isMessageDisplayed(msg));
		ExtentManager.getTest().pass("Message sent and displayed");
	}

	@Test
	public void TC_210_VerifyTimestamp() {
		ExtentManager.getTest().info("TC_210: Verify timestamp");

		Assert.assertTrue(chat.isTimestampDisplayed());
		ExtentManager.getTest().pass("Timestamp verified");
	}
	@Test
	public void TC_211_MessagePersistenceAfterRefresh() {
		ExtentManager.getTest().info("TC_211: Message persistence");
		chat.openChatWithUser("Sara kligton");
		String msg = "Persistent Message";
		chat.sendMessage(msg);

		driver.navigate().refresh();

		Assert.assertTrue(chat.isMessageDisplayed(msg));
		ExtentManager.getTest().pass("Message persisted");
	}

	@Test
	public void TC_214_DeleteOwnMessage() {
		ExtentManager.getTest().info("TC_214: Delete message");

		chat.openChatWithUser("Sara kligton");
		chat.deleteOwnMessage("MSG_1775108997000_01");
		ExtentManager.getTest().pass("Message deleted");
	}
////
////	@Test
////	public void TC_216_CreateGroup() {
////		ExtentManager.getTest().info("TC_216: Create group");
////
////		String groupName = "AutomationGroup";
////		chat.createGroup(groupName, "testUser2");
////
////		Assert.assertTrue(chat.isGroupCreated(groupName));
////		ExtentManager.getTest().pass("Group created");
////	}
////
////	@Test
////	public void TC_217_SendGroupMessage() {
////		ExtentManager.getTest().info("TC_217: Send group message");
////
////		String msg = "Hello Group";
////		chat.sendMessage(msg);
////
////		Assert.assertTrue(chat.isMessageDisplayed(msg));
////		ExtentManager.getTest().pass("Group message sent");
////	}
////
////	@Test
////	public void TC_224_RemoveMember() {
////		ExtentManager.getTest().info("TC_224: Remove member");
////
////		chat.removeMember();
////		ExtentManager.getTest().pass("Member removed");
////	}
//
	// ================= MULTI USER SETUP =================

	private static final Map<String, String> userPasswordMap = Map.of("tom@yopmail.com", "Test@121", "sara@yopmail.com",
			"Test@121");

	private static final Map<String, String> userNameMap = Map.of("tom@yopmail.com", "Tom Customer",
			"sara@yopmail.com", "Sara kligton");

	private static final String[] users = { "tom@yopmail.com", "sara@yopmail.com" };

	private static Map<String, ChatPage> chatMap = new HashMap<>();
	private static Map<String, WebDriver> driverMap = new HashMap<>();

	@BeforeMethod
	public void setupMultiUser(Method method) {

		if (!method.getName().equals("verifyParallelChat"))
			return;

		for (String user : users) {

			WebDriver driver = DriverFactory.createDriver(user);
			driverMap.put(user, driver);

			login(driver, user, userPasswordMap.get(user));
			driver.get("https://yodixa.lusites.xyz/app/messages");

			chatMap.put(user, new ChatPage(driver));
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

	@AfterMethod
	public void cleanMultiUser(Method method) {

		if (!method.getName().equals("verifyParallelChat"))
			return;

		driverMap.values().forEach(WebDriver::quit);
		driverMap.clear();
		chatMap.clear();
	}

	// ================= LOGIN HELPER =================

	private void login(WebDriver driver, String email, String password) {

		driver.get("https://yodixa.lusites.xyz/app");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(password);

		driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).click();

		wait.until(ExpectedConditions.urlContains("/app"));
	}
}