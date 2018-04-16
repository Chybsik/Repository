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

    double weight;

    public Circle() {
        double posX = 0;
        double posY = 0;
        double r = 0;

        double weight = 1;
    }

    public Circle(double r, Vertex v) {
        this.r = r;
        posX = v.posX;
        posY = v.posY;
        this.weight = 1;
    }

    public Circle(double r, double posX, double posY) {
        this.r = r;
        this.posX = posX;
        this.posY = posY;
        this.weight = 1;
    }

    public double CompareTo(Vector vector) {
        if (vector.getClass() == Circle.class) {
            Circle c = (Circle) vector;
            return Math.pow(0.1, Math.abs(posX - c.posX)) * Math.pow(0.1, Math.abs(posY - c.posY)) * Math.pow(0.1, Math.abs(r - c.r));
        } else if (vector.getClass() == Ellipse.class) {
            Ellipse e = (Ellipse) vector;
            return Math.pow(0.1, Math.abs(posX - e.posX)) * Math.pow(0.1, Math.abs(posY - e.posY)) * Math.pow(0.1, Math.abs(r - e.rx)) * Math.pow(0.1, Math.abs(r - e.ry));
        } else {
            return 0;
        }
    }
}
