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
public class Line {
    double posX1;
    double posY1;
    double posX2;
    double posY2;
    
    public Line(double posX1, double posY1, double posX2, double posY2){
        this.posX1 = posX1;
        this.posX2 = posX2;
        this.posY1 = posY1;
        this.posY2 = posY2;
    }
    public Line(Vertex v1, Vertex v2){
        this.posX1 = v1.posX;
        this.posX2 = v2.posX;
        this.posY1 = v1.posY;
        this.posY2 = v2.posY;
    }
}
