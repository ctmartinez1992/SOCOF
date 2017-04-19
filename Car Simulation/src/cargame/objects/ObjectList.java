package cargame.objects;

import cargame.collision.Coordinate;
import cargame.gui.Paintable;
import java.awt.Font;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds objects
 * 
 * @author Carlos
 */
public class ObjectList implements Paintable {

    private static final long serialVersionUID = 1L;
    public static String state = "";
    
    private List<GameObject> list;

    public ObjectList() {
        this.list = new ArrayList();
        createObjects();
    }

    @Override
    public void paint(Graphics2D g2) {
        for (GameObject obj : this.list) {
            obj.paint(g2);
        }
        
        g2.setFont(new Font("Arial", 15, 15));
        g2.drawString("State of the Simulation: " + this.state, 10, 600 - 50);
    }

    public void update() {
        for (GameObject obj : this.list) {
            if(obj instanceof SimulationCar) {                
                ((SimulationCar) obj).steer();
            }
            if(obj instanceof Car) {
                ((Car) obj).steer();
            }
        }
    }

    private void createObjects() {
        //list.add(new Car("car_blue.png", new Coordinate(400, 300, 0), 75, 150, 0, 10, 20));
        //list.add(new Car("car_red.png", new Coordinate(100, 300, 0), 75, 150, 0, 10, 40));
        GameObject blueCar = new Car("Blue01", "car_blue.png", new Coordinate(700, 300, 0), 75, 150, 0, 10, 20);
        GameObject redCar = new Car("Red01", "car_red.png", new Coordinate(100, 300, 0), 75, 150, 180, 10, 40);
        blueCar.detectedVehicles.add(redCar);
        redCar.detectedVehicles.add(blueCar);
        list.add(blueCar);
        list.add(redCar);
    }
}