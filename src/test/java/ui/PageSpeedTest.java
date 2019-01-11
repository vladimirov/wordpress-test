package ui;

import appmanager.HelperBase;
import appmanager.TestBase;
import org.gitlab4j.api.GitLabApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;


public class PageSpeedTest extends TestBase {

    @Test
    public void testPageSpeed() throws IOException, GitLabApiException {
        Logger logger = LoggerFactory.getLogger(HelperBase.class);
        String desc = "PageSpeed Desktop percentage value need to be more than 50";
        String pageSpeedScreenshot = "PageSpeedTestDesktop";
        int percentDesktop = 0;
        String pageSpeedLink = app.site().pageLinkForGitlab();

        app.openPageSpeedUrl();
        app.pageSpeed().enterPageUrlToPageSpeed();
        app.pageSpeed().analyzeButtonClick();
        try {
            app.pageSpeed().percentageIsPresent();//TODO change type of waiting
            app.pageSpeed().desktopTabClick();
            percentDesktop = Integer.valueOf(app.pageSpeed().desktopPercent());
            if (percentDesktop < 50) {
                logger.info("Google PageSpeed Desktop need to be optimized to more than 50. Right now it's value is - " + percentDesktop);
                app.pageSpeed().screenshotCapture(pageSpeedScreenshot);
                String markdownPageSpeedPage = app.getGitlabFileMarkdown(pageSpeedScreenshot);
                app.uploadIssueWithDescriptionToGitlab(
                        "PageSpeed Desktop percentage value is " + percentDesktop,
                        pageSpeedLink + "\n" + desc + "\n" + markdownPageSpeedPage,
                        null);
            } else {
                logger.info("PageSpeed Desktop is up to date. Right now it's value is - " + percentDesktop);
            }
        } catch (Exception e){
            app.pageSpeed().screenshotCapture(pageSpeedScreenshot);
            String markdownPageSpeedPage = app.getGitlabFileMarkdown(pageSpeedScreenshot);
            app.uploadIssueWithDescriptionToGitlab(
                    "PageSpeed Desktop percentage value is not displayed",
                    markdownPageSpeedPage,
                    null);
        }
    }

}

//*/2 * * * *