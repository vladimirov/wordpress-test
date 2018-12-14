package pages;

import appmanager.HelperBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AdminPage extends HelperBase {

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    private JavascriptExecutor jse = (JavascriptExecutor) driver;
    private String postTitle = "Test Post - The quick brown fox jumps over the lazy dog";

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

    public String url() throws URISyntaxException {
        URI uri = new URI(String.valueOf(driver.getCurrentUrl()));
        String path = uri.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    public void gotoPostsPage() {
        click(By.linkText("Posts"));
    }

    public void closeTipsPopUp() {
        click(By.cssSelector("button.components-buttoncomponents-icon-buttonnux-dot-tip__disable"));
    }

    public void addNewPostButtonClick() {
        click(addPostButtonLocator);
    }

    public void enterPostTitle() {
        try {
            type(postTitleInputLocator, postTitle);
        } catch (Exception e){
            type(By.id("post-title-0"), postTitle);
        }
    }

    public void enterTestContent() throws IOException {
        try{
            click(textTabLocator);
            type(textAreaLocator, testContent());
        } catch (Exception e){
            type(By.cssSelector("div.components-autocomplete"), testContent());
        }
    }

    public void publishPost() {
        scrollTillElementIsVisible(helpLinkLocator);
        jse.executeScript("document.getElementById('original_publish').setAttribute('type', 'text')");//to change attribute of element
        type(hiddenPublishInputLocator, "test");
        submit(hiddenPublishInputLocator);
        if (isAlertPresent()) {
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

    public String testContent() throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/main/resources/test-content.txt")),
                StandardCharsets.UTF_8);
    }

    public boolean postTitleTextIsDisplayed() {
        return isTextDisplayed(createdPostTitleLocator, postTitle);
    }

}