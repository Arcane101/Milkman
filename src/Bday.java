import net.dv8tion.jda.api.entities.User;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bday {
    private Date date = null;
    private User person;
    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public Date getDate(){
        return date;
    }
    public Bday(User s){
        person = s;
    }
    public User getPerson(){
        return person;
    }
    public void setDate(String in) {
        try{
            date = dateFormat.parse(in);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setPerson(User s){
        person = s;
    }
}
