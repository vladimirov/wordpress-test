package ui;

import appmanager.Appender;
import appmanager.HelperBase;
import appmanager.TestBase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        String title = "PageSpeed Desktop percentage value need to be optimized";
        app.checkIfIssueExists(title);
        Logger logger = LoggerFactory.getLogger(HelperBase.class);
        String url = properties.getProperty("web.baseUrl");
        String domain = url.split("/")[2];
        String pageSpeedLink = "https://developers.google.com/speed/pagespeed/insights/?url=https%3A%2F%2F" + domain + "%2F";

        String json = app.pageSpeedPage().result(url).getLighthouseResult().toString();//Skip if Lighthouse returned error

        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        String categoriesJson = jsonObject.get("categories").toString();
        JsonObject categoriesJsonObject = new Gson().fromJson(categoriesJson, JsonObject.class);
        String performanceJson = categoriesJsonObject.get("performance").toString();
        JsonObject performanceJsonObject = new Gson().fromJson(performanceJson, JsonObject.class);

        float scoreFloat = performanceJsonObject.get("score").getAsFloat() * 100;
        int score = Math.round(scoreFloat);

        if (score < 50) {
            logger.info("PageSpeed Desktop value need to be optimized to more than 50. Right now it's value is " + score);
            app.uploadIssueWithDescriptionToGitlab(
                    title,
                    "**Link**: " + pageSpeedLink + "\n\n" + "PageSpeed Desktop value need to be optimized to more than 50. Right now it's value is " + score);
        } else {
            logger.info("PageSpeed Desktop is up to date. Right now it's value is " + score);
        }

    }
}







