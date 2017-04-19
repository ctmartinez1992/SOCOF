package cargame.driving;

import cargame.input.Keys;
import cargame.collision.Coordinate;
import cargame.objects.GameObject;
import cargame.driving.Accelerator.AccState;
import cargame.objects.ObjectList;

import java.awt.Component;

/**
 * Steer an object (Implementation)
 * 
 * @author Carlos
 */
public class Steer extends Component {

    private static final long serialVersionUID = 1L;

    GameObject obj;
    Accelerator accelerator;

    //Last key pressed
    int lastKey;

    public Steer(GameObject obj) {
        this.obj = obj;
        
        accelerator = new Accelerator(obj);
        accelerator.start();
        
        ObjectList.state = "Game Mode!";
    }

    public void steer() {
        move();
        slowDown();
        setAngle();
        performMove();
    }

    //Move based on keys pressed
    private void move() {
        if (Keys.up()) {
            if (accelerator.getSpeed() < 0) {
                accelerator.setState(AccState.BREAK_DOWN);
            } else {
                accelerator.setState(AccState.INC_SPEED);
            }
            
            lastKey = 1;
        }

        if (Keys.down()) {
            if (accelerator.getSpeed() > 0) {
                accelerator.setState(AccState.BREAK_DOWN);
            } else {
                accelerator.setState(AccState.DEC_SPEED);
            }
            
            lastKey = 2;
        }
    }

    //Naturally slow down the object
    private void slowDown() {
        if (!Keys.up() && !Keys.down()) {
            accelerator.setState(AccState.SLOW_DOWN);
        }
    }

    //Properly angle the object
    private void setAngle() {
        if ((accelerator.getSpeed() != 0) && Keys.left()) {
            obj.angle += -accelerator.getSpeed() / 3;
        }

        if ((accelerator.getSpeed() != 0) && Keys.right()) {
            obj.angle += accelerator.getSpeed() / 3;
        }

        if (obj.angle > 360) {
            obj.angle = 0;
        } else if (obj.angle < 0) {
            obj.angle = 360;
        }

        obj.angle = Math.abs(obj.angle);
    }

    /**
     * Move calculation of the vehicle
     */
    private void performMove() {
        if (lastKey == 1 || lastKey == 2) {
            double cx = accelerator.getSpeed() * Math.abs(Math.sin(Math.toRadians(obj.angle)));
            double cy = accelerator.getSpeed() * Math.abs(Math.cos(Math.toRadians(obj.angle)));

            // Move north-west
            if ((obj.angle > 270 && obj.angle < 360) || obj.angle == 0) {
                obj.x += -cx;
                obj.y += -cy;
            }

            // Move north-east
            if ((obj.angle > 0 && obj.angle < 90)) {
                obj.x += cx;
                obj.y += -cy;
            }

            // Move south-west
            if ((obj.angle > 180 && obj.angle < 270)) {
                obj.x += -cx;
                obj.y += cy;
            }

            // Move south-east
            if ((obj.angle > 90 && obj.angle < 180)) {
                obj.x += cx;
                obj.y += cy;
            }
            
            obj.currentPosition = (new Coordinate(obj.x, obj.y, 0));
        }
    }
}