import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.logging.Logger;


import java.io.IOException;

public class Alabama {

    public static void main(String[] args) throws IOException {
        int count = 0;
        Document doc = Jsoup.connect(urlsAndKeys.AlabamaUrl()).userAgent("Mozilla/5.0").get();
        //Document doc1 = doc;
        Elements natureOfBusiness = doc.select("#block-sos-content > div > div > div > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(11) > td.aiSosDetailValue");
        String test = natureOfBusiness.text();
        //System.out.println(test);
        String bizInfo = doc.select("#block-sos-content > div > div > div > table:nth-child(2) > tbody:nth-child(2)").text();
        //System.out.println(bizInfo);
        Elements inspectors = doc.getElementsByClass("aiSosDetailDesc");

        for (Element e : inspectors){
            System.out.println(e.text() + "\n");
        }




        //System.out.println(inspectors);
        //System.out.println(inspectors);


    }
}
