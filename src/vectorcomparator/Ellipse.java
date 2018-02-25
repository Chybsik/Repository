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
public class Ellipse extends Vector{
    double posX;
    double posY;
    double rx;
    double ry;
    
    public Ellipse (double rx, double ry, Vertex v){
        this.rx = rx;
        this.ry = ry;
        posX = v.posX;
        posY = v.posY;
    }
    public Ellipse (double rx, double ry, double posX, double posY){
        this.rx = rx;
        this.ry = ry;
        this.posX = posX;
        this.posY = posY;
    }
    public double CompareTo(Ellipse ellipse){
        return Math.pow(1, Math.abs(posX-ellipse.posX))*Math.pow(1, Math.abs(posY-ellipse.posY))*Math.pow(1, Math.abs(ry-ellipse.ry))*Math.pow(1, Math.abs(rx-ellipse.rx));
    }
}
