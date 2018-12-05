package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

public class DefaultPagesTest extends TestBase {

    @Test
    public void testDefaultPages() throws GitLabApiException {
        String searchPageScreenshot = "SearchPagePaginationTest";
        String pageNotFoundScreenshot = "404PageTest";
        app.openSearchPageUrl();
        app.site().screenshotCapture(searchPageScreenshot);
        String markdownSearchPage = app.getGitlabFileMarkdown(searchPageScreenshot);
        String checkboxSearchLink = "* [ ] Search Page " + app.site().pageLinkForGitlab();
        app.openPageNotFoundUrl();
        app.site().screenshotCapture(pageNotFoundScreenshot);
        String markdown404 = app.getGitlabFileMarkdown(pageNotFoundScreenshot);
        String checkbox404Link = "* [ ] 404 Page " + app.site().pageLinkForGitlab();
        app.uploadIssueWithDescriptionToGitlab(
                "Default pages layout screenshots",
                checkboxSearchLink + checkbox404Link + markdownSearchPage + "\n" + markdown404);
    }
}
