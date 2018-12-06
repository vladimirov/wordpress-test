package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class DefaultPagesTest extends TestBase {

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
        app.loginToAdmin();
        app.admin().gotoPostsPage();
        app.admin().addNewPostButtonClick();
        app.admin().enterPostTitle();
        app.admin().enterTestContent();
        app.admin().publishPost();
        app.admin().gotoPostsPage();
        app.admin().searchTestPostInAdmin();
        app.admin().clickOnTestPostPermalink();
        app.admin().openTestPostPageOnSite();
        app.site().screenshotCaptureAllScreen(testPostScreenshot);
        assertTrue(app.admin().postTitleTextIsDisplayed());
        String checkboxTestPost = "* [ ] Test Post " + app.site().pageLinkForGitlab();
        String markdownTestPost = app.getGitlabFileMarkdown(testPostScreenshot);

        app.uploadIssueWithDescriptionToGitlab(
                "Default pages layout screenshots",
                checkboxSearchLink + checkbox404Link + checkboxTestPost + markdownSearchPage + "\n" + markdown404 + "\n" + markdownTestPost);


    }
}
