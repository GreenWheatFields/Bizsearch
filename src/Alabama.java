import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public static int nextBiz = 100000;
    //private static String URL = urlsAndKeys.AlabamaUrl();
    private final static String baseURL = "http://arc-sos.state.al.us/cgi/corpdetail.mbr/detail?page=number&num1=";
    public static void main(String[] args) throws IOException, ParseException {

        parse(baseURL);
}
    public static void parse(String URL) throws IOException, ParseException {
        if (nextBiz > 100300){
            pseudoKillSwitch();
        }
        int count = 0;

        String searchURL = add(baseURL);

        Document doc = Jsoup.connect(searchURL).userAgent(RandomUserAgent.getRandomUserAgent()).get();
        Elements description = doc.getElementsByClass("aiSosDetailDesc");
        Elements value = doc.getElementsByClass("aiSosDetailValue");
        String name = doc.select("#block-sos-content > div > div > div > table:nth-child(2) > thead:nth-child(1) > tr > td").text();
        String[][] table = new String[description.size() +1][value.size() + 1]; //2d array for testing.
        for (Element e : description) {
            table[0][count] = e.text();
            count++;
        }
        count = 0;
        for (Element p : value){
            table[1][count] = p.text();
            count++;
        }
        for (int i = 0; i < description.size(); i++){
            System.out.println(table[0][i] + " : " + table[1][i]);
            //for testing
        }
        csvWriter.append(searchURL).append(", USA, AL,");
        csvWriter.append('"').append(name).append('"').append(",");
        csvWriter.append(table[1][0]).append(","); //entity number
        csvWriter.append(table[1][1]).append(","); //entity type full ex "Foreign Limited Liability Company"
        //first split in uniformity
        if (table[1][1].contains("Corporation")){
            csvWriter.append("Corporation,");
        }else if (table[1][1].contains("Limited")){
            csvWriter.append("LLC,");
        }else {
            csvWriter.append("---,");
        }
        csvWriter.append(table[1][4]).append(","); // status
        if (table[1][4].equalsIgnoreCase("dissolved")){
            Date date = parser.parse(table[1][5]);
            csvWriter.append(formatter.format(date)).append(","); //dissolve date
            csvWriter.append(table[1][7]).append(",");//formation date
            csvWriter.append('"').append(table[1][8]).append('"');//agent name
            csvWriter.append('"').append(table[1][9]).append('"');//register office street address
            csvWriter.append('"').append(table[1][10]).append('"').append("\n");//mailing address
            csvWriter.flush();
            parse(baseURL);

        }else if (table[1][4].equalsIgnoreCase("withdrawn")){
            Date date = parser.parse(table[1][5]);

            String formattedDate = formatter.format(date);

            csvWriter.append(formattedDate).append(","); //withdraw date
            date = parser.parse(table[1][8]);
            csvWriter.append(formatter.format(date)).append(",");    //formation date
            csvWriter.append('"').append(table[1][10]).append('"').append(","); //registered agent name
            csvWriter.append('"').append(table[1][11]).append('"').append(","); // registered Office street address
            csvWriter.append('"').append(table[1][12]).append('"').append("\n"); //registered office mailing address
            csvWriter.flush();
            parse(baseURL);



        }else if (table[1][4].equalsIgnoreCase("merged")){
            Date date = parser.parse(table[1][5]);
            csvWriter.append(formatter.format(date)).append(","); //merge date
            date = parser.parse(table[1][8]);
            csvWriter.append(formatter.format(date)).append(","); //formation date
            csvWriter.append('"').append(table[1][9]).append('"').append(","); //registered agent name
            csvWriter.append('"').append(table[1][10]).append('"').append(","); // office addy
            csvWriter.append('"').append(table[1][11]).append('"').append(",\n"); //mailing addy
            csvWriter.flush();
            parse(baseURL);

        } else {
            csvWriter.append("---,");
            Date date = parser.parse(table[1][6]);
            csvWriter.append(formatter.format(date)).append(","); //formation date
            csvWriter.append('"').append(table[1][7]).append('"').append(","); //registered agent name
            csvWriter.append('"').append(table[1][8]).append('"').append(",");  //registered mailing address
            csvWriter.append('"').append(table[1][9]).append('"').append("\n"); //register mailing address
            csvWriter.flush();
            parse(baseURL);

        }
        csvWriter.flush();
        //csvWriter.close();

        System.out.println(table[0][6] + table[1][6]);

    }
    private static String add(String URL){

        nextBiz++;
        return URL + nextBiz;
    }
    public static void pseudoKillSwitch(){
        System.out.println("done");
    }
}

