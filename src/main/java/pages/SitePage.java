package pages;

import appmanager.Appender;
import appmanager.HelperBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Properties;

public class SitePage extends HelperBase {

    private final Properties properties;

    public SitePage(WebDriver driver) {
        super(driver);
        properties = new Properties();
    }

    public void getPageReady() {
        waitForPageLoadComplete(driver, 10);
    }

    public void verifyAllLinksOnSite() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format(Appender.path, target))));
        verifyAllLinks(properties.getProperty("web.baseUrl"));
    }

    public String getResponseCode() throws IOException {
        return responseCode(properties.getProperty("web.baseUrl"));
    }

    public boolean responseCodeIs200() throws IOException {
        return responseCode(properties.getProperty("web.baseUrl")).equalsIgnoreCase("200");
    }

}

