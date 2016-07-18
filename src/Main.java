import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Gallo-Laptop on 7/9/2016.
 */
public class Main {

    public static final int MirrorWidth = 1280;
    public static final int MirrorHeight = 800;


    public static void main(String args[]){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowMirror();
            }
        });
    }

    public static void createAndShowMirror() {
        JFrame mirror = new JFrame("JMirror");
        mirror.setPreferredSize(new Dimension(MirrorWidth, MirrorHeight));
        //fullScreen(mirror, true);
        GraphicsDevice d = GraphicsEnvironment
                .getLocalGraphicsEnvironment().getDefaultScreenDevice();

        mirror.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mirror.setUndecorated(true);
        mirror.getContentPane().setBackground(Color.black);
        mirror.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ModuleLoader loader = new ModuleLoader();
        loader.load();

        GridLayout layout = new GridLayout(2, 2, 5, 5);
        mirror.getContentPane().setLayout(layout);
        mirror.getContentPane().add(loader.getModuleTopLeft());
        mirror.getContentPane().add(loader.getModuleTopRight());
        mirror.getContentPane().add(loader.getModuleBottomLeft());
        mirror.getContentPane().add(loader.getModuleBottomRight());


        mirror.pack();
        mirror.setVisible(true);
    }

/*    static public boolean fullScreen(final JFrame frame, boolean doPack) {

        GraphicsDevice device = frame.getGraphicsConfiguration().getDevice();
        boolean result = device.isFullScreenSupported();

        if (result) {
            frame.setUndecorated(true);
            frame.setResizable(true);

            frame.addFocusListener(new FocusListener() {

                @Override
                public void focusGained(FocusEvent arg0) {
                    frame.setAlwaysOnTop(true);
                }

                @Override
                public void focusLost(FocusEvent arg0) {
                    frame.setAlwaysOnTop(false);
                }
            });

            if (doPack)
                frame.pack();

            device.setFullScreenWindow(frame);
        }
        else {
            frame.setPreferredSize(frame.getGraphicsConfiguration().getBounds().getSize());

            if (doPack)
                frame.pack();

            frame.setResizable(true);

            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            boolean successful = frame.getExtendedState() == Frame.MAXIMIZED_BOTH;

            frame.setVisible(true);

            if (!successful)
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        }
        return result;
    }*/

}
