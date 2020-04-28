import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.FileWriter;
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
        FileWriter csvWriter = new FileWriter("Alabama.csv");
        csvWriter.append("State,");
        csvWriter.append("Country,");
        csvWriter.append("ID Number");
        csvWriter.append(",");
        csvWriter.append("Entity Type");
        csvWriter.append(",");
        csvWriter.append("Principal Address,");
        csvWriter.append("\n");
        csvWriter.append("AL,");
        csvWriter.append("USA,");
        csvWriter.append(table[1][0]).append(",");
        csvWriter.append(table[1][1]).append(",");
        csvWriter.append(table[1][2]).append("\n");
        csvWriter.flush();
        csvWriter.close();

     //   System.out.println(table[0][5] + table[1][5]);
    //System.out.println(inspectors);
    //System.out.println(inspectors);
}

}

