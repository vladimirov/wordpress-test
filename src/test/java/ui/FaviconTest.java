package ui;

import appmanager.TestBase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;


public class FaviconTest extends TestBase {

//    @Test
//    public void testFavicon() throws IOException, URISyntaxException, GitLabApiException {
//        try {
//            app.openBaseUrl();
//            assertTrue(app.faviconPage().isFaviconPresent());
//            assertTrue(app.faviconPage().verifyFaviconLink());
//        } catch (AssertionError e) {
//            app.faviconPage().screenshotCapture("FaviconTest");
//            app.uploadScreenshotToGitlab("Favicon is missing", "FaviconTest.png");
//        }
//    }

    @Test
    public void testFavicon() throws Exception {
        app.openBaseUrl();
        app.faviconPage().screenshotCapture("FaviconTest");
        app.faviconPage().screenshotCaptureAllScreen("FaviconTestAllScreen");
    }

}
