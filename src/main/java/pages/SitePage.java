package pages;

import appmanager.Appender;
import appmanager.HelperBase;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SitePage extends HelperBase {

    private Properties projectProperties = new Properties();

    public SitePage(WebDriver driver) throws IOException {
        super(driver);
        projectProperties.load(new FileReader(new File(Appender.path)));
    }

    public void getPageReady() {
        waitForPageLoadComplete(driver, 10);
    }

    public boolean sitemapPageIsAdded() throws IOException {
        String url = projectProperties.getProperty("web.baseUrl");
        try {
            return responseCode(url) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean responseCodeIsValid() throws IOException {
        String url = projectProperties.getProperty("web.baseUrl");
//        return responseCode(url) < 400 && responseCode(url).equals(403) && responseCode(url).equals(999);
        return responseCode(url) == 200;
    }

}

