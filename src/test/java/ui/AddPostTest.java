package ui;

import appmanager.TestBase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class AddPostTest extends TestBase {

    @Test
    public void testPostCreation() throws Exception {
        String testPostScreenshot = "TestPost";

        app.loginToAdmin();
        app.admin().gotoPostsPage();
        app.admin().addNewPostButtonClick();

        //Close tips popup in wordpress5
//        app.admin().closeTipsPopUp();

        app.admin().enterPostTitle();
        app.admin().enterTestContent();
        app.admin().publishPost();
        app.admin().gotoPostsPage();
        app.admin().searchTestPostInAdmin();
        app.admin().clickOnTestPostPermalink();
        app.admin().openTestPostPageOnSite();
        assertTrue(app.admin().postTitleTextIsDisplayed());

        app.site().screenshotCaptureAllScreen(testPostScreenshot);
        String markdownTestPost = app.getGitlabFileMarkdown(testPostScreenshot);

        app.uploadIssueWithDescriptionToGitlab(
                "Test post screenshot",
                markdownTestPost,
                "Question");

    }

}
