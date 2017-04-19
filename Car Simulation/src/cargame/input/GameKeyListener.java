package cargame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Key Handler
 * 
 * @author Carlos
 */
public class GameKeyListener implements KeyListener {

    Keys keylist;

    public GameKeyListener() {
        this.keylist = Keys.getInstance();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keylist.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keylist.remove(e.getKeyCode());
    }
}