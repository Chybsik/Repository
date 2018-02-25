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
public class Line extends Vector{
    double posX1;
    double posY1;
    double posX2;
    double posY2;
    double length;
    
    double weight=1;
    double angle;
    
    public Line(double posX1, double posY1, double posX2, double posY2){
        this.posX1 = posX1;
        this.posX2 = posX2;
        this.posY1 = posY1;
        this.posY2 = posY2;
        
        length = Math.pow(posX1-posX2, 2)+Math.pow(posY1-posY2, 2);
        
        angle = Math.acos((posX2-posX1)/length)*180;
    }
    public Line(Vertex v1, Vertex v2){
        this.posX1 = v1.posX;
        this.posX2 = v2.posX;
        this.posY1 = v1.posY;
        this.posY2 = v2.posY;
        
        length = Math.sqrt(Math.pow(posX1-posX2, 2)+Math.pow(posY1-posY2, 2));
        angle = Math.acos((posX2-posX1)/length)*180;
    }
    public double CompareTo(Line line){
        return Math.pow(0.1, Math.abs(angle-line.angle))*Math.pow(1, Math.abs(length-line.length))*Math.pow(0.1, Math.abs(posX1 - posX2))*Math.pow(0.1, Math.abs(posY1 - posY2));
    }
}
