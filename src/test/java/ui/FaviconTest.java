package ui;

import appmanager.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.testng.Assert.*;


public class FaviconTest extends TestBase {

    @Test(enabled = true)
    public void testFavicon() throws IOException, URISyntaxException {
//        app.openBaseUrl();
        app.openTestUrl();

        assertTrue(app.faviconPage().isFaviconPresent());
        assertTrue(app.faviconPage().verifyFaviconLink());
    }

}
