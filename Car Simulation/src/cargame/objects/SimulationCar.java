package cargame.objects;

import cargame.collision.Coordinate;
import cargame.driving.SteerForwardSimulation;

/**
 * @author Carlos
 */
public class SimulationCar extends GameObject {

    private static final long serialVersionUID = 1L;

    private SteerForwardSimulation steering;
    
    /**
     * @param name - Uniquely identifies the object (Preferably unique)
     * @param image - the path to the image that represents the car
     * @param currentPosition - Position of the car
     * @param width - width of the car
     * @param height - height of the car
     * @param angle - angle of the car
     * @param debugX - X position of the debug
     * @param debugY - Y position of the debug
     */
    public SimulationCar(String name, String image, Coordinate currentPosition, double width, double height, double angle, int debugX, int debugY) {
        super(name, currentPosition, width, height, angle, debugX, debugY);
        setTexture("/cargame/resources/" + image);
        steering = new SteerForwardSimulation(this);        
    }

    public void steer() {
        steering.steer();
    }
}