package cargame.gui.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * Utility Methods
 * 
 * @author Carlos
 */
public class Util {

    //Centers the window to the screen
    public static void centerScreen(JFrame window) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((dim.width - window.getSize().width) / 2,
                           (dim.height - window.getSize().height) / 2);
    }
}