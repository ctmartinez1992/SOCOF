package cargame.driving;

import cargame.objects.GameObject;

/**
 * Acceleration logic
 * 
 * @author Carlos
 */
public class Accelerator extends Thread {

    //Metric that controls the increase/decrease of acceleration
    private final int ACC_CONSTANT = 100;

    //Maximum velocity
    private static final int MAX_SPEED = 1;

    //Constant metric used to slow down
    private static final int BREAK_CONST = 2;

    //Current acceleration state
    public enum AccState {
        INC_SPEED,         //Manually increasing speed
        DEC_SPEED,         //Manually decreasing speed
        SLOW_DOWN,         //Automatically decreasing speed
        BREAK_DOWN,        //Hit the brakes
        STOPPED            //Car is stopped
    }

    private AccState state;
    private final GameObject obj;

    //Objects velocity
    private double speed;
    
    private long timeDiff;
    private long sleep;
    private long beforeTime;

    public Accelerator(GameObject obj) {
        this.obj = obj;
        this.state = AccState.STOPPED;
    }

    //Current speed
    public double getSpeed() {
        return this.speed;
    }

    public void setState(AccState state) {
        this.state = state;
    }

    private void incSpeed() {
        if (speed < MAX_SPEED) {
            speed++;
        }
    }

    private void decSpeed() {
        if (speed > -MAX_SPEED) {
            speed--;
        }
    }

    private void slowdown() {
        if (speed > 0) {
            speed--;
        } else if (speed < 0) {
            speed++;
        }
    }

    private void breakdown() {
        if (speed > 0) {
            speed -= BREAK_CONST;
            if (speed < 0) {
                speed = 0;
            }
        } else if (speed < 0) {
            speed += BREAK_CONST;
            if (speed > 0) {
                speed = 0;
            }
        }
    }

    public void instantStop() {
        speed = 0;
    }

    @Override
    public void run() {
        //FPS
        timeDiff = 0;
        sleep = 0;
        beforeTime = System.currentTimeMillis();

        //Process
        while (true) {
            switch (state) {
                case INC_SPEED:
                    incSpeed();
                    break;
                    
                case DEC_SPEED:
                    decSpeed();
                    break;
                    
                case SLOW_DOWN:
                    slowdown();
                    break;
                    
                case BREAK_DOWN:
                    breakdown();
                    break;
                    
                case STOPPED:
                    instantStop();
                    break;
                    
                default:
                    System.out.println("This is not possible!!!");
                    break;
            }
            
            calculateFPS();
        }
    }
    
    private void calculateFPS() {        
        timeDiff = System.currentTimeMillis() - beforeTime;
        sleep = ACC_CONSTANT - timeDiff;
        sleep = (sleep < 0) ? 2 : sleep;
        
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
        
        beforeTime = System.currentTimeMillis();
    }
}