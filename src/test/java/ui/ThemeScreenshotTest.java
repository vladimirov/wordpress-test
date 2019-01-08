package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

public class ThemeScreenshotTest extends TestBase {

    @Test
    public void testPageSpeed() throws GitLabApiException {
        app.loginToAdmin();
        app.openThemesPage();
        if (app.admin().themeScreenshotIsBlank()) {
            String themeScreenshot = "ThemeScreenshot";
            app.admin().screenshotCapture(themeScreenshot);
            String markdownThemeScreenshot = app.getGitlabFileMarkdown(themeScreenshot);
            app.uploadIssueWithDescriptionToGitlab(
                    "Theme screenshot is missing in admin",
                    app.site().pageLinkForGitlab() + "\n" + markdownThemeScreenshot,
                    null);
        }
    }
}
