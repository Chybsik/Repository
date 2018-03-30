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
        MainForm form = new MainForm();
        form.setVisible(true);
    }
    
    public static double Compare(String p, String p2){
        List<Vector> vector = new ArrayList<Vector>();
        vector = SVGDestructor.Destruct(p);
        
        List<Vector> vector2 = new ArrayList<Vector>();
        vector2 = SVGDestructor.Destruct(p2);
        
        double result = 0;
        for (int i = 0; i < vector.size(); i++) {
            for (int j = 0; j < vector2.size(); j++) {
                result += vector.get(i).CompareTo(vector2.get(j));
            }
        }
        return vector.size()==0 ? 0:result/vector.size();
    }
}