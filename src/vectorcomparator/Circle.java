/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vectorcomparator;

/**
 *
 * @author user
 */
public class Circle extends Vector {
    double posX;
    double posY;
    double r;
    public Circle (double r, Vertex v){
        this.r = r;
        posX = v.posX;
        posY = v.posY;
    }
    public Circle (double r, double posX, double posY){
        this.r = r;
        this.posX = posX;
        this.posY = posY;
    }
    public double CompareTo(Circle circle){
        return Math.pow(1, Math.abs(posX-circle.posX))*Math.pow(1, Math.abs(posY-circle.posY))*Math.pow(1, Math.abs(r-circle.r));
    }
}
