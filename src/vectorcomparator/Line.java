package vectorcomparator;

/**
 *
 * @author user
 */
public class Line extends Vector {

    private double posX1;
    private double posY1;
    private double posX2;
    private double posY2;

    private double weight;

    public Line(){
        this.posX1 = 0;
        this.posX2 = 0;
        this.posY1 = 0;
        this.posY2 = 0;

        this.weight = 1;
    }
    
    public Line(double posX1, double posY1, double posX2, double posY2) {
        this.posX1 = posX1;
        this.posX2 = posX2;
        this.posY1 = posY1;
        this.posY2 = posY2;

        this.weight = 1;
    }

    public Line(Vertex v1, Vertex v2) {
        this.posX1 = v1.posX;
        this.posX2 = v2.posX;
        this.posY1 = v1.posY;
        this.posY2 = v2.posY;
        this.weight = 1;
    }
    
    public void SetX1(double posX1){
        this.posX1 = posX1;
    }
    public void SetX2(double posX2){
        this.posX2 = posX2;
    }
    public void SetY1(double posY1){
        this.posY1 = posY1;
    }
    public void SetY2(double posY2){
        this.posY2 = posY2;
    }
    
    public double CompareTo(Vector vector) {
        if (vector.getClass() == Line.class) {
            Line l = (Line) vector;
            return Math.pow(0.1, Math.abs(posX1 - l.posX1)) * Math.pow(0.1, Math.abs(posY1 - l.posY1))* Math.pow(0.1, Math.abs(posX2 - l.posX2)) * Math.pow(0.1, Math.abs(posY2 - l.posY2));
        } else {
            return 0;
        }
    }
}
