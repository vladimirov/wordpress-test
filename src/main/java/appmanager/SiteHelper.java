package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

public class SiteHelper extends HelperBase {

    private final Properties properties;

    public SiteHelper(WebDriver driver) {
        super(driver);
        properties = new Properties();
    }

    public void addNewPostButtonClick() {
        click(By.xpath("//a[@href='post-new.php']"));
    }

    public void enterPostTitle() {
        type(By.name("post_title"), "Test Post");
    }

    public void enterTestContent() {
        click(By.id("content-html"));
        type(By.className("wp-editor-area"), Keys.chord(Keys.CONTROL, "v"));
    }

    public void publish() {
        click(By.id("publish"));
    }


}
