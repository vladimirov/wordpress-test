package appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.Properties;

public class SiteHelper extends HelperBase {

    private final Properties properties;
    private String title = "Test Post " + System.currentTimeMillis();
    JavascriptExecutor jse = (JavascriptExecutor) driver;


    public SiteHelper(WebDriver driver) {
        super(driver);
        properties = new Properties();
    }

//    public void openBaseUrl() {
//        driver.get(properties.getProperty("web.baseUrl"));
//    }
//
//    public void openPageNotFoundUrl() {
//        driver.get(properties.getProperty("web.baseUrl"));
//    }

    public void addNewPostButtonClick() {
        click(By.xpath("//a[@href='post-new.php']"));
    }

    public void enterPostTitle() {
        type(By.name("post_title"), title);
    }

    public void enterTestContent() {
        click(By.id("content-html"));
        type(By.className("wp-editor-area"), Keys.chord(Keys.CONTROL, "v"));
    }

    public void publishPost() {
        jse.executeScript("document.getElementById('original_publish').setAttribute('type', 'text')");//to change attribute of element
        type(By.id("original_publish"), "test");
        submit(By.id("original_publish"));
        Alert alert = driver.switchTo().alert();
        alert.accept();
        waitToBePresent(By.id("message"));
        click(By.id("publish"));
    }

    public void scrollUp() {
        jse.executeScript("window.scrollBy(0,-250)", "");
    }

    public void previewPost() {
        click(By.cssSelector("button.handlediv"));
        click(By.id("post-preview"));
    }

    public void openTestPostPage() {
        type(By.id("post-search-input"), title);
        click(By.id("search-submit"));
        click(By.cssSelector("a.row-title"));
        click(By.id("sample-permalink"));
        waitToBePresent(By.cssSelector("div#primary"));
    }
}
