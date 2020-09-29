import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class BdayTimer {
    public static String date;
    private ArrayList<Bday> bdays;
    public BdayTimer(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    updateClock();
                } catch (Exception e) {
                    System.out.println("Could not update!");
                }
            }
        };
        timer.scheduleAtFixedRate(task, 100, 86400000);
    }

    public void updateClock() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd");
        bdays = BdayVault.getBdays();
        date = timeFormat.format(new Date());
        for(Bday s : bdays){
            if(s.getDate() != null && timeFormat.format(s.getDate()).equals(date)){
                Commands.sendBdayMessage(s.getPerson());
            }
        }
    }


    public String getDate(){
        return date;
    }
}
