import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by agallo on 7/10/16.
 */
public class StockModule extends Module {

    private static String stock = "http://finance.yahoo.com/d/quotes.csv?s=W&f=snd1l1yr";

    @Override
    public void initModule() {
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        try {
            String out = new Scanner(new URL(stock).openStream(), "UTF-8").useDelimiter("\\A").next();
            System.out.println(out);
            JLabel stockLabel = new JLabel("Wayfair Inc: "+out.substring(out.indexOf(",",40)+1 , out.indexOf(",",52)));
            stockLabel.setForeground(Color.WHITE);
            Font font = new Font("Roboto Condensed", Font.PLAIN, 30);
            stockLabel.setFont(font);
            stockLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            add(stockLabel);

        } catch (Exception e){

        }



    }
}
