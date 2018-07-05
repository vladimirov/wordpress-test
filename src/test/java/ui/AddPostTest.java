package ui;

import appmanager.TestBase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;


public class AddPostTest extends TestBase {

    @Test(priority = 1)
    public void testPostCreation() {
        app.loginToCRM();
        app.admin().copyTestContent();
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
        app.admin().screenShot("TestPost");
        assertTrue(app.admin().postTitleTextIsDisplayed());
    }

    @Test(priority = 3, enabled = false)
    public void testPostDeletion() {
        app.loginToAdmin();//TODO Do not login
        app.admin().gotoPostsPage();
        app.admin().searchTestPostInAdmin();
        app.admin().deleteTestPost();
        assertTrue(app.admin().movedToTrashMessageIsDisplayed());
    }

}
