import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;


public class AddPostTest extends TestBase {

    @Test
    public void testPostCreation() throws InterruptedException {
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

//    @Test
//    public void testCommentCreation(){
//        app.loginToAdmin();
//        app.admin().gotoPostsPage();
//        app.site().searchTestPostInAdmin();
//        app.admin().clickOnTestPostPermalink();
//    }

    @Test(enabled = false)
    public void testPostDeletion() throws InterruptedException {
        app.loginToAdmin();//TODO Do not login
        app.admin().gotoPostsPage();
        app.admin().searchTestPostInAdmin();
        app.admin().deleteTestPost();
        assertTrue(app.admin().movedToTrashMessageIsDisplayed());
    }

}
