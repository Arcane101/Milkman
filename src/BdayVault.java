import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class BdayVault {
    private static ArrayList<Bday> bdays = new ArrayList<>();
    public BdayVault(GuildMessageReceivedEvent event){
        for(Member m : event.getGuild().getMembers()){
            bdays.add(new Bday(m.getUser()));
        }
        load();
        new BdayTimer();
    }
    public static ArrayList<Bday> getBdays(){
        return bdays;
    }
    public void load(){
        try{
            File data = new File("bdays.txt");
            Scanner scan = new Scanner(data);
            while (scan.hasNextLine()){
                String[] args = scan.nextLine().split("~");
                for (Bday s: bdays) {
                    if(s.getPerson().getId().equals(args[0])){
                        s.setDate(args[1]);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("No BDay File! You will need to save");
        }
    }
    public static void save() throws IOException {
        String content = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        FileWriter fileWriter = new FileWriter("bdays.txt");
        for(Bday b: bdays){
            if (b.getDate() != null){
                content += (b.getPerson().getId() + "~" + dateFormat.format(b.getDate()) + "\n");
            }
        }
        fileWriter.write(content);
        fileWriter.close();
    }
    public void addBday(String in, User user){
        for (Bday s: bdays) {
            if(s.getPerson().getId().equals(user.getId())){
                s.setDate(in);
            }
        }
    }
}
