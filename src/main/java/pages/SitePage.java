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

    public List<String> brokenLinks() throws IOException {
        System.out.println("Waiting for HTTP URL Connection response...");
        List<String> list = new ArrayList<>();
        String url = projectProperties.getProperty("web.baseUrl");
        HashSet<String> hashSet = hrefHashSet(url);
        for (String s : hashSet) {
            System.out.println(s + " " + responseCodeNumber(s));
//            if (responseCodeNumber(s) > 300 && responseCodeNumber(s) != 999 && responseCodeNumber(s) != 403 && responseCodeNumber(s) != 301) {
            if (responseCodeNumber(s) > 300) {
                list.add(s);
            }
        }
        System.out.println("Total broken links count: " + list.size());
        return list;
    }

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

//    public HashSet<String> hrefHashSet() throws IOException {
    public HashSet<String> brokenHashSet() throws IOException {
        String url = projectProperties.getProperty("web.baseUrl");
        HashSet<String> hashSet = new HashSet<>();
        HashSet<String> brokenHashSet = new HashSet<>();
        Elements sitemaps = getElementsFromXml(url + "sitemap_index.xml");
        for (Element sitemap : sitemaps) {
            Elements sitemapUrls = getElementsFromXml(sitemap.text());
            for (Element sitemapUrl : sitemapUrls) {
                Elements hrefs = getElementsFromHtml(sitemapUrl.text());
                for (Element href : hrefs) {
                    String link = href.attr("href");
                    if (!link.contains("#") && !link.contains("tel") && !link.contains("@") && !link.isEmpty()) {
//                        hashSet.add(link);
                        if (responseCodeNumber(link) > 300) {
                            brokenHashSet.add(sitemapUrl.text());
                        }
                    }
                }
            }
        }
        System.out.println(brokenHashSet);
        System.out.println("BROKEN LINKS SIZE IS; " + hashSet.size());
        return brokenHashSet;
    }

//    public HashSet<String> brokenHashSet() throws IOException {
//        HashSet<String> hashSet = new HashSet<>();
//        for (String s : hrefHashSet()) {
//            if (responseCodeNumber(s) > 300) {
//                hashSet.add(s);
//            }
//        }
//        System.out.println("BROKEN LINKS SIZE IS; " + hashSet.size());
//        return hashSet;
//    }


}

