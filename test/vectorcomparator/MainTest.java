package vectorcomparator;

import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
//import java.util.Random;

/**
 *
 * @author Timur
 */
public class MainTest {

    //Тестирование метода разбора для объекта Ellipse
    @Test
    public void ShouldParseElipse() {
        String example = "<ellipse ry=\"46\" rx=\"55.5\" id=\"svg_1\" cy=\"142.5\" cx=\"134\" stroke-width=\"1.5\" stroke=\"#000\" fill=\"#fff\"/>";
        Ellipse ellipse = new Ellipse(55.5, 46, 134, 142.5);
        Ellipse ellipseActual = Util.EllipseParse(example.split(" "));
        Assert.assertTrue(ellipse.posX == ellipseActual.posX & ellipse.posY == ellipseActual.posY & ellipse.rx == ellipseActual.rx & ellipse.ry == ellipseActual.ry);
    }

    //Тестирование метода разбора для объекта Path и его метода CompareTo
    @Test
    public void ShouldParsePathAndSelfCompare() {
        String example = "<path id=\"svg_1\" d=\"M197.2,238.4c-1.2-4-2.8-8-4.8-12s-4.8-7.2-7.6-10.4\" opacity=\"0.5\" fill-opacity=\"null\" stroke-opacity=\"null\" stroke-width=\"1.5\" stroke=\"#000\" fill=\"none\"/>";

        ArrayList<Vector> vector = new ArrayList<>();
        vector.add(new CubicBezier(new Vertex(197.2, 238.4), new Vertex(197.2 - 4.8, 238.4 - 12), new Vertex(197.2 - 1.2, 238.4 - 4), new Vertex(197.2 - 2.8, 238.4 - 8)));
        vector.add(new CubicBezier(new Vertex(197.2 - 4.8, 238.4 - 12), new Vertex(197.2 - 4.8 - 7.6, 238.4 - 12 - 10.4), new Vertex(197.2 - 2.8 - (197.2 - 4.8 - (197.2 - 2.8)) * 2, 238.4 - 8 - (238.4 - 12 - (238.4 - 8) * 2)), new Vertex(197.2 - 4.8 - 4.8, 238.4 - 12 - 7.2)));

        ArrayList<Vector> vectorActual = Util.PathParse(example);

        double result = 1;
        for (int i = 0; i < Math.min(vector.size(), vectorActual.size()); i++) {
            result *= vector.get(i).CompareTo(vectorActual.get(i));
        }
        Assert.assertEquals(result, 1, 0);
    }

    //Нагрузочный тест файлом размером ~5.2 МБ
    @Test
    public void LoadTest() {
        String path = "C:\\temp\\sakura-2069810.svg";
        Assert.assertEquals(Util.Compare(Util.Verify(path), Util.Verify(path)),1,0);
    }
    
    //Тест пустого пути
    @Test
    public void EmptyPathTest(){
        String path = "";
        Assert.assertNull(Util.Verify(path));
    }
    
    //Тест пустого файла
    @Test
    public void EmptyFileTest(){
        String path = "C:\\temp\\empty.svg";
        Assert.assertNull(Util.Verify(path));
    }

//    //Тест с использованием 1 млн. элементов
//    @Test
//    public void MillionElementsTest(){
//        ArrayList<Vector> list = new ArrayList<>();
//        ArrayList<Vector> list2 = new ArrayList<>();
//        Random rnd = new Random();
//        for (int i = 0; i < 1000000; i++) {
//            list.add(new CubicBezier(new Vertex(rnd.nextInt(500),rnd.nextInt(500)),new Vertex(rnd.nextInt(500),rnd.nextInt(500)), new Vertex(rnd.nextInt(500),rnd.nextInt(500)),new Vertex(rnd.nextInt(500),rnd.nextInt(500))));
//            list2.add(new CubicBezier(new Vertex(rnd.nextInt(500),rnd.nextInt(500)),new Vertex(rnd.nextInt(500),rnd.nextInt(500)), new Vertex(rnd.nextInt(500),rnd.nextInt(500)),new Vertex(rnd.nextInt(500),rnd.nextInt(500))));
//        }
//        Assert.assertEquals(Util.Compare(list, list2),1,0);
//    }
}
