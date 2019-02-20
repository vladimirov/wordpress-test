package pages;

import appmanager.Appender;
import appmanager.HelperBase;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

    public HashSet<String> sitemapUrls() throws IOException {
        String url = projectProperties.getProperty("web.baseUrl");
        HashSet<String> hashSet = new HashSet<>();
        Elements sitemaps = getElementsFromXml(url + "sitemap_index.xml");
        for (Element sitemap : sitemaps) {
            Elements sitemapUrls = getElementsFromXml(sitemap.text());
            for (Element sitemapUrl : sitemapUrls) {
                hashSet.add(sitemapUrl.text());
                System.out.println(sitemapUrl.text());
            }
        }
        System.out.println(hashSet.size());
        return hashSet;
    }

    public HashSet<String> hrefHashSet() throws IOException {
        HashSet<String> hrefHashSet = new HashSet<>();
        for (String sitemapUrl : sitemapUrls()) {
            Elements hrefs = getElementsFromHtml(sitemapUrl);
            for (Element href : hrefs) {
                String link = href.attr("href");
                if (!link.contains("#") && !link.contains("tel") && !link.contains("@") && !link.isEmpty()) {
                    hrefHashSet.add(link);
                }
            }
        }
        return hrefHashSet;
    }

    public HashSet<String> checkHrefHashSet() throws IOException {
        HashSet<String> brokenLinks = new HashSet<>();
        for (String href : hrefHashSet()) {
            if (responseCodeNumber(href) > 300) {
                brokenLinks.add(href);
            }
        }
        return brokenLinks;
    }
}

