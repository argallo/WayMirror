import com.sun.org.apache.xpath.internal.operations.Mod;

/**
 * Created by Gallo-Laptop on 7/9/2016.
 */
public class ModuleLoader {

    private Module moduleTopLeft;
    private Module moduleTopRight;
    private Module moduleBottomLeft;
    private Module moduleBottomRight;

    public void load(){
        // change to use config file
        moduleTopLeft = new WeatherModule();
        moduleTopRight = new StockModule();
        moduleBottomLeft = new NewsModule();
        moduleBottomRight = new CalendarModule();
    }

    public Module getModuleBottomLeft() {
        return moduleBottomLeft;
    }

    public Module getModuleBottomRight() {
        return moduleBottomRight;
    }

    public Module getModuleTopLeft() {
        return moduleTopLeft;
    }

    public Module getModuleTopRight() {
        return moduleTopRight;
    }


}



