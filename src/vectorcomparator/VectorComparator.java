package vectorcomparator;
import java.io.*;
import static java.lang.System.out;
import java.nio.file.*;
import java.util.regex.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Timur
 */
public class VectorComparator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //String p = "C:\\temp\\santa-claus.svg";
        //List<Vector> vector = new ArrayList<Vector>();
        //vector = SVGDestructor.Destruct(p);
        Line line1 = new Line(0,0,20,35);
        Line line2 = new Line(10,20, 40,0);
        System.out.print(LineCompare(line1,line2));
    }
    public static double LineCompare(Line line1, Line line2){
        double biss1 = Math.sqrt(Math.pow(line1.posX1-line1.posX2, 2)+Math.pow(line1.posY1-line1.posY2, 2));
        double sin1 = (line1.posY1-line1.posY2)/biss1;
        double cos1 = (line1.posX1-line1.posX2)/biss1;
        
        double biss2 = Math.sqrt(Math.pow(line2.posX1-line2.posX2, 2)+Math.pow(line2.posY1-line2.posY2, 2));
        double sin2 = (line2.posY1-line2.posY2)/biss2;
        double cos2 = (line2.posX1-line2.posX2)/biss2;
        
        double x = 1 - Math.abs(sin1-sin2 + cos1-cos2 + line1.posX1-line2.posX1 + line1.posY1-line2.posY1)/4;
        return (x<0)? 0:x;
    }
}
