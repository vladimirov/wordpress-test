package pages;

import appmanager.HelperBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SitePage extends HelperBase {

    public SitePage(WebDriver driver) {
        super(driver);
    }

    public String comment = "The quick brown fox jumps over the lazy dog";
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

    public void makeClick(){
        click(By.cssSelector("body"));
    }

    public void screenBrowserConsole(){
        openBrowserConsole();
        screenShot("TestConsoleErrors");
    }

}

