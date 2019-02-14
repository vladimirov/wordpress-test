package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

public class TaglineTextTest extends TestBase {

    @Test
    public void testTaglineTextInAdmin() throws GitLabApiException {
        String title = "Tagline has default text in admin";
        app.checkIfIssueExists(title);
        app.loginToAdmin();
        app.openSettingsPage();
        if (app.admin().taglineHasDefaultText()) {
            String taglineScreenshot = "TaglineScreenshot";
            app.admin().screenshotCapture(taglineScreenshot);
            String markdownTaglineScreenshot = app.getGitlabFileMarkdown(taglineScreenshot);
            app.uploadIssueWithDescriptionToGitlab(
                    title,
                    app.site().pageLinkForGitlab() + "\n" + markdownTaglineScreenshot);
        }
    }

}
