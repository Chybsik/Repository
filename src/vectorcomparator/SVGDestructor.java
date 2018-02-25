/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vectorcomparator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Timur
 */
public class SVGDestructor {
    public static ArrayList Destruct(String path){
        ArrayList<Vector> list = new ArrayList<>();
        String s = "";
        Pattern p;
        Matcher m;
        
        try{
            s = new String(Files.readAllBytes(Paths.get(path)));
        }catch(IOException e){
            return list;
        }
        
        String pattern = "<[a-z][^\\/]*\\/>";
        p = Pattern.compile(pattern);
        m = p.matcher(s);
        
        while (m.find()){
            String str = m.group().replaceAll("\\s+", " ");                     //Убирает табулирование
            String[] words = str.split(" ");
            switch(words[0]){
                case "<path":break;
                case "<rect": list.add(RectDestruct(words));break;
                case "<circle": list.add(CircleDestruct(words));break;
                case "<ellipse": list.add(EllipseDestruct(words));break;
                case "<line": list.add(LineDestruct(words));break;
                case "<polyline": list.add(PolylineDestruct(words, false));break;
                case "<polygon": list.add(PolygonDestruct(words));break;
                default: break;
            }
        }
        return list;
        
    }
    public static Rect RectDestruct(String[] words){
        Rect rect = new Rect();
        for (String word : words) {
            if (word.matches("^x=\"\\d+\"$")) {
                rect.posX = Double.parseDouble(word.replaceAll("\\D+", ""));
            }else
            if (word.matches("^y=\"\\d+\"$")) {
                rect.posY = Double.parseDouble(word.replaceAll("\\D+", ""));
            }else
            if (word.matches("^width=\"\\d+\"$")) {
                rect.width = Double.parseDouble(word.replaceAll("\\D+", ""));
            }else
            if (word.matches("^height=\"\\d+\"$")) {
                rect.height = Double.parseDouble(word.replaceAll("\\D+", ""));
            }else
            if (word.matches("^rx=\"\\d+\"$")) {
                rect.rx = Double.parseDouble(word.replaceAll("\\D+", ""));
            }else
            if (word.matches("^ry=\"\\d+\"$")) {
                rect.ry = Double.parseDouble(word.replaceAll("\\D+", ""));
            }
        }
        return rect;
    }
    public static Circle CircleDestruct(String[] words){
        Circle circle = new Circle(0,0,0);
        for(int i = 1; i<words.length;i++){
            if(words[i].contains("cx")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                circle.posX = Double.parseDouble(m.group());
            }
            if(words[i].contains("cy")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                circle.posY = Double.parseDouble(m.group());
            }
            if(words[i].contains("r=")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                circle.r = Double.parseDouble(m.group());
            }
        }
        return circle;
    }
    public static Ellipse EllipseDestruct(String[] words){
        Ellipse ellipse = new Ellipse(0,0,0,0);
        for(int i = 1; i<words.length;i++){
            if(words[i].contains("cx")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                ellipse.posX = Double.parseDouble(m.group());
            }
            if(words[i].contains("cy")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                ellipse.posY = Double.parseDouble(m.group());
            }
            if(words[i].contains("rx=")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                ellipse.rx = Double.parseDouble(m.group());
            }
            if(words[i].contains("ry=")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                ellipse.ry = Double.parseDouble(m.group());
            }
        }
        return ellipse;
    }
    public static Line LineDestruct(String[] words){
        Line line = new Line(0,0,0,0);
        for(int i=1;i<words.length;i++){
            if(words[i].contains("x1")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                line.posX1 = Double.parseDouble(m.group());
            }
            if(words[i].contains("x2")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                line.posX2 = Double.parseDouble(m.group());
            }
            if(words[i].contains("y1")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                line.posY1 = Double.parseDouble(m.group());
            }
            if(words[i].contains("y2")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                line.posY2 = Double.parseDouble(m.group());
            }
        }
        return line;
    }
    public static Polyline PolylineDestruct(String[] words, boolean returnPolygon){
        List<Line> lines = new ArrayList<>();
        List<Vertex> vertices = new ArrayList<>();
        for(int i = 1; i<words.length;i++){
            if(words[i].contains(",")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                Double x = Double.parseDouble(m.group());
                Double y = Double.parseDouble(m.group());
                vertices.add(new Vertex(x,y));
            }
        }
        for(int i = 1; i<vertices.size();i++){
            lines.add(new Line(vertices.get(i-1),vertices.get(i)));
        }
        if(returnPolygon){
            lines.add(new Line(vertices.get(vertices.size()-1),vertices.get(0)));
        }
        Polyline polyline = new Polyline(lines);
        return polyline;
    }
    public static Polygon PolygonDestruct(String[] words){
        return new Polygon(PolylineDestruct(words, true).lines);
    }
}
