package vectorcomparator;

/**
 *
 * @author user
 */
public class CubicBezier extends Vector {

    Vertex sp;
    Vertex ep;
    Vertex cp1;
    Vertex cp2;

    double weight;

    public CubicBezier(Vertex startPoint, Vertex endPoint, Vertex controlPoint1, Vertex controlPoint2) {
        this.sp = new Vertex(startPoint);
        this.ep = new Vertex(endPoint);
        this.cp1 = new Vertex(controlPoint1);
        this.cp2 = new Vertex(controlPoint2);
        this.weight = 1;
    }

    public double CompareTo(Vector vector) {
        if (vector.getClass() == CubicBezier.class) {
            CubicBezier curve = (CubicBezier) vector;
            return sp.CompareTo(curve.sp) * ep.CompareTo(curve.ep) * cp1.CompareTo(curve.cp1) * cp2.CompareTo(curve.cp2);
        } else {
            return 0;
        }

    }
}
