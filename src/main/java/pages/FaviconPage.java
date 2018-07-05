package pages;

import appmanager.HelperBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;

public class FaviconPage extends HelperBase {

    public FaviconPage(WebDriver driver) {
        super(driver);
    }

    public boolean faviconIsOnSite() {

        String link = "https://github.com";

        Document doc = Jsoup.parse(link);
        Element element = doc.head().select("link[href~=.*\\.(ico|png)]").first();
        String href = getElementAttribute("href");
        return element.attr("href").equals(href);
    }

}
