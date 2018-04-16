/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vectorcomparator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Timur
 */
public class Util {
    public static double Compare(List<Vector> vector, List<Vector> vector2) {

        for (int i = 0; i < vector.size(); i++) {
            Vector temp = vector.get(i);
            temp.weight = 1;
            for (int j = i + 1; j < vector.size(); j++) {
                temp.weight *= 1 - temp.CompareTo(vector.get(j));
            }
            vector.set(i, temp);
        }

        for (int i = 0; i < vector2.size(); i++) {
            Vector temp = vector2.get(i);
            temp.weight = 1;
            for (int j = i + 1; j < vector2.size(); j++) {
                temp.weight *= 1 - temp.CompareTo(vector2.get(j));
            }
            vector2.set(i, temp);
        }

        double result = 0;
        for (int i = 0; i < vector.size(); i++) {
            for (int j = 0; j < vector2.size(); j++) {
                result += vector.get(i).CompareTo(vector2.get(j)) * vector.get(i).weight * vector2.get(j).weight;
            }
        }
        return vector2.isEmpty() ? 0 : result / vector2.size();
    }

    public static ArrayList<Vector> Verify(String p) {
        List<Vector> vector = new ArrayList<Vector>();
        return SVGDestructor.Destruct(p);
    }
}
