import java.util.Timer;
import java.util.TimerTask;

public class cookieResetTimer {

    public cookieResetTimer(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                for (CookieAccount a: CookieCounter.getAccountDatebase()){
                    a.wipegiven();
                }
            }
        };
        timer.scheduleAtFixedRate(task,1000, 86400000);
    }
}
