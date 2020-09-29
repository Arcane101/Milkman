import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SpankCounter {
    private static int spanks = 0;

    public static void saveSpanks() throws IOException {
        String content = "";
        FileWriter fileWriter = new FileWriter("spanks.txt");
        fileWriter.write(String.valueOf(spanks));
        fileWriter.close();
    }
    public static void addSpank(){
        spanks++;
    }
    public static void loadSpanks(){
        try{
            File data = new File("spanks.txt");
            Scanner scan = new Scanner(data);
            while (scan.hasNextLine()){
                spanks = Integer.parseInt(scan.nextLine());
            }
        }catch (Exception e){
            System.out.println("Could not load spanks");
        }
    }
    public static int getSpanks(){
        return spanks;
    }
}
