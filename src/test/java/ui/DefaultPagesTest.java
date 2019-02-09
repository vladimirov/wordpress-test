package ui;

import appmanager.Appender;
import appmanager.TestBase;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DefaultPagesTest extends TestBase {

//    @BeforeTest
//    public void testPostCreation() throws IOException {
//        app.loginToAdmin();//Add post via database - app.addPostDb(app.admin().testContent());
//        app.openPostsPageInAdmin();
//        app.admin().addNewPostButtonClick();
//        app.admin().enterPostTitle();
//        app.admin().enterTestContent();
//        app.admin().publishPost();
//        app.admin().logoutFromAdmin();
//    }

    @Test
    public void testDefaultPages() throws Exception {
        //Search Page
        app.openSearchPageUrl();
        app.site().getPageReady();
        String searchPageScreenshot = "SearchPageTest";
        app.site().screenshotCapture(searchPageScreenshot);
        String checkboxSearchLink = "* [ ] Search Page " + app.site().pageLinkForGitlab();
        String markdownSearchPage = app.getGitlabFileMarkdown(searchPageScreenshot);
        //404 Page
        app.openPageNotFoundUrl();
        app.site().getPageReady();
        String pageNotFoundScreenshot = "404PageTest";
        app.site().screenshotCapture(pageNotFoundScreenshot);
        String markdown404 = app.getGitlabFileMarkdown(pageNotFoundScreenshot);
        String checkbox404Link = "* [ ] 404 Page " + app.site().pageLinkForGitlab();
        //Test Post Page
//        app.admin().openTestPostUrl();
//        String testPostScreenshot = "TestPost";
//        app.site().screenshotCaptureAllScreen(testPostScreenshot);
//        String checkboxTestPostLink = "* [ ] Test Post " + app.site().pageLinkForGitlab();
//        String markdownTestPost = app.getGitlabFileMarkdown(testPostScreenshot);

//        app.uploadIssueWithDescriptionToGitlab(
//                "Default pages layout screenshots in " + app.browserName() + " browser",
//                "Browser: " + app.browserName() + "\n" + "Version: " + app.browserVersion() + "\n" +
//                        checkboxSearchLink + checkbox404Link + checkboxTestPostLink + markdownSearchPage + "\n" + markdown404 + "\n" + markdownTestPost,
//                "Questions");
        //Without Test Post Test
        app.uploadIssueWithDescriptionToGitlab(
                "Default pages layout screenshots in " + app.browserName() + " browser",
                "Browser: " + app.browserName() + "\n" + "Version: " + app.browserVersion() + "\n" +
                        checkboxSearchLink + checkbox404Link + markdownSearchPage + "\n" + markdown404 + "\n");

    }

}
