import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class AddPostTest extends TestBase {

    @Test
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
        app.site().openTestPostPageOnSite();
        app.admin().screenShot();

        assertTrue(app.admin().postTitleTextIsDisplayed());
    }

//    @Test
//    public void testCommentCreation(){
//        app.loginToAdmin();
//        app.admin().gotoPostsPage();
//        app.site().searchTestPostInAdmin();
//        app.admin().clickOnTestPostPermalink();
//    }

    @Test(enabled = false)
    public void testPostDeletion() {
        app.loginToAdmin();//TODO Do not login
        app.admin().gotoPostsPage();
        app.admin().searchTestPostInAdmin();
        app.admin().deleteTestPost();
        assertTrue(app.admin().movedToTrashMessageIsDisplayed());
    }

}
