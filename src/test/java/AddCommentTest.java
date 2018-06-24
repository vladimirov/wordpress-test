import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class AddCommentTest extends TestBase{

    @Test
    public void testCommentCreation(){
        app.openBaseUrl();
        app.site().searchTestPost();
        app.site().openTestPostPageOnSite();
        app.site().postComment();
        app.loginToAdmin();
        app.admin().gotoCommentsPage();
        app.admin().approveComment();
        app.admin().openPostWithComment();
        app.site().scrollToComment();

        assertTrue(app.site().commentTextIsDisplayed());
    }

}
