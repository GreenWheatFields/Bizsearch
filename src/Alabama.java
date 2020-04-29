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

    public static void main(String[] args) throws IOException, ParseException {
        int count = 0;
        String URL = urlsAndKeys.AlabamaUrl();
        System.out.println(URL);
        Document doc = Jsoup.connect(URL).userAgent(RandomUserAgent.getRandomUserAgent()).get();
        Elements description = doc.getElementsByClass("aiSosDetailDesc");
        Elements value = doc.getElementsByClass("aiSosDetailValue");
        String name = doc.select("#block-sos-content > div > div > div > table:nth-child(2) > thead:nth-child(1) > tr > td").text();
        String[][] table = new String[description.size() +1][value.size() + 1];
        for (Element e : description) {
            //System.out.println(e.text());
            table[0][count] = e.text();
            count++;
        }
        count = 0;
        for (Element p : value){
            table[1][count] = p.text();
            count++;
        }
        //BufferedWriter csvWriter = new BufferedWriter(
        //        new FileWriter("Alabama.csv", true));


        FileWriter csvWriter = new FileWriter("Alabama.csv",true);
            //csvWriter.append("URL,Country,State,Name,ID#,Type,Status,Disolvation Date,Formation Date,Place of Formation,Principal Adderess, Prinicipal Mailing Address, Registered Agent Name, Registered Office Street Address, Registered Office Mailing Adderess, Nature of Buisness, Capital Authorized, Capital Paid In,Incorporator Name, Incorporator Street Adderess, Incorporator Mailing Adderess,\n");
        for (int i = 0; i < description.size(); i++){
           System.out.println(table[0][i] + " : " + table[1][i]);
        }
        csvWriter.append(URL).append(", USA, AL,");
            csvWriter.append(String.valueOf('"')).append(name).append(String.valueOf('"')).append(",");
            csvWriter.append(table[1][0]).append(",");
            csvWriter.append(table[1][1]).append(",");
            if (table[1][1].contains("Corporation")){
                csvWriter.append("Corporation,");
            }else if (table[1][1].contains("Limited")){
                csvWriter.append("LLC,");
            }else {
                csvWriter.append("---,");
            }
            csvWriter.append(table[1][4]).append(",");
            if (table[1][4].equalsIgnoreCase("dissolved")){
                var parser = new SimpleDateFormat("M-dd-yyyy");
                Date date = parser.parse(table[1][5]);
                var formatter = new SimpleDateFormat("MM/dd/yyyy");
                String formattedDate = formatter.format(date);
                csvWriter.append(formattedDate).append(",");
                csvWriter.append(table[1][6]).append(",\n");
            }else if (table[1][4].equalsIgnoreCase("withdrawn")){
                var parser = new SimpleDateFormat("M-dd-yyyy");
                Date date = parser.parse(table[1][5]);
                var formatter = new SimpleDateFormat("MM/dd/yyyy");
                String formattedDate = formatter.format(date);
                csvWriter.append(formattedDate).append(",");
            }else {
                csvWriter.append("---,").append(table[1][5]).append("\n");
            }
            csvWriter.flush();
            csvWriter.close();





     System.out.println(table[0][5] + table[1][5]);
    //System.out.println(description);
    //System.out.println(description);
}

}

