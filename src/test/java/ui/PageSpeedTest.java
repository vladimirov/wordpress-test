package ui;

import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PageSpeedTest extends TestBase {

    @Test
    public void testPageSpeed() throws IOException, GitLabApiException {
        app.openPageSpeedUrl();
        app.pageSpeed().enterPageUrlToPageSpeed();
        app.pageSpeed().analyzeButtonClick();
        app.pageSpeed().waitTillAnalyzing();
//        app.pageSpeed().desktopTabClick();
        //Mobile Tab
//        int percentMobile = Integer.valueOf(app.pageSpeed().mobilePercent());
//        if (percentMobile < 50){
//            System.out.println("Google PageSpeed Mobile need to be optimized to 50-89. Right now it's value is - " + percentMobile);
//            app.pageSpeed().screenShot("PageSpeedTestMobile");
//            app.uploadScreenshotToGitlab("PageSpeedTestMobile.png", "Google PageSpeed Mobile percentage value need to be more than 50");
//        }
        //Desktop tab
        app.pageSpeed().desktopTabClick();
        int percentDesktop = Integer.valueOf(app.pageSpeed().desktopPercent());
        System.out.println(percentDesktop);
        if (percentDesktop < 50) {
            System.out.println("Google PageSpeed Desktop need to be optimized to more than 50. Right now it's value is - " + percentDesktop);
            app.pageSpeed().screenShot("PageSpeedTestDesktop");
            app.uploadScreenshotToGitlab("Google PageSpeed Desktop percentage value need to be more than 50", "PageSpeedTestDesktop.png");
        }


    }
}