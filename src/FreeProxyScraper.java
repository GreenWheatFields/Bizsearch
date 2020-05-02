import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class FreeProxyScraper {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://free-proxy-list.net/").userAgent(RandomUserAgent.getRandomUserAgent()).get();
        String[][] proxyList = new String[20][20];
        for (int i = 0; i < proxyList.length; i++){
            String test2 = doc.select("#proxylisttable > tbody > tr:nth-child(" + i +1 +")").text();
            String[]p = test2.split(" ");
            proxyList[0][i] = p[0];
            proxyList[1][i] = p[1];
        }
        System.out.println(proxyList[0][0] + " " + proxyList[1][0]);
        System.setProperty(proxyList[0][0],proxyList[0][1]);
        URL url = new URL("http://arc-sos.state.al.us/cgi/corpdetail.mbr/detail?page=number&num1=");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setConnectTimeout(1000);
        System.out.println(con.getResponseCode());
    }


}
