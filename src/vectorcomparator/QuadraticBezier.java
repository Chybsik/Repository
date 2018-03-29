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
public class QuadraticBezier {
    Vertex sp;
    Vertex ep;
    Vertex cp;
    
    public QuadraticBezier(Vertex startPoint, Vertex endPoint, Vertex controlPoint){
        this.cp = controlPoint;
        this.ep = endPoint;
        this.sp = startPoint;
    }
}
