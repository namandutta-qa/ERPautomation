package com.erp;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.erp.base.BaseTest;
import com.erp.pages.ChatPage;
import com.erp.utils.ExtentManager;

public class ChatTest extends BaseTest {

    @Test(priority = 1)
    public void TC_207_OpenOneToOneChat() {

        ExtentManager.getTest().info("TC_207: Open one-to-one chat");

        ChatPage chat = new ChatPage(driver);
        chat.openChat();

        Assert.assertTrue(chat.isChatWindowVisible());

        ExtentManager.getTest().pass("Chat window opened and visible");
    }

    @Test(priority = 2)
    public void TC_208_SendTextMessage() {

        ExtentManager.getTest().info("TC_208: Send text message");

        ChatPage chat = new ChatPage(driver);
        String msg = "Hello Automation";

        chat.sendMessage(msg);
        Assert.assertTrue(chat.isMessageDisplayed(msg));

        ExtentManager.getTest().pass("Message sent and displayed in chat");
    }

    @Test(priority = 3)
    public void TC_210_VerifyTimestamp() {

        ExtentManager.getTest().info("TC_210: Verify message timestamp");

        ChatPage chat = new ChatPage(driver);
        Assert.assertTrue(chat.isTimestampDisplayed());

        ExtentManager.getTest().pass("Timestamp is displayed for messages");
    }

    @Test(priority = 4)
    public void TC_211_MessagePersistenceAfterRefresh() {

        ExtentManager.getTest().info("TC_211: Message persistence after refresh");

        ChatPage chat = new ChatPage(driver);
        String msg = "Persistent Message";

        chat.sendMessage(msg);
        driver.navigate().refresh();

        Assert.assertTrue(chat.isMessageDisplayed(msg));

        ExtentManager.getTest().pass("Message persisted after page refresh");
    }

    @Test(priority = 5)
    public void TC_214_DeleteOwnMessage() {

        ExtentManager.getTest().info("TC_214: Delete own message");

        ChatPage chat = new ChatPage(driver);
        chat.deleteOwnMessage();

        ExtentManager.getTest().pass("Message deleted successfully");
    }

    // ================= GROUP TESTS =================

    @Test(priority = 6)
    public void TC_216_CreateGroup() {

        ExtentManager.getTest().info("TC_216: Create group and verify");

        ChatPage chat = new ChatPage(driver);
        String groupName = "AutomationGroup";

        chat.createGroup(groupName, "testUser2");
        Assert.assertTrue(chat.isGroupCreated(groupName));

        ExtentManager.getTest().pass("Group created and verified");
    }

    @Test(priority = 7)
    public void TC_217_SendGroupMessage() {

        ExtentManager.getTest().info("TC_217: Send group message");

        ChatPage chat = new ChatPage(driver);
        String msg = "Hello Group";

        chat.sendMessage(msg);
        Assert.assertTrue(chat.isMessageDisplayed(msg));

        ExtentManager.getTest().pass("Group message sent and displayed");
    }

    @Test(priority = 8)
    public void TC_224_RemoveMember() {

        ExtentManager.getTest().info("TC_224: Remove member from group");

        ChatPage chat = new ChatPage(driver);
        chat.removeMember();

        ExtentManager.getTest().pass("Member removed from group");
    }
}