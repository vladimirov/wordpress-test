package ui;

import appmanager.TestBase;
import org.testng.annotations.Test;

import java.io.IOException;


public class PageSpeedTest extends TestBase {

    @Test
    public void testPageSpeed() throws IOException {
        app.openPageSpeedUrl();
        app.pageSpeed().enterPageUrlToPageSpeed();
        app.pageSpeed().analyzeButtonClick();
        app.pageSpeed().waitTillAnalyzing();
        if (app.pageSpeed().speedReportCardIsDisplayed() || app.pageSpeed().errorBarIsDisplayed()) {
            app.pageSpeed().screenShot("TestPageSpeed");
        }
    }

}
