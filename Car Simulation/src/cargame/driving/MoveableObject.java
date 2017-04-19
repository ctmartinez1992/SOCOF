/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cargame.driving;

/**
 *
 * @author carlos
 */
public interface MoveableObject {
    public void manageSimulation();
    public void setAngle();
    public void performMove();
}
