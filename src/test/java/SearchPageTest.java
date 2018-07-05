import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class SearchPageTest extends TestBase {

    @Test
    public void testSearchResults() {
        app.openSearchPageUrl();
        app.site().screenShot("TestSearchPage");
    }

    @Test
    public void testSearchResultsPagination() {
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
        app.admin().screenShot("TestPostSearchPagePagination");
    }

}
