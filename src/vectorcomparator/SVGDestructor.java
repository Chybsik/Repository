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
        String s = "";
        Pattern p;
        Matcher m;
        String path_pattern;
        
        try{
            s = new String(Files.readAllBytes(Paths.get(path)));
        }catch(IOException e){
        }
        
        String pattern = "<[^/]*/>";
        p = Pattern.compile(pattern);
        m = p.matcher(s);
        
        List<Vector> list = new ArrayList<Vector>();
        
        while (m.find()){
            String[] words = m.group().split(" ");
            switch(words[0]){
                case "path":;
                case "rect": list.add(RectDestruct(words));
                case "circle":;
                case "ellipse":;
                case "line":;
                case "polyline":;
                case "polygon":;
            }
        }
        return (ArrayList)list;
    }
    public static Rect RectDestruct(String[] words){
        Rect rect = new Rect();
        for(int i=1; i<words.length; i++){
            if(words[i].contains("x")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                rect.posX = Double.parseDouble(m.group());
            }
            if(words[i].contains("y")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                rect.posY = Double.parseDouble(m.group());
            }
            if(words[i].contains("width")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                rect.width = Double.parseDouble(m.group());
            }
            if(words[i].contains("height")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                rect.height = Double.parseDouble(m.group());
            }
            if(words[i].contains("rx")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                rect.rx = Double.parseDouble(m.group());
            }
            if(words[i].contains("ry")){
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                rect.ry = Double.parseDouble(m.group());
            }
        }
        return rect;
    }
    public static Circle CircleDestruct(String[] words){
        Circle circle = new Circle();
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
        Ellipse ellipse = new Ellipse();
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
