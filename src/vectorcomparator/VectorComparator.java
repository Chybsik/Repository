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
        /*
        Vertex v1 = new Vertex(1,1);
        Vertex v2 = v1;
        v2.posX=2;
        System.out.println(v1.posX);
        
        Pattern p = Pattern.compile("\\-?\\d+(\\.\\d+)?");
        Matcher m = p.matcher("c-2.4-7.6-5.2-14.4-8.8-20.8");
        while (m.find()) {
            System.out.println(m.group());
        }
        */
        MainForm form = new MainForm();
        form.setVisible(true);
    }
    
    public static double Compare(String p, String p2){
        List<Vector> vector = new ArrayList<Vector>();
        vector = SVGDestructor.Destruct(p);
        
        List<Vector> vector2 = new ArrayList<Vector>();
        vector2 = SVGDestructor.Destruct(p2);
        
        for (int i = 0; i < vector.size(); i++) {
            Vector temp = vector.get(i);
            for (int j = i+1; j < vector.size(); j++) {
                temp.weight*=1-temp.CompareTo(vector.get(j));
            }
            vector.set(i, temp);
        }
        
        for (int i = 0; i < vector2.size(); i++) {
            Vector temp = vector2.get(i);
            for (int j = i+1; j < vector2.size(); j++) {
                temp.weight*=1-temp.CompareTo(vector2.get(j));
            }
            vector2.set(i, temp);
        }
        
        double result = 0;
        for (int i = 0; i < vector.size(); i++) {
            for (int j = 0; j < vector2.size(); j++) {
                result += vector.get(i).CompareTo(vector2.get(j))*vector.get(i).weight*vector2.get(j).weight;
            }
        }
        return vector2.isEmpty() ? 0:result/vector2.size();
    }
}