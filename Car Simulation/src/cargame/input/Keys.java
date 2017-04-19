package cargame.input;

import java.awt.event.KeyEvent;
import java.util.HashSet;

/**
 * HashSet with pressed keys
 * 
 * @author Carlos
 * 
 */
public class Keys extends HashSet<Integer> {

    private static final long serialVersionUID = 1L;

    //The class holds its own instance
    private static final Keys keys = new Keys();

    //So the constructor is made private
    private Keys() {
    }

    public static Keys getInstance() {
        return keys;
    }

    //Was left arrow pressed?
    public static boolean left() {
        return keys.contains(KeyEvent.VK_LEFT);
    }

    //Was right arrow pressed?
    public static boolean right() {
        return keys.contains(KeyEvent.VK_RIGHT);
    }

    //Was up arrow pressed?
    public static boolean up() {
        return keys.contains(KeyEvent.VK_UP);
    }

    //Was down arrow pressed?
    public static boolean down() {
        return keys.contains(KeyEvent.VK_DOWN);
    }

    //Was enter key pressed?
    public static boolean enter() {
        return keys.contains(KeyEvent.VK_ENTER);
    }

    //Was space key pressed?
    public static boolean space() {
        return keys.contains(KeyEvent.VK_SPACE);
    }

    //Was r key pressed?
    public static boolean r() {
        return keys.contains(KeyEvent.VK_R);
    }
}