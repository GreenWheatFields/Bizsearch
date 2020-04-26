import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.logging.Logger;


import java.io.IOException;

public class Alabama {

    public static void main(String[] args) throws IOException {

        Document doc = Jsoup.connect(urlsAndKeys.AlabamaUrl()).userAgent("Mozilla/5.0").get();
        Elements natureOfBusiness = doc.select("#block-sos-content > div > div > div > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(11) > td.aiSosDetailValue");
        String test = natureOfBusiness.outerHtml();
        System.out.println(test);

    }
}
