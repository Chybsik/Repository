package vectorcomparator;

/**
 *
 * @author user
 */
public class Ellipse extends Vector {

    double posX;
    double posY;
    double rx;
    double ry;

    double weight;

    public Ellipse() {
        this.rx = 0;
        this.ry = 0;
        posX = 0;
        posY = 0;
        this.weight = 1;
    }

    public Ellipse(double rx, double ry, Vertex v) {
        this.rx = rx;
        this.ry = ry;
        posX = v.posX;
        posY = v.posY;
        this.weight = 1;
    }

    public Ellipse(double rx, double ry, double posX, double posY) {
        this.rx = rx;
        this.ry = ry;
        this.posX = posX;
        this.posY = posY;
        this.weight = 1;
    }

    public double CompareTo(Vector vector) {
        if (vector.getClass() == Ellipse.class) {
            Ellipse e = (Ellipse) vector;
            return Math.pow(0.1, Math.abs(posX - e.posX)) * Math.pow(0.1, Math.abs(posY - e.posY)) * Math.pow(0.1, Math.abs(ry - e.ry)) * Math.pow(0.1, Math.abs(rx - e.rx));
        } else if (vector.getClass() == Circle.class) {
            Circle c = (Circle) vector;
            return Math.pow(0.1, Math.abs(posX - c.posX)) * Math.pow(0.1, Math.abs(posY - c.posY)) * Math.pow(0.1, Math.abs(ry - c.r)) * Math.pow(0.1, Math.abs(rx - c.r));
        } else {
            return 0;
        }
    }
}
