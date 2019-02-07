package ui;

import appmanager.Appender;
import appmanager.ApplicationManager;
import appmanager.HelperBase;
import appmanager.TestBase;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.pagespeedonline.Pagespeedonline;
import com.google.api.services.pagespeedonline.Pagespeedonline.Pagespeedapi;
import com.google.api.services.pagespeedonline.model.PagespeedApiPagespeedResponseV5;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.gitlab4j.api.GitLabApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class PageSpeedApiTest extends TestBase {

    private Properties properties;

    private HttpRequestInitializer setHttpTimeout(final HttpRequestInitializer requestInitializer) {
        return httpRequest -> {
            requestInitializer.initialize(httpRequest);
            httpRequest.setConnectTimeout(3 * 60000);  // 3 minutes connect timeout
            httpRequest.setReadTimeout(3 * 60000);  // 3 minutes read timeout
        };
    }

    @BeforeTest
    public void getProperties() throws IOException {
        properties = new Properties();
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format(Appender.path, target))));
    }

    @Test
    public void testPageSpeedViaApi() throws Throwable {
        Logger logger = LoggerFactory.getLogger(HelperBase.class);
        String description = "PageSpeed Desktop percentage value need to be more than 50";
        String url = properties.getProperty("web.baseUrl");
        String domain = url.split("/")[2];
        String pageSpeedLink = "https://developers.google.com/speed/pagespeed/insights/?url=https%3A%2F%2F" + domain + "%2F";

        JsonFactory jsonFactory = new JacksonFactory();
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();

        Pagespeedonline p = new Pagespeedonline.Builder(transport, jsonFactory, setHttpTimeout(httpRequest -> {})).build();
        Pagespeedapi.Runpagespeed runpagespeed = p.pagespeedapi().runpagespeed(url);
        PagespeedApiPagespeedResponseV5 result = runpagespeed.execute();

        String json = result.getLighthouseResult().toString();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        String categoriesJson = jsonObject.get("categories").toString();
        JsonObject categoriesJsonObject = new Gson().fromJson(categoriesJson, JsonObject.class);
        String performanceJson = categoriesJsonObject.get("performance").toString();
        JsonObject performanceJsonObject = new Gson().fromJson(performanceJson, JsonObject.class);
        float scoreFloat = performanceJsonObject.get("score").getAsFloat() * 100;
        int score = Math.round(scoreFloat);

        if (score < 50) {
            logger.info("Google PageSpeed Desktop need to be optimized to more than 50. Right now it's value is " + score);
            app.uploadIssueWithDescriptionToGitlab(
                    "PageSpeed Desktop percentage value is " + score,
                    pageSpeedLink + "\n\n" + description);
        } else {
            logger.info("PageSpeed Desktop is up to date. Right now it's value is " + score);
        }

    }
}







