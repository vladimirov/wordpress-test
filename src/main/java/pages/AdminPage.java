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

    protected JavascriptExecutor jse = (JavascriptExecutor) driver;
    public String postTitle = "Test Post " + System.currentTimeMillis() + " The quick brown fox jumps over the lazy dog";

    private By createdPostTitleLocator = By.cssSelector("h1.entry-title");

    public String url() throws URISyntaxException {
        URI uri = new URI(String.valueOf(driver.getCurrentUrl()));
        String path = uri.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    public String testContent() throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/main/resources/test-content.txt")),
                StandardCharsets.UTF_8);
    }

    public boolean postTitleTextIsDisplayed() {
        return isTextDisplayed(createdPostTitleLocator, postTitle);
    }

}