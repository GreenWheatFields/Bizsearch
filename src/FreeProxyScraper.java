import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FreeProxyScraper {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://free-proxy-list.net/").userAgent(RandomUserAgent.getRandomUserAgent()).get();
        Elements test = doc.select("#proxylisttable > tbody");
        int count =0;
        String[] motherList = new String[20];
        for (int i = 0; i < motherList.length; i++){
            String test2 = doc.select("#proxylisttable > tbody > tr:nth-child(" + i +1 +")").text();
            String[]p = test2.split(" ");
            motherList[i] = p[0] + ":" + p[3];
            System.out.println(motherList[i]);

        }

        /*System.setProperty(p[1],p[3]);
        URL url = new URL("http://arc-sos.state.al.us/cgi/corpdetail.mbr/detail?page=number&num1=");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setConnectTimeout(1000);
        System.out.println(con.getResponseCode()); */
    }


}
