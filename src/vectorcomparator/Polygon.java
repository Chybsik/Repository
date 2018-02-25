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
 * @author user
 */
public class Polygon extends Vector{
    List<Line> lines = new ArrayList<>();
    
    public Polygon(List<Line> lines){
        this.lines = lines;
    }
}
