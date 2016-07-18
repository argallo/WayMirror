import org.w3c.dom.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * Created by Gallo-Laptop on 7/9/2016.
 */
public class WeatherModule extends Module {


/*
*
*
			"01d": "wi-day-sunny",
			"02d": "wi-day-cloudy",
			"03d": "wi-cloudy",
			"04d": "wi-cloudy-windy",
			"09d": "wi-showers",
			"10d": "wi-rain",
			"11d": "wi-thunderstorm",
			"13d": "wi-snow",
			"50d": "wi-fog",
			"01n": "wi-night-clear",
			"02n": "wi-night-cloudy",
			"03n": "wi-night-cloudy",
			"04n": "wi-night-cloudy",
			"09n": "wi-night-showers",
			"10n": "wi-night-rain",
			"11n": "wi-night-thunderstorm",
			"13n": "wi-night-snow",
			"50n": "wi-night-alt-cloudy-windy"
* */

    private static String[] weatherCodes = new String[]{"01d", "02d","03d","04d","09d","10d","11d", "13d", "50d", "01n","02n","03n","04n","09n","10n","11n","13n","50n"};
    private static int[] weatherImagePosition = new int[]{1, 7, 13, 18, 16, 17, 15, 22, 12, 2, 13, 30, 31, 33, 34, 32, 38, 35};

    //TODO: get from configfile
    private static final String API_KEY = "EnterAPI_KEY";
    private static final String CITY = "Boston";
    private static String WeatherCall = "http://api.openweathermap.org/data/2.5/weather?q="+CITY+"&APPID="+API_KEY+"&mode=xml";
    //private static final String WeatherCall = "http://api.openweathermap.org/data/2.5/weather?q=Boston&APPID=4d337abf03a75e0c1ba4cef3d984c62a&mode=xml";


    private static final Insets insets = new Insets(0, 0, 0, 0);
    private float fahrenheit = 0;
    private String weather;
    private String weatherIcon;
    private String windSpeed;
    private JLabel time;

    @Override
    public void initModule() {

        this.setLayout(new GridBagLayout());
        try {

            String out = new Scanner(new URL(WeatherCall).openStream(), "UTF-8").useDelimiter("\\A").next();
            System.out.println(out);
            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = newDocumentBuilder.parse(new ByteArrayInputStream(out.getBytes()));
            Element root = doc.getDocumentElement();
            fahrenheit = (int)(((Float.parseFloat(root.getChildNodes().item(1).getAttributes().getNamedItem("value").getNodeValue()) * 9) / 5) - (459.67f));
            weatherIcon = root.getChildNodes().item(8).getAttributes().getNamedItem("icon").getNodeValue();
            weather = root.getChildNodes().item(8).getAttributes().getNamedItem("value").getNodeValue();
            windSpeed = root.getChildNodes().item(4).getFirstChild().getAttributes().getNamedItem("value").getNodeValue();
        } catch (Exception e){
            e.printStackTrace();
        }


        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.US);
        String asWeek = dateFormat.format(now);

        JLabel dayOfWeek = new JLabel(asWeek);
        dayOfWeek.setForeground(Color.WHITE);
        Font font = new Font("Roboto Condensed", Font.PLAIN, 30);
        dayOfWeek.setFont(font);
        addComponent(dayOfWeek, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0);

        Calendar cal = Calendar.getInstance();

        JLabel date  = new JLabel(new SimpleDateFormat("MMMM").format(cal.getTime())+ " "+cal.get(Calendar.DAY_OF_MONTH) +", "+cal.get(Calendar.YEAR));
        date.setForeground(Color.WHITE);
        date.setFont(font);
        addComponent(date, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,0);

        DateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        time = new JLabel(timeFormat.format(new Date()).toString());
        Font font2 = new Font("Roboto", Font.PLAIN, 40);
        time.setForeground(Color.WHITE);
        time.setFont(font2);
        addComponent(time, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,0);

        JLabel temperature = new JLabel(fahrenheit+"°F");
        temperature.setForeground(Color.WHITE);
        temperature.setFont(font);
        addComponent(temperature, 1, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,1);

        JLabel wind = new JLabel("Wind: "+windSpeed+"mph");
        wind.setForeground(Color.WHITE);
        wind.setFont(font);
        addComponent(wind, 1, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,0);

        JLabel weatherLabel = new JLabel(weather);
        weatherLabel.setForeground(Color.WHITE);
        weatherLabel.setFont(font);
        addComponent(weatherLabel, 1, 5, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,0);

        System.out.println(weatherIcon);
        for(int i = 0; i < weatherCodes.length; i++){
            if(weatherCodes[i].equals(weatherIcon)) {
                try {
                    BufferedImage img = ImageIO.read(this.getClass().getResource("meteocons.png"));
                    System.out.println(i);
                    img = img.getSubimage(((weatherImagePosition[i] % 9))*300, ((int)(weatherImagePosition[i]/9))*300, 300, 300);
                    Image image = img.getScaledInstance(200, 200,Image.SCALE_REPLICATE);
                    JLabel wIcon = new JLabel(new ImageIcon(image));
                    wIcon.setBorder(new EmptyBorder(-10,-10,-10,-10));
                    wIcon.setHorizontalAlignment(SwingConstants.LEFT);

                    addComponent(wIcon, 0, 3, 1, 3, GridBagConstraints.CENTER, GridBagConstraints.BOTH,0);
                } catch (Exception e) {
                }
                break;
            }

        }


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        sleep(1000);
                        DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
                        time.setText(timeFormat.format(new Date()).toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

    }

    private void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill, int ipadx) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, ipadx, 1.0, anchor, fill, insets, 0, 0);
        add(component, gbc);
    }

}
