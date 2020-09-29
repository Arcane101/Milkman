import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class PrayerTimer {
    public static String time = "";
    public PrayerTimer(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updateClock();
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 60000);
    }

    public static void updateClock(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        time = timeFormat.format(new Date());
        System.out.println(time);
        if(time.equals("06:30 PM") || time.equals("07:30 PM") || time.equals("05:30 PM") || time.equals("09:30 PM")){
            Commands.praiseallah();
        }else if(time.equals("12:01 AM") || time.equals("12:30 PM")){
            try{
                Document doc = Jsoup.connect("https://randomword.com/").userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
                Element e1 = doc.getElementById("random_word");
                Element e2 = doc.getElementById("random_word_definition");
                Commands.wordofday(e1.text(), e2.text());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getTime(){
        return time;
    }
}
