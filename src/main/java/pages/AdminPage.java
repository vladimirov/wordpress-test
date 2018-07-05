package pages;

import appmanager.HelperBase;
import org.openqa.selenium.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class AdminPage extends HelperBase {

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    protected JavascriptExecutor jse = (JavascriptExecutor) driver;
    public String postTitle = "Test Post " + System.currentTimeMillis() + " The quick brown fox jumps over the lazy dog";

    private By addPostButtonLocator = By.xpath("//a[@href='post-new.php']");
    private By createdPostTitleLocator = By.cssSelector("h1.entry-title");
    private By postTitleLocator = By.cssSelector("a.row-title");
    private By postTitleInputLocator = By.name("post_title");
    private By textTabLocator = By.id("content-html");
    private By textAreaLocator = By.className("wp-editor-area");
    private By postSearchLocator = By.id("post-search-input");
    private By postSearchButtonLocator = By.id("search-submit");
    private By publishPostButtonLocator = By.id("publish");
    private By hiddenPublishInputLocator = By.id("original_publish");
    private By successMessageLocator = By.id("message");
    private By permalinkLocator = By.id("sample-permalink");
    private By moveToTrashLocator = By.cssSelector("a.submitdelete.deletion");
    private By helpLinkLocator = By.id("contextual-help-link");
    private By commentAuthorLocator = By.cssSelector("div.comment-author");
    private By approveCommentLocator = By.cssSelector("span.approve");
    private By columnResponseLocator = By.cssSelector("div.response-links");
    private By testPostPageLocator = By.cssSelector("div#primary");
    private By commentsMenu = By.id("menu-comments");
    private By approveSectionLocator = By.xpath("//tbody[@id='the-comment-list']/tr[1]");


    public void gotoHomePage() {
        click(By.linkText("Home"));
    }

    public void gotoPostsPage() {
        click(By.linkText("Posts"));
    }

    public void gotoMediaPage() {
        click(By.linkText("Media"));
    }

    public void gotoPluginsPage() {
        click(By.xpath("//a[@href='plugins.php']"));
    }

    public void gotoUsersPage() {
        click(By.linkText("Users"));
    }

    public void gotoSettingsPage() {
        click(By.linkText("Settings"));
    }

    public void gotoCommentsPage() {
        click(commentsMenu);
    }

    public String url() throws URISyntaxException {
        URI uri = new URI(String.valueOf(driver.getCurrentUrl()));
        String path = uri.getPath();
        String pageName = path.substring(path.lastIndexOf('/') + 1);
        return String.valueOf(pageName);
    }

    public void copyTestContent() {
        click(By.id("js-copy-button"));
    }

    public void addNewPostButtonClick() {
        click(addPostButtonLocator);
    }

    public void enterPostTitle() {
        type(postTitleInputLocator, postTitle);
    }

    public void enterTestContent() {
        click(textTabLocator);
        type(textAreaLocator, Keys.chord(Keys.CONTROL, "v"));
    }

    public void publishPost() {
        scrollTillElementIsVisible(helpLinkLocator);
        jse.executeScript("document.getElementById('original_publish').setAttribute('type', 'text')");//to change attribute of element
        type(hiddenPublishInputLocator, "test");
        submit(hiddenPublishInputLocator);
        if (isAlertPresent()){
            driver.switchTo().alert().accept();
        }
        click(publishPostButtonLocator);
        waitToBePresent(successMessageLocator);
    }

    public void searchTestPostInAdmin() {
        type(postSearchLocator, postTitle);
        click(postSearchButtonLocator);
    }

    public void clickOnTestPostPermalink() {
        click(postTitleLocator);
        click(permalinkLocator);
    }

    public void openTestPostPageOnSite() {
        waitToBePresent(testPostPageLocator);
    }

    public void deleteTestPost() {
        click(moveToTrashLocator);
    }

    public boolean movedToTrashMessageIsDisplayed() {
        return textIsDisplayed(moveToTrashLocator, "1 post moved to the Trash. ");
    }

    public boolean postTitleTextIsDisplayed() {
        return isTextDisplayed(createdPostTitleLocator, postTitle);
    }


    public void approveComment() {
        hoverOnElement(commentAuthorLocator);
        hoverOnElement(approveSectionLocator);
        click(approveCommentLocator);
    }

    public void openPostWithComment() {
        click(By.cssSelector("a.comments-view-item-link"));
    }


}