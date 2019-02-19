package pages;

import appmanager.Appender;
import appmanager.HelperBase;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

//    public boolean sitemapPageIsAdded() {
//        String url = projectProperties.getProperty("web.baseUrl");
//        try {
//            return responseCode(url) > 0;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public boolean responseCodeIsValid() throws IOException {
//        String url = projectProperties.getProperty("web.baseUrl");
//        return responseCode(url) == 200;
//    }

    public void getLinkHashSet() throws IOException {
        String url = projectProperties.getProperty("web.baseUrl");
        System.out.println("Size of collection is: " + hrefHashSet(url).size());

    }

    public void getLinks() throws IOException {
        System.out.println("Searching for links on site...");
        String url = projectProperties.getProperty("web.baseUrl");
        HashSet<String> hashSet = hrefHashSet(url);
        System.out.println(hashSet.size());
        for (String s : hashSet) {
            System.out.println(s);
        }
    }

    public void getResponse() throws IOException {
        String url = projectProperties.getProperty("web.baseUrl");
        HashSet<String> hashSet = hrefHashSet(url);
        for (String s : hashSet) {
            System.out.println(responseCodeNumber(s));
        }
    }

    public List<String> brokenLinks() throws IOException {
        System.out.println("Waiting for HTTP URL Connection response...");
        List<String> list = new ArrayList<>();
        String url = projectProperties.getProperty("web.baseUrl");
        HashSet<String> hashSet = hrefHashSet(url);
        for (String s : hashSet) {
            System.out.println(s + " " + responseCodeNumber(s));
//            if (responseCodeNumber(s) != 200 && responseCodeNumber(s) != 999 && responseCodeNumber(s) != 403 && responseCodeNumber(s) != 301) {
            if (responseCodeNumber(s) > 403) {
                list.add(s);
            }
        }
        System.out.println("Total broken links count: " + list.size());
        return list;
    }

}

