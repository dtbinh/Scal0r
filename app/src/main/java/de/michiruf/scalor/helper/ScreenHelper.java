package de.michiruf.scalor.helper;

import javax.swing.JFrame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * @author Michael Ruf
 * @since 2016-03-24
 */
public class ScreenHelper {

    public static void showOnScreenFullscreen(int screen, JFrame frame) {
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        if (screen > -1 && screen < screens.length) {
            screens[screen].setFullScreenWindow(frame);
        } else if (screens.length > 0) {
            screens[0].setFullScreenWindow(frame);
        } else {
            throw new RuntimeException("No Screens Found");
        }
    }

    public static void showOnScreen(int screen, JFrame frame) {
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        if (screen > -1 && screen < screens.length) {
            frame.setLocation(screens[screen].getDefaultConfiguration().getBounds().x, frame.getY());
        } else if (screens.length > 0) {
            frame.setLocation(screens[0].getDefaultConfiguration().getBounds().x, frame.getY());
        } else {
            throw new RuntimeException("No Screens Found");
        }
    }
}
