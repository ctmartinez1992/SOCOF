package cargame.main;

import cargame.gui.GameWindow;
import javax.swing.SwingUtilities;

/**
 * Main class
 * 
 * @author Carlos
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameWindow gameWindow = new GameWindow();
            }
        });
    }
}