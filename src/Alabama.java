import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Alabama {
    public static FileWriter csvWriter;

    static {
        try {
            csvWriter = new FileWriter("Alabama.csv",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SimpleDateFormat parser = new SimpleDateFormat("M-dd-yyyy");
    public static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    public static int nextBiz = 5649;
    //private static String URL = urlsAndKeys.AlabamaUrl();
    private final static String baseURL = "http://arc-sos.state.al.us/cgi/corpdetail.mbr/detail?page=number&num1=";
    public static int totalScraped = 0;
    private static String formatted;
    public static String lastIP;
    public static int proxyCount = 0;
    public static void main(String[] args) throws IOException, ParseException {
        //checkConn();
        parse();
        //setProxy();
        //System.out.println("done");
        //System.out.println(nextBiz);



}
    public static void parse() throws IOException, ParseException {
      /* if (proxyCount < 1){
            proxyCount++;
            setProxy();
        }
        if (proxyCount >= 74){
            proxyCount = 0;
            parse();
        }*/
        while(totalScraped < 75000) {
            nextBiz++;
            String searchURL = baseURL + String.format("%06d", nextBiz);
            System.out.println(searchURL);
            Document doc = Jsoup.connect(searchURL).userAgent(RandomUserAgent.getRandomUserAgent()).get();
            System.out.println("test1"); //repeating bug might be from not accurately checking for the "too many request" message.
            String check = doc.select("#block-sos-content > div > div > div > b").text();
            int check2 = doc.getAllElements().size(); //9 is blank listing message
            if(check2 == 9){
                totalScraped = 0;
                killSwitch();
            }
            if (check.equalsIgnoreCase("No matches found.")){
                parse();
            }
            Elements description = doc.getElementsByClass("aiSosDetailDesc");
            Elements value = doc.getElementsByClass("aiSosDetailValue");
            String name = doc.select("#block-sos-content > div > div > div > table:nth-child(2) > thead:nth-child(1) > tr > td").text();
            String[][] table = new String[description.size() + 1][value.size() + 1]; //2d array for testing.


            int count = 0;
            for (Element e : description) {
                table[0][count] = e.text();
                count++;
            }
            count = 0;
            for (Element p : value) {
                table[1][count] = p.text();
                count++;
            }
            System.out.println(name);
           /* for (int i = 0; i < description.size(); i++) {
                System.out.println(table[0][i] + " : " + table[1][i]);
                //for testing
            }*/
            csvWriter.append(searchURL).append(", USA, AL,");
            csvWriter.append('"').append(name).append('"').append(",");
            csvWriter.append(table[1][0]).append(","); //entity number
            csvWriter.append(table[1][1]).append(","); //entity type full ex "Foreign Limited Liability Company"
            //first split in uniformity
            if (table[1][1].contains("Corporation")) {
                csvWriter.append("Corporation,");
            } else if (table[1][1].contains("Limited")) {
                csvWriter.append("LLC,");
            } else {
                csvWriter.append("---,");
            }
            System.out.println("test2");
            csvWriter.append(table[1][4]).append(","); // business status
            switch (table[1][4].toLowerCase()){
                case "dissolved":
                    Date date = parser.parse(table[1][5]);
                    csvWriter.append(formatter.format(date)).append(","); //dissolve date
                    csvWriter.append(table[1][7]).append(",");//formation date
                    csvWriter.append('"').append(table[1][8]).append('"').append(",");//agent name
                    csvWriter.append('"').append(table[1][9]).append('"').append(",");//register office street address
                    csvWriter.append('"').append(table[1][10]).append('"').append(",\n");//mailing address
                    csvWriter.flush();
                    proxyCount++;
                    totalScraped++;
                    System.out.println(totalScraped);
                    parse();
                case "withdrawn":
                    Date date2 = parser.parse(table[1][5]);
                    String formattedDate = formatter.format(date2);
                    csvWriter.append(formattedDate).append(","); //withdraw date
                    date2 = parser.parse(table[1][8]);
                    csvWriter.append(formatter.format(date2)).append(",");    //formation date
                    csvWriter.append('"').append(table[1][10]).append('"').append(","); //registered agent name
                    csvWriter.append('"').append(table[1][11]).append('"').append(","); // registered Office street address
                    csvWriter.append('"').append(table[1][12]).append('"').append("\n"); //registered office mailing address
                    csvWriter.flush();
                    proxyCount++;
                    totalScraped++;
                    System.out.println(totalScraped);
                    parse();
                case "merged":
                    Date date3 = parser.parse(table[1][5]);
                    csvWriter.append(formatter.format(date3)).append(","); //merge date
                    date3 = parser.parse(table[1][8]);
                    csvWriter.append(formatter.format(date3)).append(","); //formation date
                    csvWriter.append('"').append(table[1][9]).append('"').append(","); //registered agent name
                    csvWriter.append('"').append(table[1][10]).append('"').append(","); // office addy
                    csvWriter.append('"').append(table[1][11]).append('"').append(",\n"); //mailing addy
                    csvWriter.flush();
                    totalScraped++;
                    proxyCount++;
                    System.out.println(totalScraped);
                    parse();
                case "consolidated":
                    Date date4 = parser.parse(table[1][5]);
                    csvWriter.append(formatter.format(date4)).append(",");
                    date4 = parser.parse(table[1][8]);
                    csvWriter.append(formatter.format(date4)).append(","); //formation date
                    csvWriter.append('"').append(table[1][9]).append('"').append(","); //registered agent name
                    csvWriter.append('"').append(table[1][10]).append('"').append(","); // office addy
                    csvWriter.append('"').append(table[1][11]).append('"').append(",\n"); //mailing addy
                    csvWriter.flush();
                    totalScraped++;
                    proxyCount++;
                    System.out.println(totalScraped);
                    parse();

              //  case "cancelled":
                //    System.out.println("CANCELLED" + searchURL);

                default:
                    csvWriter.append("---,");
                    Date date5 = parser.parse(table[1][6]);
                    csvWriter.append(formatter.format(date5)).append(","); //formation date
                    csvWriter.append('"').append(table[1][7]).append('"').append(","); //registered agent name
                    csvWriter.append('"').append(table[1][8]).append('"').append(",");  //registered mailing address
                    csvWriter.append('"').append(table[1][9]).append('"').append(",").append("\n"); //register mailing address
                    csvWriter.flush();
                    totalScraped++;
                    proxyCount++;
                    System.out.println(totalScraped);
                    parse();


            }
            csvWriter.flush();
        }


    }

    private static void setProxy() throws IOException {
        Authenticator.setDefault(
                new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(urlsAndKeys.password(), urlsAndKeys.user().toCharArray());
                    }
                }
        );
        int randomServer = 0;
        var rd = new Random();
        randomServer = rd.nextInt(19)+1;
        URL url = new URL("https://api.nordvpn.com/v1/servers/recommendations?filters\\228\\=81"); //https://api.nordvpn.com/v1/servers/recommendations
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(1000);
        con.connect();
        int l = con.getResponseCode();
        System.out.println(l);
        JsonParser jp = new JsonParser();
        JsonArray root = jp.parse(new InputStreamReader((InputStream) con.getContent())).getAsJsonArray();
        JsonObject servers = root.get(randomServer).getAsJsonObject();
        String hostname = servers.getAsJsonPrimitive("hostname").getAsString();
        if (hostname.equals(lastIP)){
            setProxy();
        }
        lastIP = hostname;
        System.out.println("getting proxies");
        System.setProperty("http.proxyHost", hostname);
        System.setProperty("http.proxyPort", "80");
        System.setProperty("http.proxyUser", urlsAndKeys.user());
        System.setProperty("http.proxyPassword", urlsAndKeys.password());


        URL url2 = new URL("http://arc-sos.state.al.us/cgi/corpdetail.mbr/detail?page=number&num1");
        HttpURLConnection con2 = (HttpURLConnection)url2.openConnection();

        int newResponseCode = con2.getResponseCode();
        System.out.println(newResponseCode);
        System.out.println(newResponseCode + " getting response code.");
        if (newResponseCode == 407){
            System.out.println("Bad proxy. Getting new Proxy");
            setProxy();
        }
        System.out.println("proxy set ");
       // System.out.println(hostname);
    }
    public static void killSwitch(){
        System.out.println("BANNED");
    }
}

