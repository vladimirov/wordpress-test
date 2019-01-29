package api;

import appmanager.TestBase;
import org.testng.annotations.Test;

public class AddPostTest extends TestBase {

    @Test(enabled = true)
    public void testPostCreation() throws Exception {
        String testPostScreenshot = "TestPost";
        app.loginToAdmin();
        app.admin().gotoPostsPage();
        app.admin().addNewPostButtonClick();
        app.admin().enterPostTitle();
        app.admin().enterTestContent();
        app.admin().publishPost();
        app.admin().logoutFromAdmin();
        app.admin().openTestPostUrl();
        app.site().screenshotCaptureAllScreen(testPostScreenshot);
        String markdownTestPost = app.getGitlabFileMarkdown(testPostScreenshot);
        app.uploadIssueWithDescriptionToGitlab(
                "Test post screenshot",
                markdownTestPost);
    }

}
