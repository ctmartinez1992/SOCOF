package cargame.objects;

import cargame.collision.Coordinate;
import cargame.collision.VehicleCollisionSystem;
import cargame.gui.Paintable;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * GameObject is an entity in the game universe
 * 
 * @author Carlos
 */
public class GameObject extends Rectangle2D.Double implements Paintable {

    private static final long serialVersionUID = 1L;

    private Image texture;
    private AffineTransform objectTransform;
    private AffineTransform objectDefaultTransform;
    
    public List<GameObject> detectedVehicles;
    
    public VehicleCollisionSystem collisionSystem;
    public GameObjectStoredValues originalValues;
    public Coordinate currentPosition;
    public Coordinate currentCenterPosition;
    
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
    public GameObject(String name, Coordinate currentPosition, double width, double height, double angle, int debugX, int debugY) {
        this.name = name;
        this.x = currentPosition.getX();
        this.y = currentPosition.getY();
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.debugX = debugX;
        this.debugY = debugY;
        this.currentPosition = currentPosition;
        this.currentCenterPosition = new Coordinate(0, 0, 0);
             
        this.detectedVehicles = new ArrayList();
        this.collisionSystem = new VehicleCollisionSystem(currentCenterPosition, this); 
        new Thread(this.collisionSystem).start();
        
        this.objectTransform = new AffineTransform();
        this.objectDefaultTransform = new AffineTransform();
        
        this.originalValues = new GameObjectStoredValues(name, currentPosition, width, height, angle, debugX, debugY);
    }

    //Set texture for the object
    public void setTexture(String path) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        URL imgRes = getClass().getResource(path);
        texture = toolkit.getImage(imgRes);

        ImageFilter filter = new RGBImageFilter() {            
            public int markerRGB = 0xFFFFFFFF;

            @Override
            public int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };
        
        ImageProducer ip = new FilteredImageSource(texture.getSource(), filter);
        texture = toolkit.createImage(ip);
    }

    @Override
    public void paint(Graphics2D g2) {
        objectTransform = AffineTransform.getRotateInstance(Math.toRadians(angle), this.x, this.y);
        objectDefaultTransform = (AffineTransform) g2.getTransform().clone();

        g2.setTransform(objectTransform);
            g2.drawImage(texture, (int) x, (int) y, (int) width, (int) height, null); 
            
            Ellipse2D.Double hole = new Ellipse2D.Double();
            hole.width = 10;
            hole.height = 10;
            hole.x = getCenterX();
            hole.y = getCenterY();
            g2.draw(hole);
            
            currentCenterPosition.setX(getCenterX());
            currentCenterPosition.setY(getCenterY());
        g2.setTransform(objectDefaultTransform);

        //System.out.println("[" + this.name + "] - velocidade(KM/h): " + this.detectedVehicles.get(0).collisionSystem.calculateTravelSpeed());
        debug(g2);
    }
    
    public void reset() {
        this.name = this.originalValues.name;
        this.x = this.originalValues.x;
        this.y = this.originalValues.y;
        this.width = this.originalValues.width;
        this.height = this.originalValues.height;
        this.angle = this.originalValues.angle;
        this.debugX = this.originalValues.debugX;
        this.debugY = this.originalValues.debugY;
        this.currentPosition = new Coordinate(this.x, this.y, 0);
    }
    
    //Debug object
    private void debug(Graphics2D g2) {
        String sx = String.format("%.1f", currentCenterPosition.getX());
        String sy = String.format("%.1f", currentCenterPosition.getY());
        String sa = String.format("%.1f", this.angle);        
        String v = String.format("%.1f", this.collisionSystem.getTravelSpeed());
        String t = String.format("%.1f", this.collisionSystem.colisao_seg);
        g2.setFont(new Font("Courier New", 15, 15));
        g2.drawString("X: " + sx + " - Y: " + sy + " - Angle: " + sa + " - Vel(Km/h): " + v + " - Colisao(seg): " + t, this.debugX, this.debugY);
    }
}