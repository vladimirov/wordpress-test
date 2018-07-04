import org.testng.annotations.Test;

public class SearchPageTest extends TestBase {

    @Test
    public void testSearchResults() {
        app.openSearchPageUrl();
        app.site().screenShot("TestSearchPage");
    }

}
