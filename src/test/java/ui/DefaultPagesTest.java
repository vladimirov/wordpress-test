package ui;

import appmanager.TestBase;
import org.testng.annotations.*;

import java.io.IOException;

public class DefaultPagesTest extends TestBase {

    @Parameters("run")
    @BeforeTest
    public void testPostCreation() throws IOException {
        app.loginToAdmin();//Add post via database - app.addPostDb(app.admin().testContent());
        app.openPostsPageInAdmin();
        app.admin().addNewPostButtonClick();
        app.admin().enterPostTitle();
        app.admin().enterTestContent();
        app.admin().publishPost();
        app.admin().logoutFromAdmin();
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
        app.admin().openTestPostUrl();
        app.site().screenshotCaptureAllScreen(testPostScreenshot);
        String checkboxTestPostLink = "* [ ] Test Post " + app.site().pageLinkForGitlab();
        String markdownTestPost = app.getGitlabFileMarkdown(testPostScreenshot);

        app.uploadIssueWithDescriptionToGitlab(
                "Default pages layout screenshots in " + app.browserName() + " browser",
                "Browser: " + app.browserName() + "\n" + "Version: " + app.browserVersion() + "\n" +
                        checkboxSearchLink + checkbox404Link + checkboxTestPostLink + markdownSearchPage + "\n" + markdown404 + "\n" + markdownTestPost,
                "Question");
    }
}
