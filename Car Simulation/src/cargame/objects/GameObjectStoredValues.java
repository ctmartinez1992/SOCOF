package cargame.objects;

import cargame.collision.Coordinate;

import java.awt.geom.Rectangle2D;

/**
 * GameObject is an entity in the game universe
 * 
 * @author Carlos
 */
public class GameObjectStoredValues extends Rectangle2D.Double {

    private static final long serialVersionUID = 1L;
    
    public Coordinate currentPosition;
    
    public String name;
    
    public double angle;
    
    public int debugX;
    public int debugY;

    /**
     * @param name - Uniquely identifies the object (Preferably unique)
     * @param currentPosition - Position of the car
     * @param width - width of the car
     * @param height - height of the car
     * @param angle - angle of the car
     * @param debugX - X position of the debug
     * @param debugY - Y position of the debug
     */
    public GameObjectStoredValues(String name, Coordinate currentPosition, double width, double height, double angle, int debugX, int debugY) {
        this.name = name;
        this.x = currentPosition.getX();
        this.y = currentPosition.getY();
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.debugX = debugX;
        this.debugY = debugY;
        this.currentPosition = currentPosition;
    }
}