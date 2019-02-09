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

//    @BeforeTest
//    public void loginToAdmin(){
//        app.loginToAdmin();
//    }

    @Test
    public void testThemeScreenshotInAdmin() throws GitLabApiException, IOException {
        app.openThemesPage();
        if (app.admin().themeScreenshotIsBlank()) {
            String themeScreenshot = "ThemeScreenshot";
            app.admin().screenshotCapture(themeScreenshot);
            String markdownThemeScreenshot = app.getGitlabFileMarkdown(themeScreenshot);
            app.uploadIssueWithDescriptionToGitlab(
                    "Theme screenshot is missing in admin",
                    app.site().pageLinkForGitlab() + "\n" + markdownThemeScreenshot);

            Files.delete(Paths.get("test-screenshots/" + themeScreenshot + "-" + Appender.id + ".png"));
            System.out.println("SCREENSHOT DELETED");
        }
    }

}
