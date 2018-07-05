package ui;

import appmanager.TestBase;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import javax.lang.model.element.Element;
import java.io.IOException;

import static org.testng.Assert.assertTrue;


public class FaviconTest extends TestBase {

    @Test
    public void testFavIcon1() throws IOException {
        app.openTest();
        System.out.println(app.faviconPage().elementsWithFavicons().size());

        for (Elements e : app.faviconPage().elementsWithFavicons())  {
            System.out.println(e);
        }

        

    }

}
