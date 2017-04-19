package cargame.driving;

import cargame.collision.Coordinate;
import cargame.input.Keys;
import cargame.objects.GameObject;
import cargame.driving.Accelerator.AccState;
import cargame.objects.ObjectList;

import java.awt.Component;

/**
 * Simulates a car moving forward
 *
 * @author carlos
 */
public class SteerForwardSimulation extends Component implements MoveableObject {

    private static final long serialVersionUID = 1L;

    private final GameObject obj;
    private final Accelerator accelerator;
    
    //Has the simulation started
    private boolean simulation;
            
    public SteerForwardSimulation(GameObject obj) {
        this.obj = obj;
        
        accelerator = new Accelerator(obj);
        accelerator.start();
        
        ObjectList.state = "Stopped (Enter to Start)";
        this.simulation = false;
    }

    public void steer() {
        manageSimulation();
        setAngle();
        performMove();
    }

    //Move based on keys pressed
    @Override
    public void manageSimulation() {
        //Start the simulation
        if (Keys.enter() && !this.simulation) {
            ObjectList.state = "Running (Space to Pause; R to Restart)";
            this.simulation = true;
            if (accelerator.getSpeed() < 0) {
                accelerator.setState(AccState.BREAK_DOWN);
            } else {
                accelerator.setState(AccState.INC_SPEED);
            }
        }

        //Pause the simulation
        if (Keys.space() && this.simulation) {
            ObjectList.state = "Paused (Enter to Run; R to Restart)";
            this.simulation = false;
            if (accelerator.getSpeed() > 0) {
                accelerator.setState(AccState.BREAK_DOWN);
            } else {
                accelerator.setState(AccState.DEC_SPEED);
            }
        }

        //Restart the simulation
        if (Keys.r()) {
            ObjectList.state = "Restarted & Stopped (Enter to Run)";
            this.simulation = false;
            this.obj.reset();
            accelerator.setState(AccState.STOPPED);
            accelerator.instantStop();
        }
    }

    //Properly angle the object
    @Override
    public void setAngle() {
        if ((accelerator.getSpeed() != 0) && Keys.left()) {
            obj.angle += -accelerator.getSpeed() / 4;
        }

        if ((accelerator.getSpeed() != 0) && Keys.right()) {
            obj.angle += accelerator.getSpeed() / 4;
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
    @Override
    public void performMove() {
        if (this.simulation) {
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
            
            obj.currentPosition = new Coordinate(obj.x, obj.y, 0);
        }
    }
}