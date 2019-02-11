package pages;

import appmanager.Appender;
import appmanager.HelperBase;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SitePage extends HelperBase {

    public SitePage(WebDriver driver) {
        super(driver);
    }


    public void getPageReady() {
        waitForPageLoadComplete(driver, 10);
    }


    public boolean responseCodeIs200() throws IOException {
        Properties projectProperties = new Properties();
        projectProperties.load(new FileReader(new File(Appender.path)));
        return responseCode(projectProperties.getProperty("web.baseUrl")).equals(200);
    }

}

