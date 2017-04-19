package cargame.collision;

import cargame.objects.GameObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleCollisionSystem implements Runnable {
    
    private static final int sleep=100;
    private GameObject vehicle;
    private Coordinate lastPosition;     
    private Coordinate currentPosition;
    private Coordinate u; 
    public double colisao_seg=0;   
     
    private double travelSpeed;

    public VehicleCollisionSystem(Coordinate currentPosition, GameObject go) {
        this.lastPosition = new Coordinate(currentPosition.getX(), currentPosition.getY(), currentPosition.getZ());
        this.currentPosition = new Coordinate(currentPosition.getX(), currentPosition.getY(), currentPosition.getZ());
        this.vehicle = go;
        this.u = new Coordinate(0, 0, 0);
    }
    
    @Override
    public void run() {
        int i;
        while(true){
            //if(i>2) cria tasks ou threads?????
            calculateTravelSpeed();    
            //System.out.println("travel speed:"+travelSpeed);
            for (GameObject go : this.vehicle.detectedVehicles) {
                detectCollision(go);
            }
             try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(VehicleCollisionSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void detectCollision(GameObject go) {
            u = Coordinate.subtract(currentPosition, lastPosition);     
            //<editor-fold defaultstate="collapsed" desc="Dados teste">
//            Coordinate w = new Coordinate(-183.37, 152.8, 0);              
//            Coordinate sub_u_v = Coordinate.subtract(new Coordinate(16.67, 0, 0),new Coordinate(0, 13.88, 0));
            //</editor-fold>
            Coordinate v = go.collisionSystem.getU();
            Coordinate q0 = go.collisionSystem.getLastPosition();         
            Coordinate w = Coordinate.subtract(lastPosition, q0);  
            
            Coordinate sub_u_v = Coordinate.subtract(u, v);
            
            double res = -w.getX()*sub_u_v.getX()-w.getY()*sub_u_v.getY();
            double div =  Math.pow(Math.sqrt((Math.pow(sub_u_v.getX(),2)+Math.pow(sub_u_v.getY(),2))),2);
            colisao_seg=res/(div*(1000/sleep));
    }

    //depois vai ficar privado e vai ser chamado pelo proprio sistema
    public double calculateTravelSpeed() {
        lastPosition = currentPosition;
        currentPosition = vehicle.currentCenterPosition;

        double distance;
        double quadrado_x;
        double quadrado_y;

        //fazer o x e y em processos separados
        quadrado_x = Math.pow(lastPosition.getX() - currentPosition.getX(), 2);
        quadrado_y = Math.pow(lastPosition.getY() - currentPosition.getY(), 2);
        distance = Math.sqrt(quadrado_x + quadrado_y);

        //antes de retornar altera valor de ultima coordenada
        travelSpeed = distance * 3.6;
        return travelSpeed;
    }

    private void parametricEquation() {
        Coordinate u = Coordinate.subtract(currentPosition, lastPosition);
    }

    public Coordinate getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(Coordinate lastPosition) {
        this.lastPosition = lastPosition;
    }

    public Coordinate getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Coordinate currentPosition) {
        this.currentPosition = currentPosition;
    }

    public double getTravelSpeed() {
        return travelSpeed;
    }

    public void setTravelSpeed(double travelSpeed) {
        this.travelSpeed = travelSpeed;
    }

    public GameObject getVehicle() {
        return vehicle;
    }

    public void setVehicle(GameObject vehicle) {
        this.vehicle = vehicle;
    }
    public Coordinate getU() {
        return u;
    }

    public void setU(Coordinate u) {
        this.u = u;
    }
}