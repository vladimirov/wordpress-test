package appmanager;

import org.openqa.selenium.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class AdminHelper extends HelperBase {

    private final Properties properties;
    protected JavascriptExecutor jse = (JavascriptExecutor) driver;

    public String postTitle = "Test Post " + System.currentTimeMillis() + " The quick brown fox jumps over the lazy dog";
    public String movedToTrashMessage = "1 post moved to the Trash. ";

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
//    private By bulkActionSelectorLocator = By.className("comment.column-comment.has-row-actions.column-primary");
    private By commentAuthorLocator = By.cssSelector("div.comment-author");
    private By approveCommentLocator = By.cssSelector("span.approve");
    private By columnResponseLocator = By.cssSelector("div.response-links");


    public AdminHelper(WebDriver driver) {
        super(driver);
        properties = new Properties();
    }

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

    public void gotoCommentsPage(){
        click(By.linkText("Comments"));
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

    public void gotoTestPostPage(){

    }

    public void deleteTestPost() {
        click(moveToTrashLocator);
    }

    public boolean movedToTrashMessageIsDisplayed() {
        return textIsDisplayed(movedToTrashMessage, moveToTrashLocator);
    }

    public boolean postTitleIsDisplayed() {
        return isElementPresent(createdPostTitleLocator);
    }

    public boolean postTitleTextIsDisplayed() {
        return isTextDisplayed(postTitle, createdPostTitleLocator);
    }



    public void approveComment(){
        hoverOnElement(commentAuthorLocator);
        click(approveCommentLocator);
    }

    public void openPostWithComment (){
        click(columnResponseLocator);
        click(permalinkLocator);
    }


}