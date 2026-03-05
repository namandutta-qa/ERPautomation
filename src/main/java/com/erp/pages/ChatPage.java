package com.erp.pages;

import org.openqa.selenium.*;
import java.util.List;
import com.erp.utils.WaitUtils;

public class ChatPage {

    private WebDriver driver;
    private WaitUtils wait;

    public ChatPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    // ===== Common Locators =====
    private By messageBtn = By.id("messageBtn");
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

    // ===== One-to-One =====

    public void openChat() {
        wait.waitForClickability(messageBtn).click();
        wait.waitForVisibility(chatWindow);
    }

    public void sendMessage(String text) {
        wait.waitForVisibility(messageInput).sendKeys(text);
        wait.waitForClickability(sendBtn).click();
    }

    public boolean isMessageDisplayed(String text) {
        List<WebElement> allMessages = driver.findElements(messages);
        return allMessages.stream().anyMatch(e -> e.getText().contains(text));
    }

    public boolean isTimestampDisplayed() {
        return driver.findElements(timestamp).size() > 0;
    }

    public void deleteOwnMessage() {
        wait.waitForClickability(deleteBtn).click();
    }

    public boolean isChatWindowVisible() {
        return driver.findElements(chatWindow).size() > 0;
    }

    // ===== Group =====

    public void createGroup(String groupName, String member) {
        wait.waitForClickability(createGroupBtn).click();
        wait.waitForVisibility(groupNameInput).sendKeys(groupName);
        wait.waitForVisibility(addMemberInput).sendKeys(member);
        wait.waitForClickability(saveGroupBtn).click();
    }

    public void removeMember() {
        wait.waitForClickability(removeMemberBtn).click();
    }

    public boolean isGroupCreated(String groupName) {
        return driver.getPageSource().contains(groupName);
    }
}