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
public class Arc extends Vector{

    double rx = 0;
    double ry = 0;
    double xAxisRotation = 0;
    double largeArcDlag = 0;
    double sweepFlag = 0;
    double x = 0;
    double y = 0;

    public Arc() {
        this.rx = 0;
        this.ry = 0;
        this.xAxisRotation = 0;
        this.largeArcDlag = 0;
        this.sweepFlag = 0;
        this.x = 0;
        this.y = 0;
    }
    
    public Arc(double rx,double ry, double xAxisRotation, double largeArcDlag, double sweepFlag, double x, double y) {
        this.rx = rx;
        this.ry = ry;
        this.xAxisRotation = xAxisRotation;
        this.largeArcDlag = largeArcDlag;
        this.sweepFlag = sweepFlag;
        this.x = x;
        this.y = y;
    }
    
    public double CompareTo(Vector vector){
        if (vector.getClass() == Arc.class) {
            Arc a = (Arc)vector;
            return Math.pow(0.1, Math.abs(x-a.x))*Math.pow(0.1, Math.abs(y-a.y))*Math.pow(0.1, Math.abs(rx-a.rx))*Math.pow(0.1, Math.abs(ry-a.ry))*Math.pow(0.1, Math.abs(xAxisRotation-a.xAxisRotation));
        }else{
            return 0;
        }
    }
}
