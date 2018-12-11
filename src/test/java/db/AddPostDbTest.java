package db;

import appmanager.TestBase;
import model.PostData;
import model.Posts;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddPostDbTest extends TestBase {

    @Test
    public void testPostCreation() {
        Posts before = app.db().posts();
        System.out.println("Number of posts is: " + before.size());
//        PostData post = new PostData().withId(Integer.MAX_VALUE).withTitle("Test DB Creation");


        //        Posts after = app.db().posts();
//        assertThat(after.size(), equalTo(before.size() + 2));
//        assertThat(after, equalTo(before.withAdded(post)));


    }
}
