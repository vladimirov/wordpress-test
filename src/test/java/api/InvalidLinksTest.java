package api;

import appmanager.TestBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class InvalidLinksTest extends TestBase {

    public static void main(String[] args) throws IOException {

        Set<String> initial_link_set = new HashSet();
        initial_link_set.add("http://abc.com/");
        Set<String> final_link_set = retrieveAllLinksFromSite(1, initial_link_set);

        Document docs = Jsoup.connect("https://wp-dev.space/no_client/mazur/master/").get();
//        Document docs = Jsoup.connect("https://wp-dev.space/no_client/mazur/master/?p=1#comments").get();
        Elements links = docs.select("a[href]");
        System.out.println("Total Links :" + links.size());
        for (Element link : links) {
            String hrefUrl = link.attr("href");
            if (!"#".equals(hrefUrl) && !hrefUrl.isEmpty()) {
                System.out.println(hrefUrl);
            }
        }

    }
}




