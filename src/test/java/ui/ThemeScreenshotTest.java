package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

import java.io.IOException;

public class ThemeScreenshotTest extends TestBase {

    @Test
    public void testThemeScreenshotInAdmin() throws GitLabApiException, IOException {
        String title = "Theme screenshot is missing in admin";
        app.checkIfIssueExists(title);

        app.openThemesPage();
        if (app.admin().themeScreenshotIsBlank()) {
            String themeScreenshot = "ThemeScreenshot";
            app.admin().screenshotCapture(themeScreenshot);
            String markdownThemeScreenshot = app.getGitlabFileMarkdown(themeScreenshot);
            app.uploadIssueWithDescriptionToGitlab(
                    title,
                    app.site().pageLinkForGitlab() + "\n" + markdownThemeScreenshot);
        }
    }

}
