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
        
        String p = "C:\\temp\\temp.svg";
        List<Vector> vector = new ArrayList<Vector>();
        vector = SVGDestructor.Destruct(p);
        
        String p2 = "C:\\temp\\temp2.svg";
        List<Vector> vector2 = new ArrayList<Vector>();
        vector2 = SVGDestructor.Destruct(p2);
    }
    
    public static double Compare(){
        double result = 0;
        
        return result;
    }
}
