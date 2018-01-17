/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vectorcomparator;

import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author user
 */
public class Polyline {
    List<Line> lines = new ArrayList<>();
    
    public Polyline(List<Line> lines){
        this.lines = lines;
    }
}
