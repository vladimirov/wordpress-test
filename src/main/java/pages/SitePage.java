package pages;

import appmanager.Appender;
import appmanager.HelperBase;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileNotFoundException;
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
        try {
            return responseCode(projectProperties.getProperty("web.baseUrl")) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean responseCodeIs200() throws IOException {
        return responseCode(projectProperties.getProperty("web.baseUrl")).equals(200);
    }

}

