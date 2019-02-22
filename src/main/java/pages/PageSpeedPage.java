package pages;

import appmanager.HelperBase;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.pagespeedonline.Pagespeedonline;
import com.google.api.services.pagespeedonline.model.PagespeedApiPagespeedResponseV5;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class PageSpeedPage extends HelperBase {

    public PageSpeedPage(WebDriver driver) {
        super(driver);
    }

    private HttpRequestInitializer setHttpTimeout(final HttpRequestInitializer requestInitializer) {
        return httpRequest -> {
            requestInitializer.initialize(httpRequest);
            httpRequest.setConnectTimeout(3 * 60000);  // 3 minutes connect timeout
            httpRequest.setReadTimeout(3 * 60000);  // 3 minutes read timeout
        };
    }

    public PagespeedApiPagespeedResponseV5 result(String url) throws GeneralSecurityException, IOException {
        Logger logger = LoggerFactory.getLogger(HelperBase.class);
        try {
            JsonFactory jsonFactory = new JacksonFactory();
            HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
            Pagespeedonline p = new Pagespeedonline.Builder(transport, jsonFactory, setHttpTimeout(httpRequest -> {
            })).build();
            Pagespeedonline.Pagespeedapi.Runpagespeed runpagespeed = p.pagespeedapi().runpagespeed(url);
            return runpagespeed.execute();
        } catch (com.google.api.client.googleapis.json.GoogleJsonResponseException e) {
            logger.info("Lighthouse returned error: ERRORED_DOCUMENT_REQUEST. " +
                    "Lighthouse was unable to reliably load the page you requested. " +
                    "Make sure you are testing the correct URL and that the server is properly responding to all requests.");
            throw new SkipException("");
        }

    }

}
