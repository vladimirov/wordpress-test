package ui;

import appmanager.Appender;
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

    @BeforeTest
    public void getProperties() throws IOException {
        properties = new Properties();
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format(Appender.path, target))));
    }

    @Test
    public void testPageSpeedViaApi() throws Throwable {
        Logger logger = LoggerFactory.getLogger(HelperBase.class);
        String desc = "PageSpeed Desktop percentage value need to be more than 50";
        String url = properties.getProperty("web.baseUrl");

        logger.info("JSON FACTORY INITIALIZATION");
        JsonFactory jsonFactory = new JacksonFactory();
        logger.info("HttpTransport INITIALIZATION");
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        logger.info("HttpRequestInitializer INITIALIZATION");
        HttpRequestInitializer requestInitializer = null;
        logger.info("Pagespeedonline.Builder INITIALIZATION");
        Pagespeedonline p = new Pagespeedonline.Builder(transport, jsonFactory, requestInitializer).build();
        logger.info("Pagespeedapi.Runpagespeed runpagespeed INITIALIZATION");
        Pagespeedapi.Runpagespeed runpagespeed = p.pagespeedapi().runpagespeed(url);
        logger.info("PagespeedApiPagespeedResponseV5 result INITIALIZATION");
        PagespeedApiPagespeedResponseV5 result = runpagespeed.execute();

        logger.info("GETTING LIGHTHOUSE RESULT TO STRING");
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
                    desc,
                    null);
        } else {
            logger.info("PageSpeed Desktop is up to date. Right now it's value is " + score);
        }

    }
}
