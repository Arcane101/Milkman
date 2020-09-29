import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;



import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

public class Commands extends ListenerAdapter {

    private static String[] commands = {"info", "cmds", "ping", "join", "leave",
            "boog", "cookies", "leaderboard", "prune", "pog", "plot", "hierarchy",
            "whip", "spank", "birthday", "virgin"};
    private static MessageChannel channel;
    private static User author;
    private static String content;
    private static Message message;
    private static Guild server;
    private boolean flag = false;
    private boolean isBot;
    private CookieCounter cookieCounter;
    private BdayVault bdays;

    public void doCmd(String input, String[] args){
        int x = (int)(Math.random()*100)+1;
        if(x == 100){
            goldboog();
        }
        else if (input.equals(commands[0]))
        {
            info();
        }

        else if(input.equals(commands[1]))
        {
            cmds();
        }

        else if(input.equals(commands[2]))
        {
            pong();
        }
        else if(input.equals(commands[3]))
        {
            join();
        }
        else if(input.equals(commands[4]))
        {
            leave();
        }
        else if(input.equals(commands[5]))
        {
            boog();
        }
        else if(input.equals(commands[6]))
        {
            cookies(args);
        }else if(input.equals(commands[7])){
            leaderboard();
        }else if(input.equals(commands[8])){
            prune(args);
        }else if(input.equals(commands[9])){
            pog();
        }else if(input.equals(commands[10])){
            plot(args);
        }else if(input.equals(commands[11])){
            hierarchy();
        }else if(input.equals(commands[12])){
            whip();
        }else if(input.equals(commands[13])){
            spank();
        }
        else if(input.equals(commands[14])){
            birthday(args);
        }
    }

    public void birthday(String[] args){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        if(args.length == 1){
            for (Bday s : BdayVault.getBdays()){
                if(s.getPerson() == author){
                    channel.sendMessage("Your birthday is on: " + dateFormat.format(s.getDate())).queue();
                }
            }
        }else if(!message.getMentionedUsers().isEmpty()){
            boolean flag = false;
            for(Bday s : BdayVault.getBdays()){
                if(message.getMentionedUsers().get(0).getId().equals(s.getPerson().getId())){
                    User mentioned = message.getMentionedUsers().get(0);
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
                    channel.sendMessage("<@" + mentioned.getId() + ">'s birthday is " + dateFormat1.format(s.getDate())).queue();
                    flag = true;
                }
            }
            if(!flag){
                channel.sendMessage("The user does not have their birthday set!").queue();
            }

        }
        else{
            bdays.addBday(args[1], author);
            for (Bday s : BdayVault.getBdays()){
                if(s.getPerson() == author){
                    System.out.println(s.getDate().toString());
                }
            }
        }
    }


    public void spank(){
        SpankCounter.addSpank();
        channel.sendMessage("I'm sorry for my failures.\nTotal Spanks: " + SpankCounter.getSpanks()).queue();
    }
    public void hierarchy(){
        int n = 1;
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("Server Role Hierarchy")
                .setColor(Color.BLUE);
        String stars = "";
        for (Role role: server.getRoles()) {
            eb.addField(role.getName(), String.valueOf(n), true);
            n++;
        }
        eb.setImage(server.getIconUrl());
        channel.sendMessage(eb.build()).queue();
    }


    public void prune(String[] args) throws NullPointerException{
        if (server.getMember(author).hasPermission(Permission.MESSAGE_MANAGE) || author.getId().equals("676501252145676292")){
            int amount = Integer.parseInt(args[1]);
            MessageHistory messagehistory = channel.getHistoryBefore(message.getId(), amount).complete();
            List<Message> messages = messagehistory.getRetrievedHistory();
            for (Message m : messages){
                m.delete().queue();
            }
            message.delete().queue();
        }else{
            channel.sendMessage("You do not have permission!").queue();
        }
    }

    public void pog(){
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("POG")
                .setColor(Color.MAGENTA)
                .setImage("https://tr.rbxcdn.com/b5e957ee8feeaaaaf6dd0162bcb6d91f/420/420/Decal/Png.png");
        channel.sendMessage(eb.build()).queue();
    }

