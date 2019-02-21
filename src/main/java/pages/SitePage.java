package pages;

import appmanager.Appender;
import appmanager.HelperBase;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
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
        try{
            if (hrefHashSet().size() > 0)
            return true;
        } catch (org.jsoup.HttpStatusException e) {
            System.out.println("Sitemap is missing");
        }
        return false;
    }

    public HashSet<String> sitemapUrlsWithBrokenLinks() throws IOException {
        String url = projectProperties.getProperty("web.baseUrl");
        String urlFromSitemap = null;
        HashSet<String> urlHashSet = new HashSet<>();
        HashSet<String> hrefHashSet = new HashSet<>();

        System.out.println("Checking sitemap URLs...");
        Elements sitemaps = getElementsFromXml(url + "sitemap_index.xml");//list of .xml
        for (Element sitemap : sitemaps) {
            Elements sitemapUrls = getElementsFromXml(sitemap.text());//list of all URLs
            for (Element sitemapUrl : sitemapUrls) {
                urlFromSitemap = sitemapUrl.text();
                Elements hrefs = getElementsFromHtml(urlFromSitemap);//list of all hrefs
                for (Element href : hrefs) {
                    String link = href.attr("href");
                    if (!link.contains("#") && !link.contains("tel") && !link.contains("@") && !link.isEmpty()) {
                        hrefHashSet.add(link);//hashSet of hrefs
                    }
                    for (String s : hrefHashSet) {
                        int num = responseCodeNumber(s);
                        System.out.println(s + " " + num);
                        if (num > 300 && num != 403 && num != 999) { //TODO Add valid check
                            urlHashSet.add(urlFromSitemap);
                        }
                    }
                }
            }
        }
        System.out.println("Total URLs with broken links count: " + urlHashSet.size());
        return urlHashSet;
    }

    public HashSet<String> hrefHashSet() throws IOException {
        String url = projectProperties.getProperty("web.baseUrl");
        HashSet<String> hashSet = new HashSet<>();
        Elements sitemaps = getElementsFromXml(url + "sitemap_index.xml");
        for (Element sitemap : sitemaps) {
            Elements sitemapUrls = getElementsFromXml(sitemap.text());
            for (Element sitemapUrl : sitemapUrls) {
                Elements hrefs = getElementsFromHtml(sitemapUrl.text());
                for (Element href : hrefs) {
                    String link = href.attr("href");
                    if (!link.contains("#") && !link.contains("tel") && !link.contains("@") && !link.isEmpty()) {
                        hashSet.add(link);
                    }
                }
            }
        }
        return hashSet;
    }

    public HashSet<String> brokenUrlsHashSet() throws IOException {
        HashSet<String> hashSet = new HashSet<>();
        System.out.println("Total count of unique links: " + hrefHashSet().size());
        System.out.println("Checking links...");
        for (String s : hrefHashSet()) {
            int num = responseCodeNumber(s);
            System.out.println(s + " " + responseCodeNumber(s));
            if (num > 300 && num != 403 && num != 999) { //TODO Add valid check
                hashSet.add(s);
            }
        }
        return hashSet;
    }

}

