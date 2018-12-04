package pages;

import appmanager.HelperBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FaviconPage extends HelperBase {

    public FaviconPage(WebDriver driver) {
        super(driver);
    }

    public boolean isFaviconPresent() throws IOException {
        try {
            Document doc = Jsoup.connect(driver.getCurrentUrl()).userAgent("Mozilla").get();
            Element link = doc.head().select("link[href~=.*\\.(ico|png)]").first();
            return link != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public String croppedFaviconText() throws IOException, URISyntaxException {
        Document doc = Jsoup.connect(driver.getCurrentUrl()).userAgent("Mozilla").get();
        Element link = doc.head().select("link[href~=.*\\.(ico|png)]").first();
        String linkHref = link.attr("href");
        URI uri = new URI(linkHref);
        String[] segments = uri.getPath().split("/");
        return segments[segments.length - 1];
    }

    public boolean verifyFaviconLink() throws IOException {
        Document doc = Jsoup.connect(driver.getCurrentUrl()).userAgent("Mozilla").get();
        Element link = doc.head().select("link[href~=.*\\.(ico|png)]").first();
        String linkHref = link.attr("href");
        verifyLinkActive(linkHref);
        return true;
    }

}