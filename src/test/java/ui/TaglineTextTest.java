package ui;

import appmanager.Appender;
import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TaglineTextTest extends TestBase {

    @BeforeTest
    public void loginToAdmin(){
        app.loginToAdmin();
    }

    @Test
    public void testTaglineTextInAdmin() throws GitLabApiException {
        app.openSettingsPage();
        if (app.admin().taglineHasDefaultText()) {
            String taglineScreenshot = "TaglineScreenshot";
            app.admin().screenshotCapture(taglineScreenshot);
            String markdownTaglineScreenshot = app.getGitlabFileMarkdown(taglineScreenshot);
            app.uploadIssueWithDescriptionToGitlab(
                    "Tagline has default text in admin",
                    app.site().pageLinkForGitlab() + "\n" + markdownTaglineScreenshot);
        }
    }

}
