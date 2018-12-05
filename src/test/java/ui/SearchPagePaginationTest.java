package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class SearchPagePaginationTest extends TestBase {

    @Test(enabled = false)
    public void testSearchResultsPagination() throws IOException {
        app.loginToCRM();
        app.admin().copyTestContent();
        app.loginToAdmin();
        for (int i = 0; i < 10; i++) {
            app.admin().gotoPostsPage();
            app.admin().addNewPostButtonClick();
            app.admin().enterPostTitle();
            app.admin().enterTestContent();
            app.admin().publishPost();
        }
        app.openSearchPageUrl();
        app.site().scrollToPagination();
        app.admin().screenshotCapture("TestPostSearchPagePagination");
    }

}
