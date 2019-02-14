package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;


public class FaviconTest extends TestBase {

    @Test
    public void testFavicon() throws IOException, GitLabApiException {
        String title = "Favicon is missing";
        app.checkIfIssueExists(title);
        try {
            app.openBaseUrl();
            assertTrue(app.faviconPage().isFaviconPresent());
            assertTrue(app.faviconPage().verifyFaviconLink());
        } catch (AssertionError e) {
            app.uploadIssueWithDescriptionToGitlab(title, "Check site favicon");
        }
    }

}
