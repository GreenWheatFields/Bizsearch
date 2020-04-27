import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.logging.Logger;


import java.io.IOException;

public class Alabama {

    public static void main(String[] args) throws IOException {
        int count = 0;
        String URL = urlsAndKeys.AlabamaUrl();
        System.out.println(URL);
        Document doc = Jsoup.connect(URL).userAgent(RandomUserAgent.getRandomUserAgent()).get();
        //Document doc1 = doc;
        Elements natureOfBusiness = doc.select("#block-sos-content > div > div > div > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(11) > td.aiSosDetailValue");
        String test = natureOfBusiness.text();
        //System.out.println(test);
        String bizInfo = doc.select("#block-sos-content > div > div > div > table:nth-child(2) > tbody:nth-child(2)").text();
        //System.out.println(bizInfo);
        Elements inspectors = doc.getElementsByClass("aiSosDetailDesc");
        Elements inspectors2 = doc.getElementsByClass("aiSosDetailValue");
        String[][] table = new String[inspectors.size()][inspectors2.size()];
        for (Element e : inspectors) {
            //System.out.println(e.text());
            table[0][count] = e.text();
            count++;
        }
        count = 0;
        for (Element p : inspectors2){
            table[1][count] = p.text();
            count++;
        }

        for (int i = 0; i < inspectors.size()-1; i++){
                System.out.println(table[0][i] + " : " + table[1][i]);

        }


    //System.out.println(inspectors);
    //System.out.println(inspectors);
}
}

