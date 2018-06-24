import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class AddPostTest extends TestBase {

    @Test
    public void testPostCreation() {
        app.loginToCRM();
        app.admin().copyTestContent();
        app.loginToAdmin();
        app.admin().gotoPostsPage();
        app.site().addNewPostButtonClick();
        app.site().enterPostTitle();
        app.site().enterTestContent();
        app.site().publishPost();
        app.admin().gotoPostsPage();
        app.site().searchTestPostInAdmin();
        app.site().openTestPostPage();
        app.site().screenShot();

        assertTrue(app.site().postTitleTextIsDisplayed());
    }

//    @AfterTest
//    public void testPostDeletion(){
//        app.loginToAdmin();//TODO Do not login
//        app.admin().gotoPostsPage();
//        app.site().searchTestPostInAdmin();
//        app.site().deleteTestPost();
//
//        assertTrue(app.site().movedToTrashMessageIsDisplayed());
//    }

}
