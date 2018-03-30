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
        String svg = "";
        Pattern p;
        Matcher m;
        /**
         * Запись файла SVG в переменную
        */
        try{
            svg = new String(Files.readAllBytes(Paths.get(path)));
        }catch(IOException e){
            return list;
        }
        
        p = Pattern.compile("<[a-z][^\\/>]+\\/>");
        m = p.matcher(svg);
        
        /**
         * Запись элементов в формализованую запись
         */
        
        while (m.find()){
            String str = m.group().replaceAll("\\s+", " ");                     //Убирает табулирование
            String[] words = str.split(" ");
            switch(words[0]){
                case "<path": list.addAll(PathDestruct(words));break;
                case "<rect": list.addAll(RectDestruct(words));break;
                case "<circle": list.add(CircleDestruct(words));break;
                case "<ellipse": list.add(EllipseDestruct(words));break;
                case "<line": list.add(LineDestruct(words));break;
                case "<polyline": list.addAll(PolylineDestruct(words, false));break;
                case "<polygon": list.addAll(PolygonDestruct(words));break;
                default: break;
            }
        }
        
        /**
         * Присвоение полю weight каждого элемента значения
         */
        
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

    public static ArrayList<Vector> PathDestruct(String words[]){
        /**
         * Восстановление строки
         */
        String s = "";
        for (int i = 1; i < words.length-1; i++) {
            s += words[i] + " ";
        }
        s.replace("d=\"", "");
        //words = s.split("[M,m,Z,z,L,l,H,h,V,v,C,c,S,s,Q,q,T,t,A,a]");
        
        Pattern p;
        Matcher m;
        
        p = Pattern.compile("[a-zA-Z]\\s\\d+\\s\\d+");
        m = p.matcher(s);
        
        ArrayList<Vector> list = new ArrayList<>();
        Vertex curPos = new Vertex(0,0);
        Vertex origin = new Vertex(0,0);
        
        while (m.find()){
            String f = m.group();
            if (f.matches("^m\\s?\\d+\\s?\\,?\\d+$")) {
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                curPos.Translate(Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
            }else if(f.matches("^l\\s?\\d+\\s?\\,?\\d+$")){
                Vertex prevPos = curPos;
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                curPos.Translate(Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
                list.add(new Line(prevPos, curPos));
            }else if(f.matches("^h\\s?\\d+$")){
                Vertex prevPos = curPos;
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                curPos.Translate(Double.parseDouble(temp[1]), 0);
                list.add(new Line(prevPos, curPos));
            }else if(f.matches("^v\\s?\\d+$")){
                Vertex prevPos = curPos;
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                curPos.Translate(0, Double.parseDouble(temp[1]));
                list.add(new Line(prevPos, curPos));
            }else if(f.matches("^c\\s?\\d+\\s?\\,?\\d+\\s?\\,?\\d+\\s?\\,?\\d+$")){
                Vertex prevPos = curPos;
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                curPos.Translate(Double.parseDouble(temp[5]), Double.parseDouble(temp[6]));
                Vertex controlPoint1 = new Vertex(Double.parseDouble(temp[1])+prevPos.posX, Double.parseDouble(temp[2])+prevPos.posY);
                Vertex controlPoint2 = new Vertex(Double.parseDouble(temp[3])+prevPos.posX, Double.parseDouble(temp[4])+prevPos.posY);
                list.add(new CubicBezier(prevPos, curPos, controlPoint1, controlPoint2));
            }else if(f.matches("^s\\s?\\d+\\s?\\,?\\d+\\s?\\,?\\d+$")){
                Vertex prevPos = curPos;
                
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                curPos.Translate(Double.parseDouble(temp[5]), Double.parseDouble(temp[6]));
                Vertex controlPoint1 = new Vertex(Double.parseDouble(temp[1])+prevPos.posX, Double.parseDouble(temp[2])+prevPos.posY);
                list.add(new CubicBezier(prevPos, curPos, controlPoint1, controlPoint1));
            }else if(f.matches("q^\\s?\\d+\\s?\\,?\\d+\\s?\\,?\\d+\\s?\\,?\\d+$")){
                Vertex prevPos = curPos;
                
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                curPos.Translate(Double.parseDouble(temp[5]), Double.parseDouble(temp[6]));
                Vertex controlPoint = new Vertex(Double.parseDouble(temp[1])+prevPos.posX, Double.parseDouble(temp[2])+prevPos.posY);
                list.add(new QuadraticBezier(prevPos, curPos, controlPoint));
            }else if(f.matches("t^\\s?\\d+\\s?\\,?\\d+$")){
                Vertex prevPos = curPos;
                
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                curPos.Translate(Double.parseDouble(temp[5]), Double.parseDouble(temp[6]));
                Vertex controlPoint = new Vertex(Double.parseDouble(temp[1])+prevPos.posX, Double.parseDouble(temp[2])+prevPos.posY);
                list.add(new QuadraticBezier(prevPos, curPos, controlPoint));
            }else if (f.matches("^M\\s?\\d+\\s?\\,?\\d+$")) {
                
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                curPos.posX = Double.parseDouble(temp[1]);
                curPos.posY = Double.parseDouble(temp[2]);
            }else if(f.matches("^L\\s?\\d+\\s?\\,?\\d+$")){
                
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                list.add(new Line(origin, new Vertex(Double.parseDouble(temp[1]), Double.parseDouble(temp[2]))));
            }else if(f.matches("^H\\s?\\d+$")){
                
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                list.add(new Line(origin, new Vertex(Double.parseDouble(temp[1]), 0)));
            }else if(f.matches("^V\\s?\\d+$")){
                
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                list.add(new Line(origin, new Vertex(0, Double.parseDouble(temp[1]))));
            }else if(f.matches("^C\\s?\\d+\\s?\\,?\\d+\\s?\\,?\\d+\\s?\\,?\\d+$")){
                
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                Vertex controlPoint1 = new Vertex(Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
                Vertex controlPoint2 = new Vertex(Double.parseDouble(temp[3]), Double.parseDouble(temp[4]));
                list.add(new CubicBezier(origin, new Vertex(Double.parseDouble(temp[5]), Double.parseDouble(temp[6])), controlPoint1, controlPoint2));
            }else if(f.matches("^S\\s?\\d+\\s?\\,?\\d+\\s?\\,?\\d+$")){
                
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                Vertex controlPoint1 = new Vertex(Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
                list.add(new CubicBezier(origin, new Vertex(Double.parseDouble(temp[5]), Double.parseDouble(temp[6])), controlPoint1, controlPoint1));
            }else if(f.matches("Q^\\s?\\d+\\s?\\,?\\d+\\s?\\,?\\d+\\s?\\,?\\d+$")){
                
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                Vertex controlPoint = new Vertex(Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
                list.add(new QuadraticBezier(origin, new Vertex(Double.parseDouble(temp[5]), Double.parseDouble(temp[6])), controlPoint));
            }else if(f.matches("T^\\s?\\d+\\s?\\,?\\d+$")){
                
                String[] temp = new String[0];
                if(f.contains(",")){
                    f.replace("m", "m,");
                    temp = f.split(",");
                }else{temp = f.split(" ");}
                Vertex controlPoint = new Vertex(Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
                list.add(new QuadraticBezier(origin, new Vertex(Double.parseDouble(temp[5]), Double.parseDouble(temp[6])), controlPoint));
            }
        }
        return list;
    }
}
