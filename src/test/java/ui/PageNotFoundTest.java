package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

public class PageNotFoundTest extends TestBase {

    @Test
    public void testNotFoundPage() throws GitLabApiException {
        String screenshotName = "404PageTest";
        app.openPageNotFoundUrl();
        app.site().screenshotCapture(screenshotName);
        app.uploadIssueWithScreenshotToGitlab("404 page layout screenshot", screenshotName);
    }

}
