package cargame.gui;

import cargame.objects.ObjectList;

/**
 * Class for updating the game
 *
 * @author Carlos
 */
public class Updater extends Thread {

    //Updates upon this metric
    private final int DELAY = 25;

    ObjectList objHolder;
    GameCanvas canvas;
    
    long timeDiff;
    long sleep;
    long beforeTime;
    
    public Updater(ObjectList objHolder, GameCanvas canvas) {
        this.objHolder = objHolder;
        this.canvas = canvas;
    }

    //Updates and repaints
    private void update() {
        objHolder.update();
        canvas.repaint();
    }
    
    @Override
    public void run() {
        //FPS control
        timeDiff = 0;
        sleep = 0;
        beforeTime = System.currentTimeMillis();
        while (true) {
            update();
            calculateFPS();
        }
    }
    
    private void calculateFPS() {        
        timeDiff = System.currentTimeMillis() - beforeTime;
        sleep = DELAY - timeDiff;
        sleep = (sleep < 0) ? 2 : sleep;
        
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
        
        beforeTime = System.currentTimeMillis();
    }
}