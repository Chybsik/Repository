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
    
    double weight =1;
    
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
    public double CompareTo(Vector vector){
        if (vector.getClass() == Ellipse.class) {
            Ellipse e = (Ellipse)vector;
            return Math.pow(0.1, Math.abs(posX-e.posX))*Math.pow(0.1, Math.abs(posY-e.posY))*Math.pow(0.1, Math.abs(ry-e.ry))*Math.pow(0.1, Math.abs(rx-e.rx));
        }else if(vector.getClass() == Circle.class){
            Circle c = (Circle)vector;
            return Math.pow(0.1, Math.abs(posX-c.posX))*Math.pow(0.1, Math.abs(posY-c.posY))*Math.pow(0.1, Math.abs(ry-c.r))*Math.pow(0.1, Math.abs(rx-c.r));
        }else{
            return 0;
        }
    }
}