    public void plot(String[] args) throws NullPointerException{
        EmbedBuilder eb = new EmbedBuilder()

                .setColor(Color.CYAN);
        if(!message.getEmbeds().isEmpty()){
            try{
                eb.setImage(message.getEmbeds().get(0).getUrl());
                eb.setTitle(message.getContentRaw().substring(6,(message.getContentRaw().length() - message.getEmbeds().get(0).getUrl().length())));

            }catch (Exception ignored){

            }
        }else{
            eb.setImage(server.getSelfMember().getUser().getAvatarUrl());
            eb.setTitle(message.getContentRaw().substring(5));
        }
        eb.addField("", "React to this image with a ✅ if you want to go.", false);
        channel.sendMessage(eb.build()).queue();
        message.delete().queue();
    }

    public void whip(){
        if(message.getMentionedUsers().isEmpty()){
            channel.sendMessage("@ Who you want to whip").queue();
        }else if(!message.getMentionedUsers().isEmpty()){
            User whipped = message.getMentionedUsers().get(0);
            channel.sendMessage("<@" + whipped.getId() + ">, You've been whipped!").queue();
        }
    }

    public static void sendBdayMessage(User s){
        server.getTextChannelById("644755654531088415").sendMessage(server.getPublicRole().getAsMention() + " It is " + s.getName() + "'s birthday today!").queue();
    }

