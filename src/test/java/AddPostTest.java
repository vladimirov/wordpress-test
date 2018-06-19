import org.testng.annotations.Test;

public class AddPostTest extends TestBase {

    @Test
    public void createPostTest() {
        app.loginToCRM();
        app.admin().copyTestContent();
        app.loginToAdmin();
        app.admin().gotoPostsPage();
        app.site().addNewPostButtonClick();
        app.site().enterPostTitle();
        app.site().enterTestContent();
        app.site().publishPost();
        app.admin().gotoPostsPage();
        app.site().openTestPostPage();
        app.site().screenShot();
    }

}
