import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AStartTest extends AStar{

    @Test
    public void test_path_finder1(){
        ArrayList<Point> path = test(1, 5, 5, 0, 0, 3, 2, new int[][]{{0,4},{2,2},{3,1},{3,3}});
        String pathString = "";
        String confirmedPath = "[2, 3][2, 4][1, 4][0, 4][0, 3][0, 2][0, 1]";
        for(Point point: path){
            pathString += point.toString();
        }
        assertEquals("Path test for 1", confirmedPath, pathString);
    }


    @Test
    public void test_path_finder2(){
        ArrayList<Point> path = test(2, 5, 5, 0, 0, 4, 4, new int[][]{{0,4},{2,2},{3,1},{3,3}});
        String pathString = "";
        String confirmedPath = "[4, 4][3, 4][2, 4][1, 4][0, 4][0, 3][0, 2][0, 1]";
        for(Point point: path){
            pathString += point.toString();
        }
        assertEquals("Path test for 2", confirmedPath, pathString);
    }


    @Test
    public void test_path_finder3(){
        ArrayList<Point> path = test(3, 7, 7, 2, 1, 5, 4, new int[][]{{4,1},{4,3},{5,3},{2,3}});
        String pathString = "";
        String confirmedPath = "[4, 5][4, 4][4, 3][3, 3][2, 3][2, 2]";
        for(Point point: path){
            pathString += point.toString();
        }
        assertEquals("Path test for 3", confirmedPath, pathString);
    }

    @Test
    public void test_path_finder4_impossible(){
        ArrayList<Point> path = test(1, 5, 5, 0, 0, 4, 4, new int[][]{{3,4},{3,3},{4,3}});
        String pathString = "";
        String confirmedPath = "";
        for(Point point: path){
            pathString += point.toString();
        }
        assertEquals("Path test for 4", confirmedPath, pathString);
    }

}
