import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * Created by agallo on 7/10/16.
 */
public class NewsModule extends Module {

    private static String newsID = "https://api.nytimes.com/svc/topstories/v1/home.json?api-key=EnterKeyHere";
    private static ArrayList<String> headlines;

    private JLabel headlineLabel;
    private Random random;

    @Override
    public void initModule() {
        super.initModule();
        random = new Random();

        try {
            System.out.println("Made it!");
            String out = new Scanner(new URL(newsID).openStream(), "UTF-8").useDelimiter("\\A").next();
            out = out.replaceAll("\\\\u"," ");
            System.out.println("Made it!");
            String[] storys = out.split("\"title\":", -1);

            headlines = new ArrayList<String>();
            for(String story : storys){
                if(story.contains("\"abstract\":")) {
                    headlines.add(story.substring(0, story.indexOf("\"abstract\":")-1));
                }
            }
        } catch (Exception e){

        }

        headlineLabel = new JLabel("<html>"+getRandomHeadline()+"<html>");
        Font font = new Font("Roboto", Font.PLAIN, 30);
        headlineLabel.setForeground(Color.WHITE);
        headlineLabel.setPreferredSize(new Dimension(640, 350));
        headlineLabel.setFont(font);
        headlineLabel.setHorizontalAlignment(JLabel.LEFT);
        headlineLabel.setVerticalAlignment(JLabel.BOTTOM);
        add(headlineLabel);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        sleep(8000);
                        headlineLabel.setText("<html>"+getRandomHeadline()+"<html>");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();


    }

    public String getRandomHeadline(){
        return headlines.get(random.nextInt(headlines.size()));
    }
}
