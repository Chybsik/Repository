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
public class Vertex {
    double posX;
    double posY;
    
    public Vertex(double posX, double posY){
        this.posX = posX;
        this.posY = posY;
    }
    
    public void Translate(double shiftX, double shiftY){
        this.posX+=shiftX;
        this.posY+=shiftY;
    }
    
    public double CompareTo(Vertex v){
        return Math.pow(0.1, Math.abs(posX - v.posX));
    }
}
