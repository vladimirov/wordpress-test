package ui;

import appmanager.Appender;
import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ThemeScreenshotTest extends TestBase {

    String themeScreenshot = "ThemeScreenshot";


//    @BeforeTest
//    public void loginToAdmin(){
//        app.loginToAdmin();
//    }

    @Test
    public void testThemeScreenshotInAdmin() throws GitLabApiException {
        app.openThemesPage();
        if (app.admin().themeScreenshotIsBlank()) {
            app.admin().screenshotCapture(themeScreenshot);
            String markdownThemeScreenshot = app.getGitlabFileMarkdown(themeScreenshot);
            app.uploadIssueWithDescriptionToGitlab(
                    "Theme screenshot is missing in admin",
                    app.site().pageLinkForGitlab() + "\n" + markdownThemeScreenshot);
        }
    }

//    @AfterTest
//    public void deleteScreenshot() throws IOException {
//        Files.delete(Paths.get("test-screenshots/" + themeScreenshot + "-" + Appender.id + ".png"));
//        System.out.println("SCREENSHOT DELETED");
//    }
}
