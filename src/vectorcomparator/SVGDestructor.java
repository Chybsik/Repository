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
        
        String pattern = "<[a-z][^\\/>]+\\/>";
        p = Pattern.compile(pattern);
        m = p.matcher(s);
        
        /*
        ArrayList<String> l = new ArrayList<>();
        while(m.find()){
            l.add(m.group());
        }
        */
        
        while (m.find()){
            String str = m.group().replaceAll("\\s+", " ");                     //Убирает табулирование
            String[] words = str.split(" ");
            switch(words[0]){
                case "<path":break;
                case "<rect": list.addAll(RectDestruct(words));break;
                case "<circle": list.add(CircleDestruct(words));break;
                case "<ellipse": list.add(EllipseDestruct(words));break;
                case "<line": list.add(LineDestruct(words));break;
                case "<polyline": list.addAll(PolylineDestruct(words, false));break;
                case "<polygon": list.addAll(PolygonDestruct(words));break;
                default: break;
            }
        }
        
        for (int i = 0; i < list.size()-1; i++) {
            Vector temp = list.get(i);
            for (int j = i+1; j < list.size(); j++) {
                Vector temp2 = list.get(j);
                temp.weight *= 1-temp.CompareTo(temp2);
                list.set(i, temp);
            }
        }
        
        return list;
        
    }
    public static ArrayList<Vector> RectDestruct(String[] words){
        ArrayList<Vector> list = new ArrayList<>();
        
        Rect rect = new Rect();
        for (String word : words) {
            if (word.matches("^x=\"\\d+\"$")) {
                rect.posX = Double.parseDouble(word.replaceAll("\\D+", ""));
            }else
            if (word.matches("^y=\"\\d+\"$")) {
                rect.posY = Double.parseDouble(word.replaceAll("\\D+", ""));
            }else
            if (word.matches("^width=\"\\d+%?\"$")) {
                rect.width = Double.parseDouble(word.replaceAll("\\D+", ""));
            }else
            if (word.matches("^height=\"\\d+%?\"$")) {
                rect.height = Double.parseDouble(word.replaceAll("\\D+", ""));
            }else
            if (word.matches("^rx=\"\\d+\"$")) {
                rect.rx = Double.parseDouble(word.replaceAll("\\D+", ""));
            }else
            if (word.matches("^ry=\"\\d+\"$")) {
                rect.ry = Double.parseDouble(word.replaceAll("\\D+", ""));
            }
        }
        
        list.add(new Line(rect.posX, rect.posY, rect.posX+rect.width, rect.posY));
        list.add(new Line(rect.posX+rect.width, rect.posY, rect.posX+rect.width, rect.posY+rect.height));
        list.add(new Line(rect.posX+rect.width, rect.posY+rect.height, rect.posX, rect.posY+rect.height));
        list.add(new Line(rect.posX, rect.posY+rect.height, rect.posX, rect.posY));
        return list;
    }
    public static Circle CircleDestruct(String[] words){
        Circle circle = new Circle(0,0,0);
        for (String word : words) {
            if (word.matches("^cx=\"\\d+\"$")) {
                circle.posX = Double.parseDouble(word.replaceAll("\\D+", ""));
            }
            if (word.matches("^cy=\"\\d+\"$")) {
                circle.posY = Double.parseDouble(word.replaceAll("\\D+", ""));
            }
            if (word.matches("^r=\"\\d+\"$")) {
                circle.r = Double.parseDouble(word.replaceAll("\\D+", ""));
            }
        }
        return circle;
    }
    public static Ellipse EllipseDestruct(String[] words){
        Ellipse ellipse = new Ellipse(0,0,0,0);
        for (String word : words) {
            if (word.matches("^cx=\"\\d+\"$")) {
                ellipse.posX = Double.parseDouble(word.replaceAll("\\D+", ""));
            }
            if (word.matches("^cy=\"\\d+\"$")) {
                ellipse.posY = Double.parseDouble(word.replaceAll("\\D+", ""));
            }
            if (word.matches("^rx=\"\\d+\"$")) {
                ellipse.rx = Double.parseDouble(word.replaceAll("\\D+", ""));
            }
            if (word.matches("^ry=\"\\d+\"$")) {
                ellipse.ry = Double.parseDouble(word.replaceAll("\\D+", ""));
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
    public static ArrayList<Vector> PolylineDestruct(String[] words, boolean returnPolygon){
        ArrayList<Vector> lines = new ArrayList<>();
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
        //Polyline polyline = new Polyline(lines);
        return lines;
    }
    public static ArrayList<Vector> PolygonDestruct(String[] words){
        return PolylineDestruct(words, true);
    }
}
