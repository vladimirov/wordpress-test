package appmanager;

import org.openqa.selenium.*;

import java.util.Properties;

public class SiteHelper extends HelperBase {
    private final Properties properties;
    JavascriptExecutor jse = (JavascriptExecutor) driver;

    public String postTitle = "Test Post " + System.currentTimeMillis();
    public String pageNotFoundTitle = "Oops! That page can’t be found.";

    private By createdPostTitleLocator = By.cssSelector("h1.entry-title");
    private By adminBarLocator = By.cssSelector("a.ab-item");
    private By postTitleLocator = By.name("post_title");

    public SiteHelper(WebDriver driver) {
        super(driver);
        properties = new Properties();
    }

    public void addNewPostButtonClick() {
        click(By.xpath("//a[@href='post-new.php']"));
    }

    public void enterPostTitle() {
        type(postTitleLocator, postTitle);
    }

    public void enterTestContent() {
        click(By.id("content-html"));
        type(By.className("wp-editor-area"), Keys.chord(Keys.CONTROL, "v"));
    }


    public void publishPost() {
//        driver.switchTo().defaultContent();
        scrollUp();
        jse.executeScript("document.getElementById('original_publish').setAttribute('type', 'text')");//to change attribute of element
        type(By.id("original_publish"), "test");
        submit(By.id("original_publish"));

        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException e) {
            click(By.id("publish"));
            waitToBePresent(By.id("message"));
        }
        click(By.id("publish"));
        waitToBePresent(By.id("message"));
    }

    public void scrollUp() {

//        WebElement element = driver.findElement(By.tagName("header"));

        jse.executeScript("window.scrollBy(0,250)", "");

    }

    public void searchTestPostInAdmin() {
        type(By.id("post-search-input"), postTitle);
        click(By.id("search-submit"));
    }

    public void openTestPostPage() {
        click(By.cssSelector("a.row-title"));
        click(By.id("sample-permalink"));
        waitToBePresent(By.cssSelector("div#primary"));
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
