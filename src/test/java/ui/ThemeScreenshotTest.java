package ui;

import appmanager.TestBase;
import org.apache.commons.lang3.StringUtils;
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
                    "**Browser**: " + app.browserName() + "\n" + app.browserVersion() + "\n\n" +
                            "**OS**: " + StringUtils.capitalize(app.OS) + "\n\n" +
                            "**Screen size**: " + app.site().screenSize() + "\n\n" +
                            "**Link**: " + app.site().pageLinkForGitlab() + "\n\n" + markdownThemeScreenshot);
        }
    }

}
