import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CookieAccount{

    private User owner;
    private int cookieCount;
    private String userId;
    private Guild guild;
    private ArrayList<User> givenFrom = new ArrayList<User>();
    private ArrayList<String> tokens = new ArrayList<String>();

    public int getCookieCount(){
        return cookieCount;
    }

    public String getUserId(){
        return userId;
    }

    public CookieAccount(User user){
        owner = user;
        cookieCount = 0;
        userId = user.getId();
    }

    public ArrayList<String> getTokens(){
        return tokens;
    }

    public void addToken(String in){
        if(in.equals("name")){
            tokens.add("name");
            System.out.println("name change token bought!");
        }else if (in.equals("mute")){
            tokens.add("mute");
            System.out.println("mute token bought!");
        }else if(in.equals("kick")){
            tokens.add("kick");
            System.out.println("kick token bought!");
        }else if(in.equals("ban")){
            tokens.add("ban");
            System.out.println("ban token bought!");
        }
    }

    public boolean hasToken(){
        return !tokens.isEmpty();
    }

    public boolean hasToken(String in){
        for (String s: tokens){
            if(s.equals(in)){
                return true;
            }
        }
        return false;
    }

    public void removeToken(String in){
        for(int i =0; i < tokens.size();i++){
            if (in.equals(tokens.get(i))){
                tokens.remove(i);
                break;
            }
        }
    }

    public void setCookieCount(int input){
        cookieCount = input;
    }

    public void addCookie(){
        cookieCount++;
    }

    public User getOwner(){return owner;}

    public void addgiven(User in){
        givenFrom.add(in);
    }

    public boolean hasgottenfrom(User in){
        return givenFrom.contains(in);
    }

    public void wipegiven(){
        givenFrom.clear();
    }

    public boolean isUser(User user){
        return user.getId().equals(userId);
    }

    public void setCookies(int in){
        cookieCount = in;
    }

    @Override
    public String toString(){
        return owner.getName() + ": " + cookieCount + " \uD83C\uDF6A";
    }
}
