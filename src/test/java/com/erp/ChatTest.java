package com.erp;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.erp.base.BaseTest;
import com.erp.pages.AffiliatePage;
import com.erp.pages.ChatPage;
import com.erp.utils.DriverFactory;
import com.erp.utils.DriverManager;
import com.erp.utils.ExtentManager;

public class ChatTest extends BaseTest {

		ChatPage chat;

//		@BeforeMethod
//
//		public void setupRole(Method method) throws InterruptedException {
//			chat = new ChatPage(driver);
//
////		    if (!method.getName().equals("TC_007_verifyUnauthorizedAccess")) {
////		        loginAsRole("homeowner");
////		        goTo("/messages");
////		    }
//
//		}

//    @Test
//    public void TC_207_OpenOneToOneChat() {
//
//        ExtentManager.getTest().info("TC_207: Open one-to-one chat");
//
//        ChatPage chat = new ChatPage(driver);
//        chat.openChat();
//
//        Assert.assertTrue(chat.isChatWindowVisible());
//
//        ExtentManager.getTest().pass("Chat window opened and visible");
//    }
//
//    @Test
//    public void TC_208_SendTextMessage() {
//
//        ExtentManager.getTest().info("TC_208: Send text message");
//
//        ChatPage chat = new ChatPage(driver);
//        String msg = "Hello Automation";
//
//        chat.sendMessage(msg);
//        Assert.assertTrue(chat.isMessageDisplayed(msg));
//
//        ExtentManager.getTest().pass("Message sent and displayed in chat");
//    }
//
//    @Test
//    public void TC_210_VerifyTimestamp() {
//
//        ExtentManager.getTest().info("TC_210: Verify message timestamp");
//
//        ChatPage chat = new ChatPage(driver);
//        Assert.assertTrue(chat.isTimestampDisplayed());
//
//        ExtentManager.getTest().pass("Timestamp is displayed for messages");
//    }
//
//    @Test
//    public void TC_211_MessagePersistenceAfterRefresh() {
//
//        ExtentManager.getTest().info("TC_211: Message persistence after refresh");
//
//        ChatPage chat = new ChatPage(driver);
//        String msg = "Persistent Message";
//
//        chat.sendMessage(msg);
//        driver.navigate().refresh();
//
//        Assert.assertTrue(chat.isMessageDisplayed(msg));
//
//        ExtentManager.getTest().pass("Message persisted after page refresh");
//    }
//
//    @Test
//    public void TC_214_DeleteOwnMessage() {
//
//        ExtentManager.getTest().info("TC_214: Delete own message");
//
//        ChatPage chat = new ChatPage(driver);
//        chat.deleteOwnMessage();
//
//        ExtentManager.getTest().pass("Message deleted successfully");
//    }
//
//    // ================= GROUP TESTS =================
//
//    @Test
//    public void TC_216_CreateGroup() {
//
//        ExtentManager.getTest().info("TC_216: Create group and verify");
//
//        ChatPage chat = new ChatPage(driver);
//        String groupName = "AutomationGroup";
//
//        chat.createGroup(groupName, "testUser2");
//        Assert.assertTrue(chat.isGroupCreated(groupName));
//
//        ExtentManager.getTest().pass("Group created and verified");
//    }
//
//    @Test
//    public void TC_217_SendGroupMessage() {
//
//        ExtentManager.getTest().info("TC_217: Send group message");
//
//        ChatPage chat = new ChatPage(driver);
//        String msg = "Hello Group";
//
//        chat.sendMessage(msg);
//        Assert.assertTrue(chat.isMessageDisplayed(msg));
//
//        ExtentManager.getTest().pass("Group message sent and displayed");
//    }
//
//    @Test
//    public void TC_224_RemoveMember() {
//
//        ExtentManager.getTest().info("TC_224: Remove member from group");
//
//        ChatPage chat = new ChatPage(driver);
//        chat.removeMember();
//
//        ExtentManager.getTest().pass("Member removed from group");
//    }
	    // ✅ Email → Password
	    private static final Map<String, String> userPasswordMap = Map.of(
	            "jonsnow31@yopmail.com", "12345678",
	            "user1773813211554@mailinator.com", "Test@121",
	            "alex.brown1773992481399@mailinator.com", "Test@121",
	            "user1773812829576@mailinator.com", "Test@121"
	    );

	    // ✅ Email → UI Name
	    private static final Map<String, String> userNameMap = Map.of(
	            "jonsnow31@yopmail.com", "John Doe",
	            "user1773813211554@mailinator.com", "Kevin Harrell",
	            "alex.brown1773992481399@mailinator.com", "Alex Brown",
	            "user1773812829576@mailinator.com", "Test User"
	    );

	    private static final String[] users = userPasswordMap.keySet().toArray(new String[0]);

	    private static Map<String, ChatPage> chatMap = new HashMap<>();

	    @BeforeClass
	    public void setupUsers() {

	        for (String user : users) {

	            WebDriver driver = DriverFactory.createDriver(user);

	            login(driver, user, userPasswordMap.get(user));

	            driver.get("https://yodixa.lusites.xyz/app/messages");

	            chatMap.put(user, new ChatPage(driver));
	        }
	    }

	    @Test
	    public void verifyParallelChat() {

	        for (int i = 0; i < users.length; i++) {
	            for (int j = 0; j < users.length; j++) {

	                if (i == j) continue;

	                String senderEmail = users[i];
	                String receiverEmail = users[j];

	                String senderName = userNameMap.get(senderEmail);
	                String receiverName = userNameMap.get(receiverEmail);

	                String message = "MSG_" + System.currentTimeMillis();

	                ChatPage sender = chatMap.get(senderEmail);
	                ChatPage receiver = chatMap.get(receiverEmail);

	                // ✅ Send
	                sender.openChatWithUser(receiverName);
	                sender.sendMessage(message);

	                // ✅ Verify
	                receiver.openChatWithUser(senderName);

	                boolean received = receiver.waitForMessage(message);

	                Assert.assertTrue(received,
	                        "Message failed from " + senderName + " to " + receiverName);
	            }
	        }
	    }

	    @AfterClass
	    public void tearDown() {
	        chatMap.values().forEach(cp -> DriverManager.quitDriver());
	    }

	    // ✅ Login Method
	    private void login(WebDriver driver, String email, String password) {

	        driver.get("https://yodixa.lusites.xyz/app");

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys(email);
	        driver.findElement(By.name("password")).sendKeys(password);

	        driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).click();

	        wait.until(ExpectedConditions.urlContains("app"));
	    }
		
}