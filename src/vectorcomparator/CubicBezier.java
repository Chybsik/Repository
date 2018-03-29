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
public class CubicBezier {
    Vertex sp;
    Vertex ep;
    Vertex cp1;
    Vertex cp2;
    
    public CubicBezier(Vertex startPoint, Vertex endPoint, Vertex controlPoint1,Vertex controlPoint2){
        this.sp = startPoint;
        this.ep = endPoint;
        this.cp1 = controlPoint1;
        this.cp2 = controlPoint2;
    }
}
