package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

import java.io.IOException;


public class PageSpeedTest extends TestBase {

    @Test
    public void testPageSpeed() throws IOException, GitLabApiException {
        app.openPageSpeedUrl();
        app.pageSpeed().enterPageUrlToPageSpeed();
        app.pageSpeed().analyzeButtonClick();
        app.pageSpeed().waitTillAnalyzing();
        int percent = Integer.valueOf(app.pageSpeed().percentValue());
        //Mobile Tab
        if (app.pageSpeed().reportSummaryIsDisplayed() || percent < 50){
            app.pageSpeed().screenShot("PageSpeedTestMobile");
            System.out.println("Google PageSpeed Mobile need to be optimized to 50-89. Right now it's value is - " + percent);
        }
        app.uploadScreenshotToGitlab("PageSpeedTestMobile.png", "Google PageSpeed Mobile percentage value need to be more than 50");
        //Desktop tab
//        app.pageSpeed().desktopTabClick();
//        if (app.pageSpeed().reportSummaryIsDisplayed() || percent < 50){
//            app.pageSpeed().screenShot("PageSpeedTestDesktop");
//            System.out.println("Google PageSpeed Desktop need to be optimized to 50-89. Right now it's value is - " + percent);
//        }
//        app.uploadScreenshotToGitlab("PageSpeedTestDesktop.png", "Google PageSpeed Desktop percentage value need to be more than 50");

    }

}



//        if (app.pageSpeed().reportSummaryIsDisplayed() || app.pageSpeed().errorBarIsDisplayed()) {
//            app.pageSpeed().screenShot("TestPageSpeed");
//        }