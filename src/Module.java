import javax.swing.*;
import java.awt.*;

/**
 * Created by Gallo-Laptop on 7/9/2016.
 */
public class Module extends JPanel{

   public Module(){
       this.setBackground(Color.black);
       initModule();
   }

    public void initModule(){
        JLabel label = new JLabel("");
        label.setForeground(Color.WHITE);
        Font font = new Font("Roboto Condensed", Font.PLAIN, 30);
        label.setFont(font);
        add(label);
    }

}
