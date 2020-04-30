import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import javax.print.Doc;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
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
    public static void main(String[] args) throws IOException, ParseException {

        parse();
}
    public static void parse(/*String URL*/) throws IOException, ParseException {

        int count = 0;
        String URL = urlsAndKeys.AlabamaUrl();
        System.out.println(URL);

        Document doc = Jsoup.connect(URL).userAgent(RandomUserAgent.getRandomUserAgent()).get();
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
        csvWriter.append(URL).append(", USA, AL,");
        csvWriter.append(String.valueOf('"')).append(name).append(String.valueOf('"')).append(",");
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
            csvWriter.append(table[1][7]).append(",\n");//formation date
        }else if (table[1][4].equalsIgnoreCase("withdrawn")){
            Date date = parser.parse(table[1][5]);

            String formattedDate = formatter.format(date);

            csvWriter.append(formattedDate).append(","); //withdraw date
            date = parser.parse(table[1][8]);
            csvWriter.append(formatter.format(date));    //formation date
        }else {
            csvWriter.append("---,");
            Date date = parser.parse(table[1][6]);
            csvWriter.append(formatter.format(date));

        }
        csvWriter.flush();
        //csvWriter.close();

        System.out.println(table[0][6] + table[1][6]);

    }
}

