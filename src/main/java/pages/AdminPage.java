package pages;

import appmanager.HelperBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static appmanager.ApplicationManager.baseUrl;

public class AdminPage extends HelperBase {

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    private JavascriptExecutor jse = (JavascriptExecutor) driver;
    private String postTitle = "The quick brown fox jumps over the lazy dog " + System.currentTimeMillis();
    private By addPostButtonLocator = By.xpath("//a[@href='post-new.php']");
    private By postTitleInputLocator = By.name("post_title");
    private By textTabLocator = By.id("content-html");
    private By textAreaLocator = By.className("wp-editor-area");
    private By publishPostButtonLocator = By.id("publish");
    private By hiddenPublishInputLocator = By.id("original_publish");
    private By successMessageLocator = By.id("message");
    private By helpLinkLocator = By.id("contextual-help-link");
    private By themeScreenshotBlankLocator = By.cssSelector("div.theme-screenshot.blank");
    private By blockTextAreaLocator = By.id("mce_0");
    private By blockEditorInserterLocator = By.xpath("//div[@class='editor-inserter']");
    private By blockItemParagraphLocator = By.xpath("//button[@class='editor-block-types-list__item editor-block-list-item-paragraph']");
    private By tipsPopupLocator = By.xpath("//div[@class='components-popover__content']/button");
    private By primaryPublishButtonLocator = By.cssSelector("button.components-button.editor-post-publish-panel__toggle.is-button.is-primary");
    private By publishButtonLargeLocator = By.cssSelector("button.components-button.editor-post-publish-button.is-button.is-default.is-primary.is-large");
    private By publishedTextLocator = By.xpath("//div[text()='Published']");
    private By myAccountLocator = By.cssSelector("li#wp-admin-bar-my-account");
    private By logoutItemLocator = By.cssSelector("li#wp-admin-bar-logout");
    private By taglineInputLocator = By.id("blogdescription");


    public String url() throws URISyntaxException {
        URI uri = new URI(String.valueOf(driver.getCurrentUrl()));
        String path = uri.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    public void gotoPostsPage() {
        click(By.linkText("Posts"));
    }

    public void addNewPostButtonClick() {
        click(addPostButtonLocator);
    }

    public void enterPostTitle() {
        try {
            type(postTitleInputLocator, postTitle);
        } catch (Exception e) {
            click(tipsPopupLocator);
            type(By.id("post-title-0"), postTitle);
        }
    }

    public String testContent() throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/main/resources/test-content.txt")),
                StandardCharsets.UTF_8);
    }

    public void enterTestContent() throws IOException {
//        StringSelection stringSelection = new StringSelection(testContent());
//        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//        clipboard.setContents(stringSelection, null);

//        String text = new String(Files.readAllBytes(Paths.get("src/main/resources/test-content.txt")),
//                StandardCharsets.UTF_8);
        try {
            click(textTabLocator);
//            sendKeys(textAreaLocator, Keys.CONTROL + "v");
            click(textAreaLocator);
            pasteText(textAreaLocator, testContent());
        } catch (Exception e) {
            click(blockEditorInserterLocator);
            click(blockItemParagraphLocator);
//            sendKeys(blockTextAreaLocator, Keys.CONTROL + "v");
            click(blockTextAreaLocator);
            pasteText(blockTextAreaLocator, testContent());
        }
    }

    public void publishPost() {
        try {
            scrollTillElementIsVisible(helpLinkLocator);
            jse.executeScript("document.getElementById('original_publish').setAttribute('type', 'text')");//to change attribute of element
            type(hiddenPublishInputLocator, "test");
            submit(hiddenPublishInputLocator);
            if (isAlertPresent()) {
                driver.switchTo().alert().accept();
            }
            click(publishPostButtonLocator);
            waitToBePresent(successMessageLocator);
        } catch (Exception e) {
            click(primaryPublishButtonLocator);
            try {
                click(publishButtonLargeLocator);
                waitToBePresent(publishedTextLocator);
            } catch (Exception ex) {
                click(publishButtonLargeLocator);
                waitToBePresent(publishedTextLocator);
            }
        }
    }

    public void logoutFromAdmin() {
        click(myAccountLocator);
        hoverOnElement(myAccountLocator);
        waitTillElementIsVisible(logoutItemLocator);
        click(logoutItemLocator);
    }

    public void openTestPostUrl() {
        driver.get(baseUrl + postTitle.toLowerCase().replaceAll(" ", "-"));
    }

    public boolean themeScreenshotIsBlank() {
        try {
            isElementPresent(themeScreenshotBlankLocator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean taglineHasDefaultText() {
        return getElementAttributeValue(taglineInputLocator).equals("Just another WordPress site");
    }

}