package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SiteHelper extends HelperBase {

    public SiteHelper(WebDriver driver) {
        super(driver);
    }

    public String simplePostTitle = "The quick brown fox jumps over the lazy dog";
    public String comment = "The quick brown fox jumps over the lazy dog";
    public String pageNotFoundTitle = "Oops! That page can’t be found.";
    public String name = "Name " + System.currentTimeMillis();
    public String email = System.currentTimeMillis() + "@mail.com";

    private By adminBarLocator = By.cssSelector("a.ab-item");
    private By searchInputLocator = By.xpath("//input[@type='search']");
    private By searchButtonLocator = By.cssSelector("button.search-submit");
    private By postHeadingLocator = By.cssSelector("h2.entry-title");
    private By continueReadingLocator = By.cssSelector("a.more-link");
    private By commentInputLocator = By.id("comment");
    private By commentContentLocator = By.cssSelector("div.comment-content");
    private By nameLocator = By.id("author");
    private By emailLocator = By.id("email");
    private By postCommentLocator = By.id("submit");
    private By commentTextLocator = By.xpath("//div[@class='comment-content']/p");
    private By asideLocator = By.id("secondary");


    public boolean adminBarIsDisplayed() {
        return isElementPresent(adminBarLocator);
    }

    public void searchTestPost() {
        scrollTillElementIsVisible(searchInputLocator);
        type(searchInputLocator, simplePostTitle);
        click(searchButtonLocator);
    }

    public void openPostFromSearchResults() {
        click(continueReadingLocator);
    }

    public void postComment() {

        waitForPageToLoad(driver);

        scrollTillElementIsVisible(commentInputLocator);
        type(commentInputLocator, comment);
        type(nameLocator, name);
        type(emailLocator, email);
        click(postCommentLocator);
    }

    public void scrollToComment() {
        scrollTillElementIsVisible(commentContentLocator);
    }

    public boolean commentTextIsDisplayed() {
        return isTextDisplayed(comment, commentContentLocator);
    }

    public void makeClick(){
        click(By.cssSelector("body"));
    }

    public void screenBrowserConsole(){
        openBrowserConsole();
        screenShot("TestConsoleErrors");
    }

    //    public void groupPage() {
//        if (isElementPresent(By.tagName("h1"))
//                && driver.findElement(By.tagName("h1")).getText().equals("Groups")
//                && isElementPresent(By.name("new"))) {
//            return;
//        }
//        click(By.linkText("groups"));
//    }
}

