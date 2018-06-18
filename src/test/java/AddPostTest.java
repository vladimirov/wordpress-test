import org.testng.annotations.Test;

public class AddPostTest extends TestBase {

    @Test
    public void createPostTest() {
        app.openCrmUrl();
        app.goTo().copyTestContent();
        app.openAdminUrl();
        app.goTo().gotoPostsPage();
        app.goTo().addNewPostButtonClick();


    }

}
