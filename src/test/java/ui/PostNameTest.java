package ui;

import appmanager.TestBase;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

public class PostNameTest extends TestBase {

    @Test
    public void permalinksHasPostHameIsSelected() throws GitLabApiException {
        String title = "Site isn't displays with correct permalink name";
        app.checkIfIssueExists(title);
        app.loginToAdmin();
        app.openPermalinksSettings();

        if (!app.admin().postNameRadioActive()) {
            String permalinkScreenshot = "PermalinkScreenshot";
            app.admin().screenshotCapture(permalinkScreenshot);
            String markdownTaglineScreenshot = app.getGitlabFileMarkdown(permalinkScreenshot);
            app.uploadIssueWithDescriptionToGitlab(
                    title,
                    "**Browser**: " + app.browserName() + "\n" + app.browserVersion() + "\n\n" + "**OS**: " + StringUtils.capitalize(app.OS) + "\n\n" +
                            "**Link**: " + app.site().pageLinkForGitlab() + "\n" + "**Comment**: " +
                            "'Post name' permalink structure should be active by default if another doesn't mentioned in project specifications" +"\n\n" +
                            markdownTaglineScreenshot);
        }

    }
}
