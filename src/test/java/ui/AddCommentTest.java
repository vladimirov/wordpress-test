package ui;

import appmanager.TestBase;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class AddCommentTest extends TestBase {

    @Test(priority = 2)
    public void testCommentCreation(){
        app.openSearchPageUrl();
        app.site().openPostFromSearchResults();
        app.site().postComment();
        app.loginToAdmin();
        app.admin().gotoCommentsPage();
        app.admin().approveComment();
        app.admin().openPostWithComment();
        app.site().scrollToComment();

        assertTrue(app.site().commentTextIsDisplayed());
    }

}
