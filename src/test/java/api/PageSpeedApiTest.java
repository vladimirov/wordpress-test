package api;

import appmanager.TestBase;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.pagespeedonline.Pagespeedonline;
import com.google.api.services.pagespeedonline.Pagespeedonline.Pagespeedapi;
import com.google.api.services.pagespeedonline.model.PagespeedApiPagespeedResponseV5;
import org.testng.annotations.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class PageSpeedApiTest extends TestBase {

    @Test
    public void testPageSpeedViaApi() throws IOException, GeneralSecurityException {
        String url = "https://wp-dev.space/no_client/mazur/develop/";

        JsonFactory jsonFactory = new com.google.api.client.json.jackson2.JacksonFactory();
        HttpTransport transport = com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport();

        HttpRequestInitializer httpRequestInitializer = null; //this can be null here!
        Pagespeedonline p = new Pagespeedonline.Builder(transport, jsonFactory, httpRequestInitializer).build();

        Pagespeedapi.Runpagespeed runpagespeed = p.pagespeedapi().runpagespeed(url);
        PagespeedApiPagespeedResponseV5 result = runpagespeed.execute();

//        System.out.println(result.getLighthouseResult().toPrettyString());


    }

}
