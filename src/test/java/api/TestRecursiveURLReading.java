package api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TestRecursiveURLReading {

    public static void main(String[] args) {
        try {
            HashMap<String, String> h = new HashMap<>();
            String url = "https://wp-dev.space/no_client/mazur/master";
            Document doc = Jsoup.connect(url).get();

            //  Page Title
            String title = doc.title();

            //  Links in page
            Elements links = doc.select("a[href]");
            List url_array = new ArrayList();
            int i=0;
            url_array.add(url);
            h.put(url, title);
            Iterator<String> keySetIterator = h.keySet().iterator();
            while((i<=h.size())){
                try{
                    url = url_array.get(i).toString();
                    doc = Jsoup.connect(url).get();
//                    title = doc.title();
                    links = doc.select("a[href]");

                    for (Element link : links) {

                        String res= h.putIfAbsent(link.attr("href"), link.text());
                        if (res==null){
                            url_array.add(link.attr("href"));
                            System.out.println(link.attr("href"));
                        }
                    }
                }catch(Exception e){
                    System.out.println("\n"+e);
                }

                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}