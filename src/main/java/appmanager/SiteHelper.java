package appmanager;

import org.openqa.selenium.*;

import java.util.Properties;

public class SiteHelper extends HelperBase {

    private final Properties properties;
    protected JavascriptExecutor jse = (JavascriptExecutor) driver;

    public String postTitle = "Test Post " + System.currentTimeMillis();
    public String pageNotFoundTitle = "Oops! That page can’t be found.";

    private By addPostButtonLocator = By.xpath("//a[@href='post-new.php']");
    private By createdPostTitleLocator = By.cssSelector("h1.entry-title");
    private By adminBarLocator = By.cssSelector("a.ab-item");
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
    private By testPostPageLocator = By.cssSelector("div#primary");

    public SiteHelper(WebDriver driver) {
        super(driver);
        properties = new Properties();
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
//        driver.switchTo().defaultContent();
//        scrollUp();



        jse.executeScript("document.getElementById('original_publish').setAttribute('type', 'text')");//to change attribute of element
        type(hiddenPublishInputLocator, "test");
        submit(hiddenPublishInputLocator);
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException e) {
            click(publishPostButtonLocator);
            waitToBePresent(successMessageLocator);
        }
        click(publishPostButtonLocator);
        waitToBePresent(successMessageLocator);
    }

    public void searchTestPostInAdmin() {
        type(postSearchLocator, postTitle);
        click(postSearchButtonLocator);
    }

    public void openTestPostPage() {
        click(postTitleLocator);
        click(permalinkLocator);
        waitToBePresent(testPostPageLocator);
    }

    public void deleteTestPost() {

    }

    public boolean postTitleIsDisplayed() {
        return isElementPresent(createdPostTitleLocator);
    }

    public boolean postTitleTextIsDisplayed() {
        return isTextDisplayed(postTitle, createdPostTitleLocator);
    }

    public boolean adminBarIsDisplayed() {
        return isElementPresent(adminBarLocator);
    }


}
