import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;



public class Milkman {

    public static String prefix = "~";

    // Main method

    public static void main(String[] args) throws LoginException {
        PrayerTimer timer2 = new PrayerTimer();
        System.out.println(timer2.getTime());
        JDABuilder builder = new JDABuilder();
        cookieResetTimer timer = new cookieResetTimer();
        builder.setToken("NzQyMTU3MDk1ODg5MTQxODYw.XzCBqA.YLiAQ8iZn0-EbJQZvrN5jAdWuHY");
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("In Fayth's House \uD83E\uDD5B || ~cmds"));
        SpankCounter.loadSpanks();
        //Register listerners

        builder.addEventListeners(new Commands());
        builder.addEventListeners(new CookieMarket());

        //build
        builder.build();

        //JFRAME

        JButton button = new JButton("Exit");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    CookieCounter.save();
                    SpankCounter.saveSpanks();
                    BdayVault.save();
                    System.exit(-1);
                } catch (IOException ioException) {
                    System.out.println("Could not Save!");
                }
            }
        });
        button.setBounds(5,5,65,35);
        JFrame frame = new JFrame("Close Milkman?");
        frame.add(button);
        button.setForeground(Color.black);
        button.setBackground(Color.red);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 250, Toolkit.getDefaultToolkit().getScreenSize().height - 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(75, 40);
        frame.setUndecorated(true);
        frame.setState(Frame.ICONIFIED);
        frame.setVisible(true);
    }
}