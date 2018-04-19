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

import javax.swing.JOptionPane;

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
        return Destruct(p);
    }
    
    public static ArrayList Destruct(String path) {
        ArrayList<Vector> list = new ArrayList<>();
        String svg = "";
        Pattern p;
        Matcher m;

//        Pattern doublePattern;
//        String doublePatternString = "\\-?\\d+(\\.\\d+)?";
        /**
         * Запись файла SVG в переменную
         */
        try {
            svg = new String(Files.readAllBytes(Paths.get(path)));
            if (svg.length()>10000000) {
                JOptionPane.showMessageDialog(null,"Eggs are not supposed to be green.","Error",JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } catch (IOException e) {
            
            return list;
        }

        p = Pattern.compile("<[a-z][^(\\/>)]+\\/>");
        m = p.matcher(svg);
//        doublePattern  = Pattern.compile("\\-?\\d+(\\.\\d+)?");

        /**
         * Запись элементов в формализованую запись
         */
        while (m.find()) {
            String str = m.group().replaceAll("\\s{2,}", " ");                     //Убирает табулирование
            String[] words = str.split(" ");
            ArrayList<Vector> temp;
            switch (words[0]) {
                case "<path":
                    temp = PathDestruct(str);
                    if (temp.contains(null)) {
                        JOptionPane.showMessageDialog(null,"Bad structure!","Error",JOptionPane.ERROR_MESSAGE);
                    }else{
                        list.addAll(temp);
                    }
                    break;
                case "<rect":
                    temp = RectDestruct(words);
                    if (temp.contains(null)) {
                        JOptionPane.showMessageDialog(null,"Bad structure!","Error",JOptionPane.ERROR_MESSAGE);
                    }else{
                        list.addAll(temp);
                    }
                    break;
                case "<circle":
                    list.add(CircleDestruct(words));
                    break;
                case "<ellipse":
                    list.add(EllipseDestruct(words));
                    break;
                case "<line":
                    list.add(LineDestruct(words));
                    break;
                case "<polyline":
                    temp = PolylineDestruct(words, false);
                    if (temp.contains(null)) {
                        JOptionPane.showMessageDialog(null,"Bad structure!","Error",JOptionPane.ERROR_MESSAGE);
                    }else{
                        list.addAll(temp);
                    }
                    break;
                case "<polygon":
                    temp = PolygonDestruct(words);
                    if (temp.contains(null)) {
                        JOptionPane.showMessageDialog(null,"Bad structure!","Error",JOptionPane.ERROR_MESSAGE);
                    }else{
                        list.addAll(temp);
                    }
                    break;
                default:
                    break;
            }
        }

        /**
         * Присвоение полю weight каждого элемента значения
         */
        for (int i = 0; i < list.size() - 1; i++) {
            Vector temp = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                Vector temp2 = list.get(j);
                temp.weight *= 1 - temp.CompareTo(temp2);

            }
            list.set(i, temp);
        }

        return list;
    }

    public static String Clean(String str) {
        String[] lib = new String[]{"requiredFeatures", "requiredExtensions", "systemLanguage", "id", "xml:base", "xml:lang", "xml:space", "onfocusin", "onfocusout", "onactivate", "onclick", "onmousedown", "onmouseup", "onmouseover", "onmousemove", "onmouseout", "onload", "alignment-baseline", "baseline-shift", "clip", "clip-path", "clip-rule", "color", "color-interpolation", "color-interpolation-filters", "color-profile", "color-rendering", "cursor", "direction", "display", "dominant-baseline", "enable-background", "fill", "fill-opacity", "fill-rule", "filter", "flood-color", "flood-opacity", "font-family", "font-size", "font-size-adjust", "font-stretch", "font-style", "font-variant", "font-weight", "glyph-orientation-horizontal", "glyph-orientation-vertical", "image-rendering", "kerning", "letter-spacing", "lighting-color", "marker-end", "marker-mid", "marker-start", "mask", "opacity", "overflow", "pointer-events", "shape-rendering", "stop-color", "stop-opacity", "stroke", "stroke-dasharray", "stroke-dashoffset", "stroke-linecap", "stroke-linejoin", "stroke-miterlimit", "stroke-opacity", "stroke-width", "text-anchor", "text-decoration", "text-rendering", "unicode-bidi", "visibility", "word-spacing", "writing-mode", "class", "style", "externalResourcesRequired", "transform", "pathLength"};
        for (int i = 0; i < lib.length; i++) {
            str = str.replace(lib[i], "");
        }
        return str;
    }

    public static ArrayList<Vector> RectDestruct(String[] words) {
        ArrayList<Vector> list = new ArrayList<>();

        Rect rect = new Rect();
        for (String word : words) {
            if (word.matches("^x=\"\\d+\"$")) {
                rect.posX = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            } else if (word.matches("^y=\"\\d+\"$")) {
                rect.posY = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            } else if (word.matches("^width=\"\\d+%?\"$")) {
                rect.width = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            } else if (word.matches("^height=\"\\d+%?\"$")) {
                rect.height = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            } else if (word.matches("^rx=\"\\d+\"$")) {
                rect.rx = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            } else if (word.matches("^ry=\"\\d+\"$")) {
                rect.ry = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            }
        }

        list.add(new Line(rect.posX, rect.posY, rect.posX + rect.width, rect.posY));
        list.add(new Line(rect.posX + rect.width, rect.posY, rect.posX + rect.width, rect.posY + rect.height));
        list.add(new Line(rect.posX + rect.width, rect.posY + rect.height, rect.posX, rect.posY + rect.height));
        list.add(new Line(rect.posX, rect.posY + rect.height, rect.posX, rect.posY));
        return list;
    }

    public static Circle CircleDestruct(String[] words) {
        Circle circle = new Circle();
        for (String word : words) {
            if (word.matches("^cx=\"\\d+\"$")) {
                circle.posX = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            }
            if (word.matches("^cy=\"\\d+\"$")) {
                circle.posY = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            }
            if (word.matches("^r=\"\\d+\"$")) {
                circle.r = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            }
        }
        return circle;
    }

    public static Ellipse EllipseDestruct(String[] words) {
        Ellipse ellipse = new Ellipse();
        for (String word : words) {
            if (word.matches("^cx=\"\\d+\"$")) {
                ellipse.posX = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            }
            if (word.matches("^cy=\"\\d+\"$")) {
                ellipse.posY = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            }
            if (word.matches("^rx=\"\\d+\"$")) {
                ellipse.rx = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            }
            if (word.matches("^ry=\"\\d+\"$")) {
                ellipse.ry = Double.parseDouble(word.replaceAll("[^\\d\\.]+", ""));
            }
        }
        return ellipse;
    }

    public static Line LineDestruct(String[] words) {
        Line line = new Line();
        Pattern p = Pattern.compile("\\-?\\d+(\\.\\d+)?");
        for (int i = 1; i < words.length; i++) {
            if (words[i].contains("x1")) {
                words[i] = words[i].replace("x1", "");
                Matcher m = p.matcher(words[i]);
                line.SetX1(m.find() ? Double.parseDouble(m.group()) : 0);
            }
            if (words[i].contains("x2")) {
                words[i] = words[i].replace("x2", "");
                Matcher m = p.matcher(words[i]);
                line.SetX2(m.find() ? Double.parseDouble(m.group()) : 0);
            }
            if (words[i].contains("y1")) {
                words[i] = words[i].replace("y1", "");
                Matcher m = p.matcher(words[i]);
                line.SetY1(m.find() ? Double.parseDouble(m.group()) : 0);
            }
            if (words[i].contains("y2")) {
                words[i] = words[i].replace("y2", "");
                Matcher m = p.matcher(words[i]);
                line.SetY2(m.find() ? Double.parseDouble(m.group()) : 0);
            }
        }
        return line;
    }

    public static ArrayList<Vector> PolylineDestruct(String[] words, boolean returnPolygon) {
        ArrayList<Vector> lines = new ArrayList<>();
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 1; i < words.length; i++) {
            if (words[i].contains(",")) {
                Pattern p = Pattern.compile("\\d*");
                Matcher m = p.matcher(words[i]);
                double x = 0;
                double y = 0;

                x = m.find() ? Double.parseDouble(m.group()) : 0;
                y = m.find() ? Double.parseDouble(m.group()) : 0;

                vertices.add(new Vertex(x, y));
            }
        }
        for (int i = 1; i < vertices.size(); i++) {
            lines.add(new Line(vertices.get(i - 1), vertices.get(i)));
        }
        if (returnPolygon) {
            lines.add(new Line(vertices.get(vertices.size() - 1), vertices.get(0)));
        }
        //Polyline polyline = new Polyline(lines);
        return lines;
    }

    public static ArrayList<Vector> PolygonDestruct(String[] words) {
        return PolylineDestruct(words, true);
    }

    public static ArrayList<Vector> PathDestruct(String str) {

        Pattern p1 = Pattern.compile("\\sd=\"[^\"]+\""); //паттерн для вычленения геометрической составляющей элемента
        Matcher m1 = p1.matcher(str);
        String s = "";
        if (m1.find()) {
            s = m1.group().replace("d=\"", "");
            s = s.replace("\"", "");
        }
        String[] e = s.split("(?=[a-zA-Z])");

        ArrayList<Vector> list = new ArrayList<>();
        Vertex curPos = new Vertex(0, 0);
        Vertex origin = null;

        Pattern p = Pattern.compile("\\-?\\d+(\\.\\d+)?"); //паттерн для вычленения параметров

        Vertex prevCubicBezierPoint = new Vertex(); //Для использования при разбиении кубических кривых Безье
        Vertex prevQuadraticBezierPoint = new Vertex();

        if (e.length == 0) {
            return new ArrayList();
        }
        for (String el : e) {
            if (el.contains("m")) {
                double x = 0;
                double y = 0;
                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    if (i % 2 == 0) {
                        x += Double.parseDouble(m.group());
                    } else {
                        y += Double.parseDouble(m.group());
                    }
                    i++;
                }

                if (i % 2 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                curPos.Translate(x, y);
                prevCubicBezierPoint = null;
                prevQuadraticBezierPoint = null;
            } else if (el.contains("l")) {
                Vertex prevPos = new Vertex(curPos);
                origin = origin == null ? new Vertex(curPos) : origin;

                double x = 0;
                double y = 0;
                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    if (i % 2 == 0) {
                        x = Double.parseDouble(m.group());
                    } else {
                        y = Double.parseDouble(m.group());

                        curPos.Translate(x, y);
                        list.add(new Line(prevPos, curPos));

                        prevPos = new Vertex(curPos);
                    }
                    i++;
                }
                if (i % 2 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                prevCubicBezierPoint = null;
                prevQuadraticBezierPoint = null;

            } else if (el.contains("h")) {

                Vertex prevPos = new Vertex(curPos);
                origin = origin == null ? new Vertex(curPos) : origin;

                double x = 0;
                Matcher m = p.matcher(el);
                while (m.find()) {
                    x += Double.parseDouble(m.group());

                }

                curPos.Translate(x, 0);
                list.add(new Line(prevPos, curPos));
                prevCubicBezierPoint = null;
                prevQuadraticBezierPoint = null;

            } else if (el.contains("v")) {

                Vertex prevPos = new Vertex(curPos);
                origin = origin == null ? new Vertex(curPos) : origin;

                double y = 0;
                Matcher m = p.matcher(el);
                while (m.find()) {
                    y += Double.parseDouble(m.group());

                }

                curPos.Translate(0, y);
                list.add(new Line(prevPos, curPos));
                prevCubicBezierPoint = null;
                prevQuadraticBezierPoint = null;

            } else if (el.contains("c")) {
                Vertex prevPos = new Vertex(curPos);
                origin = origin == null ? new Vertex(curPos) : origin;

                double x = 0;
                double y = 0;
                double x1 = 0;
                double y1 = 0;
                double x2 = 0;
                double y2 = 0;

                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    switch (i % 6) {
                        case 0:
                            x1 = Double.parseDouble(m.group());
                            break;
                        case 1:
                            y1 = Double.parseDouble(m.group());
                            break;
                        case 2:
                            x2 = Double.parseDouble(m.group());
                            break;
                        case 3:
                            y2 = Double.parseDouble(m.group());
                            break;
                        case 4:
                            x = Double.parseDouble(m.group());
                            break;
                        case 5:
                            y = Double.parseDouble(m.group());
                            curPos.Translate(x, y);
                            Vertex controlPoint1 = new Vertex(x1 + prevPos.posX, y1 + prevPos.posY);
                            Vertex controlPoint2 = new Vertex(x2 + prevPos.posX, y2 + prevPos.posY);
                            list.add(new CubicBezier(prevPos, curPos, controlPoint1, controlPoint2));
                            prevPos = new Vertex(curPos);
                            prevCubicBezierPoint = new Vertex(controlPoint2);
                            prevQuadraticBezierPoint = null;
                            break;
                    }
                    i++;
                }
                if (i % 6 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else if (el.contains("s")) {
                Vertex prevPos = new Vertex(curPos);
                origin = origin == null ? new Vertex(curPos) : origin;

                double x = 0;
                double y = 0;
                double x2 = 0;
                double y2 = 0;

                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    switch (i % 4) {
                        case 0:
                            x2 = Double.parseDouble(m.group());
                            break;
                        case 1:
                            y2 = Double.parseDouble(m.group());
                            break;
                        case 2:
                            x = Double.parseDouble(m.group());
                            break;
                        case 3:
                            y = Double.parseDouble(m.group());
                            Vertex controlPoint1 = new Vertex();
                            curPos.Translate(x, y);
                            if (prevCubicBezierPoint != null) {
                                controlPoint1 = new Vertex(prevCubicBezierPoint);
                                controlPoint1.posX -= (prevPos.posX - controlPoint1.posX) * 2;
                                controlPoint1.posY -= (prevPos.posY - controlPoint1.posY) * 2;
                            } else {
                                controlPoint1 = new Vertex(prevPos);
                            }

                            Vertex controlPoint2 = new Vertex(x2 + prevPos.posX, y2 + prevPos.posY);
                            list.add(new CubicBezier(prevPos, curPos, controlPoint1, controlPoint2));
                            prevPos = new Vertex(curPos);
                            prevCubicBezierPoint = new Vertex(controlPoint2);
                            prevQuadraticBezierPoint = null;
                            break;
                    }
                    i++;
                }
                if (i % 4 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }

            } else if (el.contains("q")) {
                Vertex prevPos = new Vertex(curPos);
                origin = origin == null ? new Vertex(curPos) : origin;

                double x = 0;
                double y = 0;
                double x1 = 0;
                double y1 = 0;

                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    switch (i % 4) {
                        case 0:
                            x1 = Double.parseDouble(m.group());
                            break;
                        case 1:
                            y1 = Double.parseDouble(m.group());
                            break;
                        case 2:
                            x = Double.parseDouble(m.group());
                            break;
                        case 3:
                            y = Double.parseDouble(m.group());
                            curPos.Translate(x, y);
                            Vertex controlPoint = new Vertex(x1 + prevPos.posX, y1 + prevPos.posY);
                            list.add(new QuadraticBezier(prevPos, curPos, controlPoint));
                            prevPos = new Vertex(curPos);
                            prevCubicBezierPoint = null;
                            prevQuadraticBezierPoint = new Vertex(controlPoint);
                            break;
                    }
                    i++;
                }
                if (i % 4 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else if (el.contains("t")) {
                Vertex prevPos = new Vertex(curPos);
                origin = origin == null ? new Vertex(curPos) : origin;

                double x = 0;
                double y = 0;
                double x1 = 0;
                double y1 = 0;

                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    switch (i % 4) {
                        case 0:
                            x1 = Double.parseDouble(m.group());
                            break;
                        case 1:
                            y1 = Double.parseDouble(m.group());
                            break;
                        case 2:
                            x = Double.parseDouble(m.group());
                            break;
                        case 3:
                            y = Double.parseDouble(m.group());
                            curPos.Translate(x, y);
                            Vertex controlPoint = new Vertex();
                            if (prevCubicBezierPoint != null) {

                                controlPoint = new Vertex(prevQuadraticBezierPoint);
                                controlPoint.posX -= (prevPos.posX - controlPoint.posX) * 2;
                                controlPoint.posY -= (prevPos.posY - controlPoint.posY) * 2;
                            } else {
                                controlPoint = new Vertex(prevPos);
                            }

                            list.add(new QuadraticBezier(prevPos, curPos, controlPoint));
                            prevPos = new Vertex(curPos);
                            prevCubicBezierPoint = null;
                            prevQuadraticBezierPoint = new Vertex(controlPoint);
                            break;
                    }
                    i++;
                }
                if (i % 4 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else if (el.contains("a")) {
                Vertex prevPos = new Vertex(curPos);
                origin = origin == null ? new Vertex(curPos) : origin;

                double rx = 0;
                double ry = 0;
                double xAxisRotation = 0;
                double largeArcDlag = 0;
                double sweepFlag = 0;
                double x = 0;
                double y = 0;

                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    switch (i % 7) {
                        case 0:
                            rx = Double.parseDouble(m.group());
                            break;
                        case 1:
                            ry = Double.parseDouble(m.group());
                            break;
                        case 2:
                            xAxisRotation = Double.parseDouble(m.group());
                            break;
                        case 3:
                            largeArcDlag = Double.parseDouble(m.group());
                            break;
                        case 4:
                            sweepFlag = Double.parseDouble(m.group());
                            break;
                        case 5:
                            x = Double.parseDouble(m.group());
                            break;
                        case 6:
                            y = Double.parseDouble(m.group());
                            curPos.Translate(x, y);
                            list.add(new Arc(rx, ry, xAxisRotation, largeArcDlag, sweepFlag, x, y));
                            prevPos = new Vertex(curPos);
                            prevCubicBezierPoint = null;
                            prevQuadraticBezierPoint = null;
                            break;
                    }
                    i++;
                }
                if (i % 7 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else if (el.contains("z") | el.contains("Z")) {
                list.add(new Line(curPos, origin));
                curPos = new Vertex(origin);
                origin = null;

            } else if (el.contains("M")) {
                double x = 0;
                double y = 0;
                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    if (i % 2 == 0) {
                        x += Double.parseDouble(m.group());
                    } else {
                        y += Double.parseDouble(m.group());
                    }
                    i++;
                }
                if (i % 2 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                curPos.Set(x, y);
                prevCubicBezierPoint = null;
                prevQuadraticBezierPoint = null;
            } else if (el.contains("L")) {

                double x = 0;
                double y = 0;
                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    if (i % 2 == 0) {
                        x = Double.parseDouble(m.group());
                    } else {
                        y = Double.parseDouble(m.group());

                        curPos.Set(x, y);
                        list.add(new Line(new Vertex(0, 0), curPos));
                    }
                    i++;
                }
                if (i % 2 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                prevCubicBezierPoint = null;
                prevQuadraticBezierPoint = null;

            } else if (el.contains("H")) {

                double x = 0;
                Matcher m = p.matcher(el);
                while (m.find()) {
                    x += Double.parseDouble(m.group());

                }

                curPos.Set(x, 0);
                list.add(new Line(new Vertex(0, 0), curPos));
                prevCubicBezierPoint = null;
                prevQuadraticBezierPoint = null;

            } else if (el.contains("V")) {

                double y = 0;
                Matcher m = p.matcher(el);
                while (m.find()) {
                    y += Double.parseDouble(m.group());

                }

                curPos.Set(0, y);
                list.add(new Line(new Vertex(0, 0), curPos));
                prevCubicBezierPoint = null;
                prevQuadraticBezierPoint = null;

            } else if (el.contains("C")) {

                double x = 0;
                double y = 0;
                double x1 = 0;
                double y1 = 0;
                double x2 = 0;
                double y2 = 0;

                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    switch (i % 6) {
                        case 0:
                            x1 = Double.parseDouble(m.group());
                            break;
                        case 1:
                            y1 = Double.parseDouble(m.group());
                            break;
                        case 2:
                            x2 = Double.parseDouble(m.group());
                            break;
                        case 3:
                            y2 = Double.parseDouble(m.group());
                            break;
                        case 4:
                            x = Double.parseDouble(m.group());
                            break;
                        case 5:
                            y = Double.parseDouble(m.group());
                            curPos.Set(x, y);
                            Vertex controlPoint1 = new Vertex(x1, y1);
                            Vertex controlPoint2 = new Vertex(x2, y2);
                            list.add(new CubicBezier(new Vertex(0, 0), curPos, controlPoint1, controlPoint2));

                            prevCubicBezierPoint = new Vertex(controlPoint2);
                            prevQuadraticBezierPoint = null;
                            break;
                    }
                    i++;
                }
                if (i % 6 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else if (el.contains("S")) {

                double x = 0;
                double y = 0;
                double x2 = 0;
                double y2 = 0;

                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    switch (i % 4) {
                        case 0:
                            x2 = Double.parseDouble(m.group());
                            break;
                        case 1:
                            y2 = Double.parseDouble(m.group());
                            break;
                        case 2:
                            x = Double.parseDouble(m.group());
                            break;
                        case 3:
                            y = Double.parseDouble(m.group());
                            curPos.Set(x, y);
                            Vertex controlPoint1 = new Vertex();
                            if (prevCubicBezierPoint != null) {
                                controlPoint1 = new Vertex(prevCubicBezierPoint);
                                controlPoint1.posX -= (-controlPoint1.posX) * 2;
                                controlPoint1.posY -= (-controlPoint1.posY) * 2;
                            } else {
                                controlPoint1 = new Vertex(new Vertex(0, 0));
                            }

                            Vertex controlPoint2 = new Vertex(x2, y2);
                            list.add(new CubicBezier(new Vertex(0, 0), curPos, controlPoint1, controlPoint2));

                            prevCubicBezierPoint = new Vertex(controlPoint2);
                            prevQuadraticBezierPoint = null;
                            break;
                    }
                    i++;
                }
                if (i % 4 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }

            } else if (el.contains("Q")) {

                double x = 0;
                double y = 0;
                double x1 = 0;
                double y1 = 0;

                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    switch (i % 4) {
                        case 0:
                            x1 = Double.parseDouble(m.group());
                            break;
                        case 1:
                            y1 = Double.parseDouble(m.group());
                            break;
                        case 2:
                            x = Double.parseDouble(m.group());
                            break;
                        case 3:
                            y = Double.parseDouble(m.group());
                            curPos.Set(x, y);
                            Vertex controlPoint = new Vertex(x1, y1);
                            list.add(new QuadraticBezier(new Vertex(0, 0), curPos, controlPoint));

                            prevCubicBezierPoint = null;
                            prevQuadraticBezierPoint = new Vertex(controlPoint);
                            break;
                    }
                    i++;
                }
                if (i % 4 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else if (el.contains("T")) {

                double x = 0;
                double y = 0;
                double x1 = 0;
                double y1 = 0;

                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    switch (i % 4) {
                        case 0:
                            x1 = Double.parseDouble(m.group());
                            break;
                        case 1:
                            y1 = Double.parseDouble(m.group());
                            break;
                        case 2:
                            x = Double.parseDouble(m.group());
                            break;
                        case 3:
                            y = Double.parseDouble(m.group());
                            curPos.Set(x, y);
                            Vertex controlPoint = new Vertex();
                            if (prevCubicBezierPoint != null) {

                                controlPoint = new Vertex(prevQuadraticBezierPoint);
                                controlPoint.posX -= (-controlPoint.posX) * 2;
                                controlPoint.posY -= (-controlPoint.posY) * 2;
                            } else {
                                controlPoint = new Vertex(new Vertex(0, 0));
                            }

                            list.add(new QuadraticBezier(new Vertex(0, 0), curPos, controlPoint));

                            prevCubicBezierPoint = null;
                            prevQuadraticBezierPoint = new Vertex(controlPoint);
                            break;
                    }
                    i++;
                }
                if (i % 4 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else if (el.contains("A")) {

                double rx = 0;
                double ry = 0;
                double xAxisRotation = 0;
                double largeArcDlag = 0;
                double sweepFlag = 0;
                double x = 0;
                double y = 0;

                Matcher m = p.matcher(el);
                int i = 0;
                while (m.find()) {
                    switch (i % 7) {
                        case 0:
                            rx = Double.parseDouble(m.group());
                            break;
                        case 1:
                            ry = Double.parseDouble(m.group());
                            break;
                        case 2:
                            xAxisRotation = Double.parseDouble(m.group());
                            break;
                        case 3:
                            largeArcDlag = Double.parseDouble(m.group());
                            break;
                        case 4:
                            sweepFlag = Double.parseDouble(m.group());
                            break;
                        case 5:
                            x = Double.parseDouble(m.group());
                            break;
                        case 6:
                            y = Double.parseDouble(m.group());
                            curPos.Set(x, y);

                            list.add(new Arc(rx, ry, xAxisRotation, largeArcDlag, sweepFlag, x, y));
                            prevCubicBezierPoint = null;
                            prevQuadraticBezierPoint = null;
                            break;
                    }
                    i++;
                }
                if (i % 7 != 0|i==0) {
                    JOptionPane.showMessageDialog(null,"Bad file structure!","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
        }
        return list;
    }

    public Vector AddElement(String type) {
        Vector el = new Line(0, 0, 0, 0);
        switch (type) {
            case "M":
                break;
            case "m":
                break;
            case "V":
                el = new Line(0, 0, 0, 0);
        }
        return el;
    }
}
