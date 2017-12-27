/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vectorcomparator;
import java.io.*;
import static java.lang.System.out;
import java.nio.file.*;
import java.util.regex.*;

/**
 *
 * @author Timur
 */
public class VectorComparator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String s = "";
        Pattern pattern;
        Matcher matcher;
        String path_pattern;
        
        try{
            s = new String(Files.readAllBytes(Paths.get("C:\\Users\\Timur\\Desktop\\SVG\\this_boy_loves_Christmas-4784.svg")));
            //s = s.replace("\n", "").replace("\r", "");
        }catch(IOException e){
        }
        
        path_pattern = "<path.*(\n)*(\r)*/>";
        pattern = Pattern.compile(path_pattern);
        matcher = pattern.matcher(s);
        if (matcher.find()){
            out.println(matcher.group());
        }else{
            out.println("failure");
        }
    }
}
