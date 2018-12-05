package ui;

import appmanager.TestBase;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.pagespeedonline.Pagespeedonline;
import com.google.api.services.pagespeedonline.model.PagespeedApiFormatStringV4;
import com.google.api.services.pagespeedonline.model.PagespeedApiImageV4;
import com.google.api.services.pagespeedonline.model.PagespeedApiPagespeedResponseV4;
import org.gitlab4j.api.GitLabApiException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class PageSpeedTest extends TestBase {

    @Test(enabled = false)
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
//            app.pageSpeed().screenshotCapture("PageSpeedTestMobile");
//            app.uploadIssueWithScreenshotToGitlab("PageSpeedTestMobile.png", "Google PageSpeed Mobile percentage value need to be more than 50");
//        }
        //Desktop tab
        app.pageSpeed().desktopTabClick();
        int percentDesktop = Integer.valueOf(app.pageSpeed().desktopPercent());
        System.out.println(percentDesktop);
        if (percentDesktop < 50) {
            System.out.println("Google PageSpeed Desktop need to be optimized to more than 50. Right now it's value is - " + percentDesktop);
            app.pageSpeed().screenshotCapture("PageSpeedTestDesktop");
            app.uploadIssueWithScreenshotToGitlab("Google PageSpeed Desktop percentage value need to be more than 50", "PageSpeedTestDesktop.png");
        }
    }

    @Test
    public void pagespeedApiTest() throws IOException, GeneralSecurityException {
        String url = "http://www.ogilvy.co.za/";
        JsonFactory jsonFactory = new  JacksonFactory();
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        HttpRequestInitializer httpRequestInitializer = null; //this can be null here!
        Pagespeedonline p = new Pagespeedonline.Builder(transport, jsonFactory, httpRequestInitializer).build();
        Pagespeedonline.Pagespeedapi.Runpagespeed runpagespeed  = p.pagespeedapi().runpagespeed(url);
        PagespeedApiPagespeedResponseV4 result = runpagespeed.execute();
        PagespeedApiPagespeedResponseV4.FormattedResults formattedResults = result.getFormattedResults();


        System.out.println(formattedResults.toPrettyString());
    }

}