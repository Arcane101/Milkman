import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CookieMarket extends ListenerAdapter {

    private static User myself;
    private static ArrayList<CookieAccount> accountDatebase = new ArrayList<>();
    private static boolean flag = false;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        myself = event.getGuild().getSelfMember().getUser();
        String[] args = event.getMessage().getContentRaw().split("\\s");
        if(flag){
            accountDatebase = CookieCounter.getAccountDatebase();
            if (event.getMessage().getContentRaw().contains(Milkman.prefix + "market") && !event.getMessage().getAuthor().equals(event.getGuild().getSelfMember().getUser())){
                EmbedBuilder eb = new EmbedBuilder()
                        .setTitle("\uD83C\uDF6A Cookie Market \uD83C\uDF6A")
                        .setImage(event.getGuild().getSelfMember().getUser().getAvatarUrl())
                        .setColor(Color.orange)
                        .addField("","",false)
                        .addField("\uD83C\uDDE6 Change Name", "Change someone's name - 10 \uD83C\uDF6A", false)
                        .addField("\uD83C\uDDE7 Server Mute", "Server mute someone - 15 \uD83C\uDF6A", false)
                        .addField("\uD83C\uDDE8 Kick", "Kick someone - 100 \uD83C\uDF6A", false)
                        .addField("\uD83C\uDDE9 Ban", "Ban someone - 1000 \uD83C\uDF6A", false);
                event.getChannel().sendMessage(eb.build()).queue();
            }else if(event.getMessage().getAuthor().getId().equals(event.getGuild().getSelfMember().getId()) && !event.getMessage().getEmbeds().isEmpty() && event.getMessage().getEmbeds().get(0).getTitle().equals("\uD83C\uDF6A Cookie Market \uD83C\uDF6A")){
                event.getMessage().addReaction("\uD83C\uDDE6").queue();
                event.getMessage().addReaction("\uD83C\uDDE7").queue();
                event.getMessage().addReaction("\uD83C\uDDE8").queue();
                event.getMessage().addReaction("\uD83C\uDDE9").queue();
            }
        }else{
            flag = true;
        }

    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if(event.getReactionEmote().getName().equals("\uD83C\uDDE6") && !event.getUser().equals(myself)){
            for (CookieAccount a: accountDatebase){
                if(a.getUserId().equals(event.getUser().getId())){
                    a.addToken("name");
                    a.setCookies(a.getCookieCount()-10);
                    try {
                        CookieCounter.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }else if(event.getReactionEmote().getName().equals("\uD83C\uDDE7") && !event.getUser().equals(myself)){
            for (CookieAccount a: accountDatebase){
                if(a.getUserId().equals(event.getUser().getId())){
                    a.addToken("mute");
                    a.setCookies(a.getCookieCount()-15);
                    break;
                }
            }
        }else if(event.getReactionEmote().getName().equals("\uD83C\uDDE8") && !event.getUser().equals(myself)){
            for (CookieAccount a: accountDatebase){
                if(a.getUserId().equals(event.getUser().getId())){
                    a.addToken("kick");
                    a.setCookies(a.getCookieCount()-100);
                    break;
                }
            }
        }else if(event.getReactionEmote().getName().equals("\uD83C\uDDE9") && !event.getUser().equals(myself)){
            for (CookieAccount a: accountDatebase){
                if(a.getUserId().equals(event.getUser().getId())){
                    a.addToken("ban");
                    a.setCookies(a.getCookieCount()-1000);
                    break;
                }
            }
        }
    }
}
