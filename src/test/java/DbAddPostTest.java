import model.PostData;
import model.Posts;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class DbAddPostTest extends TestBase {

    @Test
    public void testPostCreation() throws InterruptedException {
        Posts before = app.db().posts();
        app.loginToCRM();
        app.admin().copyTestContent();
        app.loginToAdmin();
        app.admin().gotoPostsPage();
        app.admin().addNewPostButtonClick();
        app.admin().enterPostTitle();
        app.admin().enterTestContent();
        app.admin().publishPost();


        PostData post = new PostData().withId(Integer.MAX_VALUE).withTitle("Test DB Creation");

        Posts after = app.db().posts();
//        assertThat(after.size(), equalTo(before.size() + 1));

        assertThat(after, equalTo(before.withAdded(post).withAdded(post)));


    }


}