    public static void praiseallah(){
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.yellow)
                .setTitle("Prayer Time")
                .setImage("https://cdn.discordapp.com/attachments/605923391240929300/702353624621121576/86416841864186.gif");
        server.getTextChannelById("665857849561448478").sendMessage(eb.build()).queue();
    }
    public static void wordofday(String s1, String s2){
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("Word of The Day")
                .addField((s1.substring(0, 1).toUpperCase() + s1.substring(1)), ("Definition: " + s2), true)
                .setColor(Color.orange);
        server.getTextChannelById("665857849561448478").sendMessage(eb.build()).queue();
    }

    public void leaderboard(){
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("\uD83C\uDF6A Cookies Leaderboard \uD83C\uDF6A")
                .setColor(Color.orange)
                .setImage(server.getSelfMember().getUser().getAvatarUrl())
                .addField("","Note: You must have 1 or above to appear.", false);
        for (CookieAccount a : cookieCounter.getsortedDatabase()){
            String amount = "" + a.getCookieCount();
            eb.addField(a.getOwner().getName(), amount + " \uD83C\uDF6A", false);
        }
        channel.sendMessage(eb.build()).queue();
        message.delete().queue();
    }

    public void info(){
        EmbedBuilder eb = new EmbedBuilder().setTitle("I Am The Milkman").addField("My milk is delicious", "", false)
                .setImage(server.getSelfMember().getUser().getAvatarUrl()).setColor(Color.cyan);
        channel.sendMessage(eb.build()).queue();
        message.delete().queue();
    }

    public void cmds(){
        EmbedBuilder eb = new EmbedBuilder().setTitle("~MILKMAN COMMANDS~").setColor(Color.cyan);
        for (String cmd : commands ){
            eb.addField((Milkman.prefix + cmd), "", true);
        }
        eb.setImage(server.getSelfMember().getUser().getAvatarUrl());
        channel.sendMessage(eb.build()).queue();
        message.delete().queue();
    }

    public void pong(){
        channel.sendMessage("Pong!").queue();
    }

    public void join(){
        if(!server.getSelfMember().hasPermission(Permission.VOICE_CONNECT)){
            channel.sendMessage("I can't join the voice channel!").queue();
        }
        else {
            VoiceChannel vc = message.getMember().getVoiceState().getChannel();
            if (vc != null){
                AudioManager audioManager = server.getAudioManager();
                audioManager.openAudioConnection(vc);
                message.delete().queue();
            }
        }
    }

    public void leave(){
        VoiceChannel vc = server.getSelfMember().getVoiceState().getChannel();
        if (vc != null){
            AudioManager audioManager = server.getAudioManager();
            audioManager.closeAudioConnection();
            message.delete().queue();
        }else{
            channel.sendMessage("I'm not in a voice channel!").queue();
        }
    }

    public void boog(){
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("BOOG")
                .addField("GET BOOGED!", "", false)
                .setImage("https://images-ext-1.discordapp.net/external/x4AUpDuQRzBfViWAP_cTkucP865ecVi_G_-np-XHOt8/https/media.discordapp.net/attachments/534386980390174720/699860558991720498/boog-1-1.gif")
                .setColor(Color.green);
        channel.sendMessage(eb.build()).queue();
        message.delete().queue();
    }

    public void goldboog(){
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("THE GOLDEN BOOG")
                .addField("GET BOOGED! Fuck your command", "", false)
                .setImage("https://images-ext-1.discordapp.net/external/x4AUpDuQRzBfViWAP_cTkucP865ecVi_G_-np-XHOt8/https/media.discordapp.net/attachments/534386980390174720/699860558991720498/boog-1-1.gif")
                .setColor(Color.orange);
        channel.sendMessage(eb.build()).queue();
        message.delete().queue();
    }

    public void cookies(String[] args){
        List<User> mentioned = message.getMentionedUsers();
        if (mentioned.isEmpty()){
            String cookies = "" + cookieCounter.getAccount(author).getCookieCount();
            EmbedBuilder eb = new EmbedBuilder()
                    .setTitle(author.getName() + "'s Cookies:")
                    .setImage(author.getAvatarUrl())
                    .addField("Cookies: ", cookies + " \uD83C\uDF6A", false)
                    .setColor(Color.ORANGE);
            channel.sendMessage(eb.build()).queue();
        }else{
            String cookies = "" + cookieCounter.getAccount(mentioned.get(0)).getCookieCount();
            EmbedBuilder eb = new EmbedBuilder()
                    .setTitle(mentioned.get(0).getName() + "'s Cookies:")
                    .setImage(mentioned.get(0).getAvatarUrl())
                    .addField("Cookies: ", cookies + " \uD83C\uDF6A", false)
                    .setColor(Color.ORANGE);
            channel.sendMessage(eb.build()).queue();
        }
    }

    public static Guild getServer(){
        return server;
    }


    // DEFAULT METHODS
    public void onGuildMessageReceived( GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s");

        if(!flag){
            cookieCounter = new CookieCounter(event);
            bdays = new BdayVault(event);
            flag = true;
        }
        channel = event.getChannel();
        author = event.getAuthor();
        content = event.getMessage().getContentDisplay();
        message = event.getMessage();
        server = event.getGuild();
        isBot = message.getAuthor().equals(server.getSelfMember().getUser());
        if(isBot){
            ifBot(event);
        }
        isCmd(args);
    }

    public void isCmd(String[] input){
        for (String cmd : commands){
            if (content.contains(Milkman.prefix + cmd)){
                doCmd(cmd, input);
            }
        }
    }

    public void ifBot(GuildMessageReceivedEvent event){
       if(!event.getMessage().getEmbeds().isEmpty()){
           MessageEmbed eb = event.getMessage().getEmbeds().get(0);
           if((!eb.getFields().isEmpty()) && eb.getFields().get(0).getValue().equals("React to this image with a ✅ if you want to go.")){
               event.getMessage().addReaction("✅").queue();
           }
           if(eb.getTitle().equals("Prayer Time")){
               event.getChannel().sendMessage("<@307701386672340992> !").queue();
           }
       }
    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event){
        System.out.println("reacton recieved");
        if(flag){
            if(event.getReactionEmote().getName().equals("\uD83C\uDF6A") && !event.getUser().getId().equals(event.getChannel().retrieveMessageById(event.getMessageId()).complete().getAuthor().getId())){
                User messageauthor = event.getChannel().retrieveMessageById(event.getMessageId()).complete().getAuthor();
                CookieAccount useraccount = cookieCounter.getAccount(messageauthor);

                if(!useraccount.hasgottenfrom(event.getUser())){
                    useraccount.addCookie();
                    useraccount.addgiven(event.getUser());
                    sendPM(messageauthor, event.getUser().getName() + " has sent you a cookie!");
                }else{
                    event.getChannel().sendMessage("You must wait a day before giving this person another cookie!").queue();
                }
            }else if(event.getReactionEmote().getName().equals("\uD83C\uDF6A") && event.getUser().getId().equals(event.getChannel().retrieveMessageById(event.getMessageId()).complete().getAuthor().getId())){
                channel.sendMessage("You can't give yourself a cookie! \uD83D\uDE20").queue();
            }
        }
    }

    public void onJamesBan(GuildMemberJoinEvent event){
        if (event.getUser().getId().equals("341745228782370826")){
            server.addRoleToMember("341745228782370826", server.getRoleById("723257795943596113")).queue();
            server.addRoleToMember("341745228782370826", server.getRoleById("625589959038074910")).queue();
        }
    }

    public static void sendPM(User user, String message){
        try {
            user.openPrivateChannel().complete().sendMessage(message).queue();
        }catch (ErrorResponseException e){
            e.printStackTrace();
        }
    }
    public static void sendPM(User user, File file){
        try {
            user.openPrivateChannel().complete().sendFile(file).queue();
        }catch (ErrorResponseException e){
            e.printStackTrace();
        }
    }
}
