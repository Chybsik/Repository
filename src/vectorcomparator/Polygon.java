package vectorcomparator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class Polygon{
    List<Line> lines = new ArrayList<>();
    
    public Polygon(List<Line> lines){
        this.lines = lines;
    }
}
