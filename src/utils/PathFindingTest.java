package utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PathFindingTest {

    @Test
    public void test_no_solution() {
        /*
        {O,O,O,O,O},
        {O,S,O,O,O},
        {O,O,O,O,O},
        {O,O,O,X,O},
        {O,O,O,O,O}
         */
        Point[] obs = new Point[23];
        obs[0] = new Point(0, 0);
        obs[1] = new Point(1, 0);
        obs[2] = new Point(2, 0);
        obs[3] = new Point(3, 0);
        obs[4] = new Point(4, 0);

        obs[5] = new Point(0, 1);

        obs[6] = new Point(2, 1);
        obs[7] = new Point(3, 1);
        obs[8] = new Point(4, 1);

        obs[9] = new Point(0, 2);
        obs[10] = new Point(1, 2);
        obs[11] = new Point(2, 2);
        obs[12] = new Point(3, 2);
        obs[13] = new Point(4, 2);

        obs[14] = new Point(0, 3);
        obs[15] = new Point(1, 3);
        obs[16] = new Point(2, 3);

        obs[17] = new Point(4, 3);

        obs[18] = new Point(0, 4);
        obs[19] = new Point(1, 4);
        obs[20] = new Point(2, 4);
        obs[21] = new Point(3, 4);
        obs[22] = new Point(4, 4);


        Point start = new Point(1, 1);
        Point end = new Point(3, 3);
        PathFinder o = new PathFinder();


        assertEquals("the number of moves should be 0", o.moves.size(), 0);
    }


    @Test
    public void test_findS() {
	    /* Going to make a map that looks like this:
	            {'O','O','O','O','O','O'},
	            {'O','.','.','.','.','O'},
	            {'O','.','S','.','.','O'},
	            {'O','.','.','.','.','O'},
	            {'O','.','.','X','.','O'},
	            {'O','O','O','O','O','O'},
	     */
        Point ob1 = new Point(0, 0);
        Point ob2 = new Point(1, 0);
        Point ob3 = new Point(2, 0);
        Point ob4 = new Point(3, 0);
        Point ob5 = new Point(4, 0);
        Point ob6 = new Point(5, 0);

        Point ob7 = new Point(0, 1);
        Point ob8 = new Point(0, 2);
        Point ob9 = new Point(0, 3);
        Point ob10 = new Point(0, 4);
        Point ob11 = new Point(0, 5);

        Point ob12 = new Point(1, 5);
        Point ob13 = new Point(2, 5);
        Point ob14 = new Point(3, 5);
        Point ob15 = new Point(4, 5);
        Point ob16 = new Point(5, 5);

        Point ob17 = new Point(5, 1);
        Point ob18 = new Point(5, 2);
        Point ob19 = new Point(5, 3);
        Point ob20 = new Point(5, 4);

        Point[] obstacles = new Point[20];
        obstacles[0] = ob1;
        obstacles[1] = ob2;
        obstacles[2] = ob3;
        obstacles[3] = ob4;
        obstacles[4] = ob5;
        obstacles[5] = ob6;
        obstacles[6] = ob7;
        obstacles[7] = ob8;
        obstacles[8] = ob9;
        obstacles[9] = ob10;
        obstacles[10] = ob11;
        obstacles[11] = ob12;
        obstacles[12] = ob13;
        obstacles[13] = ob14;
        obstacles[14] = ob15;
        obstacles[15] = ob16;
        obstacles[16] = ob17;
        obstacles[17] = ob18;
        obstacles[18] = ob19;
        obstacles[19] = ob20;

        Point start = new Point(2, 2);
        Point end = new Point(3, 4);
        PathFinder o = new PathFinder();
        char[][] map = o.makeMaze(6, 6, start, end, obstacles);
        System.out.println();

        assertEquals("starting x location should be 2", 2, o.findS(map)[0]);
        assertEquals("starting y location should be 2", 2, o.findS(map)[1]);
    }

    @Test
    public void test_findX() {
		  /* Going to make a map that looks like this:
          {'O','O','O','O','O','O'},
          {'O','.','.','.','.','O'},
          {'O','.','S','.','.','O'},
          {'O','.','.','.','.','O'},
          {'O','.','.','X','.','O'},
          {'O','O','O','O','O','O'},
	   */
        Point ob1 = new Point(0, 0);
        Point ob2 = new Point(1, 0);
        Point ob3 = new Point(2, 0);
        Point ob4 = new Point(3, 0);
        Point ob5 = new Point(4, 0);
        Point ob6 = new Point(5, 0);

        Point ob7 = new Point(0, 1);
        Point ob8 = new Point(0, 2);
        Point ob9 = new Point(0, 3);
        Point ob10 = new Point(0, 4);
        Point ob11 = new Point(0, 5);

        Point ob12 = new Point(1, 5);
        Point ob13 = new Point(2, 5);
        Point ob14 = new Point(3, 5);
        Point ob15 = new Point(4, 5);
        Point ob16 = new Point(5, 5);

        Point ob17 = new Point(5, 1);
        Point ob18 = new Point(5, 2);
        Point ob19 = new Point(5, 3);
        Point ob20 = new Point(5, 4);

        Point[] obstacles = new Point[20];
        obstacles[0] = ob1;
        obstacles[1] = ob2;
        obstacles[2] = ob3;
        obstacles[3] = ob4;
        obstacles[4] = ob5;
        obstacles[5] = ob6;
        obstacles[6] = ob7;
        obstacles[7] = ob8;
        obstacles[8] = ob9;
        obstacles[9] = ob10;
        obstacles[10] = ob11;
        obstacles[11] = ob12;
        obstacles[12] = ob13;
        obstacles[13] = ob14;
        obstacles[14] = ob15;
        obstacles[15] = ob16;
        obstacles[16] = ob17;
        obstacles[17] = ob18;
        obstacles[18] = ob19;
        obstacles[19] = ob20;

        Point start = new Point(2, 2);
        Point end = new Point(3, 4);
        PathFinder m = new PathFinder();
        char[][] map = m.makeMaze(6, 6, start, end, obstacles);

        assertEquals("ending x location should be 4", 4, m.findX(map)[0]);
        assertEquals("ending y location should be 3", 3, m.findX(map)[1]);
    }

    @Test
    public void test_pathTakenLength() {
		  /* Going to make a map that looks like this:
          {'O','O','O','O','O','O'},
          {'O','.','.','.','.','O'},
          {'O','.','S','.','.','O'},
          {'O','.','.','.','.','O'},
          {'O','.','.','X','.','O'},
          {'O','O','O','O','O','O'},
          Should be solved in 4 moves. (Initial spot counts as a move)
	   */
        Point ob1 = new Point(0, 0);
        Point ob2 = new Point(1, 0);
        Point ob3 = new Point(2, 0);
        Point ob4 = new Point(3, 0);
        Point ob5 = new Point(4, 0);
        Point ob6 = new Point(5, 0);

        Point ob7 = new Point(0, 1);
        Point ob8 = new Point(0, 2);
        Point ob9 = new Point(0, 3);
        Point ob10 = new Point(0, 4);
        Point ob11 = new Point(0, 5);

        Point ob12 = new Point(1, 5);
        Point ob13 = new Point(2, 5);
        Point ob14 = new Point(3, 5);
        Point ob15 = new Point(4, 5);
        Point ob16 = new Point(5, 5);

        Point ob17 = new Point(5, 1);
        Point ob18 = new Point(5, 2);
        Point ob19 = new Point(5, 3);
        Point ob20 = new Point(5, 4);

        Point[] obstacles = new Point[20];
        obstacles[0] = ob1;
        obstacles[1] = ob2;
        obstacles[2] = ob3;
        obstacles[3] = ob4;
        obstacles[4] = ob5;
        obstacles[5] = ob6;
        obstacles[6] = ob7;
        obstacles[7] = ob8;
        obstacles[8] = ob9;
        obstacles[9] = ob10;
        obstacles[10] = ob11;
        obstacles[11] = ob12;
        obstacles[12] = ob13;
        obstacles[13] = ob14;
        obstacles[14] = ob15;
        obstacles[15] = ob16;
        obstacles[16] = ob17;
        obstacles[17] = ob18;
        obstacles[18] = ob19;
        obstacles[19] = ob20;

        Point start = new Point(2, 2);
        Point end = new Point(3, 4);
        PathFinder o = new PathFinder(6, 6, start, end, obstacles);
        //char[][] map = o.makeMaze(6,6, start, end, obstacles);
        int result = o.moves.size();
        //System.out.println(result);

        assertEquals("OPTIMAL LENGTH SIZE OF PATH is 4", 4, result);
    }

    @Test
    public void test_pathTakenLength2() {
		  /* Going to make a map that looks like this:
      {'O','O','O','O','O','O'},
      {'O','S','.','.','.','O'},
      {'O','.','.','.','.','O'},
      {'O','.','O','.','O','O'},
      {'O','.','.','X','.','O'},
      {'O','O','O','O','O','O'},
      Should be solved in 6 moves. (Initial spot counts as a move)
	*/
        Point ob1 = new Point(0, 0);
        Point ob2 = new Point(1, 0);
        Point ob3 = new Point(2, 0);
        Point ob4 = new Point(3, 0);
        Point ob5 = new Point(4, 0);
        Point ob6 = new Point(5, 0);

        Point ob7 = new Point(0, 1);
        Point ob8 = new Point(0, 2);
        Point ob9 = new Point(0, 3);
        Point ob10 = new Point(0, 4);
        Point ob11 = new Point(0, 5);

        Point ob12 = new Point(1, 5);
        Point ob13 = new Point(2, 5);
        Point ob14 = new Point(3, 5);
        Point ob15 = new Point(4, 5);
        Point ob16 = new Point(5, 5);

        Point ob17 = new Point(5, 1);
        Point ob18 = new Point(5, 2);
        Point ob19 = new Point(5, 3);
        Point ob20 = new Point(5, 4);
        Point ob21 = new Point(2, 3);
        Point ob22 = new Point(4, 3);

        Point[] obstacles = new Point[22];
        obstacles[0] = ob1;
        obstacles[1] = ob2;
        obstacles[2] = ob3;
        obstacles[3] = ob4;
        obstacles[4] = ob5;
        obstacles[5] = ob6;
        obstacles[6] = ob7;
        obstacles[7] = ob8;
        obstacles[8] = ob9;
        obstacles[9] = ob10;
        obstacles[10] = ob11;
        obstacles[11] = ob12;
        obstacles[12] = ob13;
        obstacles[13] = ob14;
        obstacles[14] = ob15;
        obstacles[15] = ob16;
        obstacles[16] = ob17;
        obstacles[17] = ob18;
        obstacles[18] = ob19;
        obstacles[19] = ob20;
        obstacles[20] = ob21;
        obstacles[21] = ob22;

        Point start = new Point(1, 1);
        Point end = new Point(3, 4);
        PathFinder o = new PathFinder(6, 6, start, end, obstacles);
        int result = o.moves.size();
        assertEquals("TESTING OPTIMAL LENGTH SIZE OF PATH is 6", 6, result);
        System.out.println();

    }

    @Test
    public void test_pathTaken1() {
		  /* Going to make a map that looks like this:
	      {'O','O','O','O','O','O'},
	      {'O','S','.','.','.','O'},
	      {'O','.','.','.','.','O'},
	      {'O','.','O','.','O','O'},
	      {'O','.','.','X','.','O'},
	      {'O','O','O','O','O','O'},
	      The third move should have row 2 and column 3
		*/
        Point ob1 = new Point(0, 0);
        Point ob2 = new Point(1, 0);
        Point ob3 = new Point(2, 0);
        Point ob4 = new Point(3, 0);
        Point ob5 = new Point(4, 0);
        Point ob6 = new Point(5, 0);

        Point ob7 = new Point(0, 1);
        Point ob8 = new Point(0, 2);
        Point ob9 = new Point(0, 3);
        Point ob10 = new Point(0, 4);
        Point ob11 = new Point(0, 5);

        Point ob12 = new Point(1, 5);
        Point ob13 = new Point(2, 5);
        Point ob14 = new Point(3, 5);
        Point ob15 = new Point(4, 5);
        Point ob16 = new Point(5, 5);

        Point ob17 = new Point(5, 1);
        Point ob18 = new Point(5, 2);
        Point ob19 = new Point(5, 3);
        Point ob20 = new Point(5, 4);
        Point ob21 = new Point(2, 3);
        Point ob22 = new Point(4, 3);

        Point[] obstacles = new Point[22];
        obstacles[0] = ob1;
        obstacles[1] = ob2;
        obstacles[2] = ob3;
        obstacles[3] = ob4;
        obstacles[4] = ob5;
        obstacles[5] = ob6;
        obstacles[6] = ob7;
        obstacles[7] = ob8;
        obstacles[8] = ob9;
        obstacles[9] = ob10;
        obstacles[10] = ob11;
        obstacles[11] = ob12;
        obstacles[12] = ob13;
        obstacles[13] = ob14;
        obstacles[14] = ob15;
        obstacles[15] = ob16;
        obstacles[16] = ob17;
        obstacles[17] = ob18;
        obstacles[18] = ob19;
        obstacles[19] = ob20;
        obstacles[20] = ob21;
        obstacles[21] = ob22;

        Point start = new Point(1, 1);
        Point end = new Point(3, 4);
        PathFinder o = new PathFinder(6, 6, start, end, obstacles);

        assertEquals("the points of the path taken should be [1, 2][1, 3][2, 3][3, 3][4, 3][4, 3]", pathToString(o.moves), "[1, 2][1, 3][2, 3][3, 3][4, 3][4, 3]");

    }


    @Test
    /**
     * test the solution to the maze for textmap01
     */
    public void test_PathTaken2() {
        String filePath = combine("src", "testmap01.txt");
        PathFindingMapFileReader map = new PathFindingMapFileReader(filePath);
        PathFinder o = new PathFinder(24, 50, map.getStart(), map.getEnd(), map.getObs());
        String actual = "[21, 13][21, 14][21, 15][21, 16][21, 17][21, 18][21, 19][20, 19][19, 19][18, 19][17, 19][16, 19]" +
                "[15, 19][15, 18][15, 19][14, 19][14, 18][14, 19][13, 19][13, 20][13, 21][13, 22][13, 23][13, 24][13, 25][13, 26]" +
                "[13, 27][13, 28][13, 29][13, 30][13, 31][14, 31][15, 31][15, 32][15, 32]";
        assertEquals("expected path of testmap01.txt is " + actual,
                pathToString(o.moves), actual);
    }


    @Test
    /**
     * test the solution to the maze for textmap01
     */
    public void test_PathTaken3() {
        String filePath = combine("src", "testmap02.txt");
        PathFindingMapFileReader map = new PathFindingMapFileReader(filePath);
        PathFinder o = new PathFinder(3, 20, map.getStart(), map.getEnd(), map.getObs());
        String actual = "[1, 3][1, 4][1, 5][1, 6][1, 7][1, 8][1, 9][1, 10][1, 11][1, 12][1, 13][1, 14][1, 15][1, 16][1, 16]";
        assertEquals("expected path of testmap01.txt is " + actual,
                pathToString(o.moves), actual);
    }


    @Test
    /**
     * test the solution to the maze for textmap01
     */
    public void test_PathTaken4() {
        String filePath = combine("src", "testmap03.txt");
        PathFindingMapFileReader map = new PathFindingMapFileReader(filePath);

        PathFinder o = new PathFinder(3, 20, map.getStart(), map.getEnd(), map.getObs());
        String actual = "[1, 3][1, 4][1, 5][1, 6][1, 7][1, 8][1, 9][1, 10][1, 11][1, 12][1, 13][1, 14][1, 15][1, 16][1, 16]";
        assertEquals("expected path of testmap01.txt is " + actual,
                pathToString(o.moves), actual);
    }


    /**
     * combines 2 paths together
     *
     * @param path1
     * @param path2
     * @return
     */
    public static String combine(String path1, String path2) {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(currentPath.toString(), path1, path2);
        return filePath.toString();
    }


    public String pathToString(ArrayList<Point> pts) {
        String str = "";
        for (Point pt : pts) {
            str += pt.toString();
        }
        return str;
    }


    public static void main(String args[]) {


    }
}



