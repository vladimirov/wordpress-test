package pages;

import appmanager.HelperBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;

public class SitePage extends HelperBase {

    public SitePage(WebDriver driver) {
        super(driver);
    }

    public String name = "Name " + System.currentTimeMillis();
    public String email = System.currentTimeMillis() + "@mail.com";

    private By adminBarLocator = By.cssSelector("a.ab-item");
    private By searchInputLocator = By.xpath("//input[@type='search']");
    private By searchButtonLocator = By.cssSelector("button.search-submit");
    private By continueReadingLocator = By.cssSelector("a.more-link");
    private By commentInputLocator = By.id("comment");
    private By commentContentLocator = By.cssSelector("div.comment-content");
    private By nameLocator = By.id("author");
    private By emailLocator = By.id("email");
    private By postCommentLocator = By.id("submit");
    private By paginationLocator = By.cssSelector("nav.navigation.pagination");


    public boolean adminBarIsDisplayed() {
        return isElementPresent(adminBarLocator);
    }

    public void searchTestPost() {
        scrollTillElementIsVisible(searchInputLocator);
        type(searchInputLocator, "The quick brown fox jumps over the lazy dog");
        click(searchButtonLocator);
    }

    public void openPostFromSearchResults() {
        click(continueReadingLocator);
    }

    public void postComment() {
        waitForPageToLoad(driver);
        scrollTillElementIsVisible(commentInputLocator);
        type(commentInputLocator, "The quick brown fox jumps over the lazy dog");
        type(nameLocator, name);
        type(emailLocator, email);
        click(postCommentLocator);
    }

    public void scrollToComment() {
        scrollTillElementIsVisible(commentContentLocator);
    }

    public boolean commentTextIsDisplayed() {
        return isTextDisplayed(commentContentLocator, "The quick brown fox jumps over the lazy dog");
    }

    public void screenBrowserConsole() {
        openBrowserConsole();
        screenshotCapture("TestConsoleErrors");
    }

    public void scrollToPagination() {
        scrollTillElementIsVisible(paginationLocator);
    }

    public void getConsoleErrors() {
        Logs logs = driver.manage().logs();
        LogEntries logEntries = logs.get(LogType.BROWSER);
        for (LogEntry logEntry : logEntries) {
            if (logEntry.getMessage().toLowerCase().contains("failed to load resource")) {
                System.out.println("Error Message in Console:" + logEntry.getMessage());
            } else if (logEntry.getMessage().toLowerCase().contains("warning")) {
                System.out.println("Warning Message in Console:" + logEntry.getMessage());
            } else {
                System.out.println("Information Message in Console:" + logEntry.getMessage());
            }
        }
    }

}

