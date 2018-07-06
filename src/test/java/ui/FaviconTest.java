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
        app.openTestUrl();

        assertTrue(app.faviconPage().isFaviconPresent());
        assertEquals(app.faviconPage().croppedFaviconText(), "cropped-favicon-32x32.png");

    }

}
