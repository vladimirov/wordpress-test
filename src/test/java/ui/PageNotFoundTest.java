package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

public class PageNotFoundTest extends TestBase {

    @Test
    public void testNotFoundPage() throws GitLabApiException {
        app.openPageNotFoundUrl();
        String screenshotName = "404PageTest";
        app.site().screenshotCapture(screenshotName);
        app.uploadIssueWithScreenshotToGitlab("404 page layout screenshot", screenshotName);
    }

}
