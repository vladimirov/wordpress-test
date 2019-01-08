package api;

import appmanager.TestBase;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.pagespeedonline.Pagespeedonline;
import com.google.api.services.pagespeedonline.Pagespeedonline.Pagespeedapi;
import com.google.api.services.pagespeedonline.model.PagespeedApiPagespeedResponseV4;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.google.api.services.pagespeedonline.model.PagespeedApiPagespeedResponseV4.FormattedResults;

public class PageSpeedApiTest extends TestBase {

    @Test
    public void testPageSpeedViaApi() throws IOException, GeneralSecurityException {
        String url = "http://weather.com";

        JsonFactory jsonFactory = new com.google.api.client.json.jackson2.JacksonFactory();
        HttpTransport transport = com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport();

        HttpRequestInitializer httpRequestInitializer = null; //this can be null here!
        Pagespeedonline p = new Pagespeedonline.Builder(transport, jsonFactory, httpRequestInitializer).build();

        Pagespeedapi.Runpagespeed runpagespeed = p.pagespeedapi().runpagespeed(url);
        PagespeedApiPagespeedResponseV4 result = runpagespeed.execute();

//        System.out.println(result.getPageStats().toPrettyString());
//        System.out.println(result.getCaptchaResult());
//        System.out.println(result.getResponseCode());
//        System.out.println(result.getKind());
//        System.out.println(result.getLoadingExperience().toPrettyString());
//        System.out.println(result.getLoadingExperience().getMetrics());




    }

}
