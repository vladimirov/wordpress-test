package pages;

import appmanager.HelperBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FaviconPage extends HelperBase {

    public FaviconPage(WebDriver driver) {
        super(driver);
    }

    public List<Elements> elementsWithFavicons() throws IOException {
        String url = "https://wp-dev.space/ism/kitchen/develop/";
//        String url = "https://wp-dev.space/ism/countersteer/master/";
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.head().select("link[href~=.*\\.(ico|png)]");
        List<Elements> list = new ArrayList<>();
        for (Element link : links) {
            list.add(link.getElementsByAttribute("href"));
//            System.out.println(link.getElementsByAttribute("href"));
        }
        return list;
    }
}
//                <link rel="icon" href="https://wp-dev.space/ceatus/ybsg/develop/" +
//                "wp-content/uploads/2018/05/cropped-fav-icon-32x32.png" sizes="32x32">

//        Document document = Jsoup.parse(url);

