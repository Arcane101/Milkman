import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class CookieCounter {
    private static ArrayList<CookieAccount> accountDatebase = new ArrayList<CookieAccount>();
    public static Guild guild;

    public CookieCounter(GuildMessageReceivedEvent event){
        guild = event.getGuild();
        initialize();
    }

    public void initialize() {
        List<Member> users = guild.getMembers();
        for(Member m : users){
            accountDatebase.add(new CookieAccount(m.getUser()));
        }
        load();
    }

    public static ArrayList<CookieAccount> getAccountDatebase(){
        return accountDatebase;
    }

    public ArrayList<CookieAccount> getsortedDatabase(){
        ArrayList<CookieAccount> sortedlist = new ArrayList<CookieAccount>();
        sortedlist.addAll(accountDatebase);
        sortedlist.sort(new CookieSorter());
        sortedlist.removeIf(a -> a.getCookieCount() == 0);
        return sortedlist;
    }

    public CookieAccount getAccount(User user){
        for (CookieAccount a : accountDatebase){
            if (a.isUser(user)){
                return a;
            }
        }
        return null;
    }

    public static void save() throws IOException {
        String content = "";
        FileWriter fileWriter = new FileWriter("data.txt");
        for (CookieAccount a : accountDatebase){
            content += a.getUserId() + " " + a.getCookieCount()  + "\n";
        }
        fileWriter.write(content);
        fileWriter.close();
    }


    public void load()
    {
        try{
            File data = new File("data.txt");
            Scanner scan = new Scanner(data);
            while (scan.hasNextLine()){
                String[] args = scan.nextLine().split(" ");
                for (CookieAccount a: accountDatebase) {
                    if(a.getUserId().equals(args[0])){
                        a.setCookieCount(Integer.parseInt(args[1]));
                    }
                }
            }
        }catch (Exception e){
            System.out.println("No Data File! You will need to save");
        }
    }
}
