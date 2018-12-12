package ui;

import appmanager.TestBase;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class DefaultPagesTest extends TestBase {

    @BeforeTest
    public void addTestPostDb() throws IOException {
        app.addPostDb(app.admin().testContent());
    }

    @Test
    public void testDefaultPages() throws Exception {
        String searchPageScreenshot = "SearchPageTest";
        String pageNotFoundScreenshot = "404PageTest";
        String testPostScreenshot = "TestPost";
        //Search Page
        app.openSearchPageUrl();
        app.site().screenshotCapture(searchPageScreenshot);
        String checkboxSearchLink = "* [ ] Search Page " + app.site().pageLinkForGitlab();
        String markdownSearchPage = app.getGitlabFileMarkdown(searchPageScreenshot);
        //404 Page
        app.openPageNotFoundUrl();
        app.site().screenshotCapture(pageNotFoundScreenshot);
        String markdown404 = app.getGitlabFileMarkdown(pageNotFoundScreenshot);
        String checkbox404Link = "* [ ] 404 Page " + app.site().pageLinkForGitlab();
        //Test Post Page
        app.openTestPostUrl();
        app.site().screenshotCaptureAllScreen(testPostScreenshot);
        String checkboxTestPostLink = "* [ ] Test Post " + app.site().pageLinkForGitlab();
        String markdownTestPost = app.getGitlabFileMarkdown(testPostScreenshot);

        app.uploadIssueWithDescriptionToGitlab(
                "Default pages layout screenshots",
                checkboxSearchLink + checkbox404Link + checkboxTestPostLink + markdownSearchPage + "\n" + markdown404 + "\n" + markdownTestPost,
                "Question");

//        app.uploadIssueWithDescriptionToGitlab(
//                "Default pages layout screenshots",
//                checkboxTestPostLink + "\n" + markdownTestPost,
//                "Question");
    }
}
