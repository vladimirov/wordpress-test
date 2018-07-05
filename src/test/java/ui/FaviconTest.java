package ui;

import appmanager.TestBase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;


public class FaviconTest extends TestBase {

    @Test
    public void testFavIcon1() {
//        app.openBaseUrl();
        app.openTest();
        assertTrue(app.faviconPage().faviconIsOnSite());
    }

}
