package utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PathFindingTest {

    @Test
    public void test_no_solution(){
        /*
        {O,O,O,O,O},
        {O,S,O,O,O},
        {O,O,O,O,O},
        {O,O,O,X,O},
        {O,O,O,O,O}
         */
        Point[] obs = new Point[23];
        obs[0] = new Point(0,0);
        obs[1] = new Point(1,0);
        obs[2] = new Point(2,0);
        obs[3] = new Point(3,0);
        obs[4] = new Point(4,0);

        obs[5] = new Point(0,1);

        obs[6] = new Point(2,1);
        obs[7] = new Point(3,1);
        obs[8] = new Point(4,1);

        obs[9] = new Point(0,2);
        obs[10] = new Point(1,2);
        obs[11] = new Point(2,2);
        obs[12] = new Point(3,2);
        obs[13] = new Point(4,2);

        obs[14] = new Point(0,3);
        obs[15] = new Point(1,3);
        obs[16] = new Point(2,3);

        obs[17] = new Point(4,3);

        obs[18] = new Point(0,4);
        obs[19] = new Point(1,4);
        obs[20] = new Point(2,4);
        obs[21] = new Point(3,4);
        obs[22] = new Point(4,4);



        Point start = new Point(1,1);
        Point end = new Point(3,3);
        PathFinder o = new PathFinder();


        System.out.println("\n TESTING FOR NO SOLUTION");
        System.out.println("SIZE OF PATH IS: " + o.moves.size());
        System.out.println("EXPECTED PATH SIZE IS " + 0);
        assertEquals("the number of moves should be 0",o.moves.size(), 0);
    }







    @Test
    public void test_findS(){
	    /* Going to make a map that looks like this:
	            {'O','O','O','O','O','O'},
	            {'O','.','.','.','.','O'},
	            {'O','.','S','.','.','O'},
	            {'O','.','.','.','.','O'},
	            {'O','.','.','X','.','O'},
	            {'O','O','O','O','O','O'},
	     */
        Point ob1 = new Point(0,0);
        Point ob2 = new Point(1,0);
        Point ob3 = new Point(2,0);
        Point ob4 = new Point(3,0);
        Point ob5 = new Point(4,0);
        Point ob6 = new Point(5,0);

        Point ob7 = new Point(0,1);
        Point ob8 = new Point(0,2);
        Point ob9 = new Point(0,3);
        Point ob10 = new Point(0,4);
        Point ob11 = new Point(0,5);

        Point ob12 = new Point(1,5);
        Point ob13 = new Point(2,5);
        Point ob14 = new Point(3,5);
        Point ob15 = new Point(4,5);
        Point ob16 = new Point(5,5);

        Point ob17 = new Point(5,1);
        Point ob18 = new Point(5,2);
        Point ob19 = new Point(5,3);
        Point ob20 = new Point(5,4);

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

        Point start = new Point(2,2);
        Point end = new Point(3,4);
        PathFinder o = new PathFinder();
        char[][] map = o.makeMaze(6,6, start, end, obstacles);
        System.out.println();

        System.out.println("TESTING FOR FIND START LOCATION");
        System.out.println("EXPECTED x,y LOCATION (2,2)");
        System.out.println("ACTUAL x,y LOCATION " + o.findS(map)[0] +", " + o.findS(map)[1]);
        assertEquals(2, o.findS(map)[0]);
        assertEquals(2, o.findS(map)[1]);
    }

    @Test
    public void test_findX(){
		  /* Going to make a map that looks like this:
          {'O','O','O','O','O','O'},
          {'O','.','.','.','.','O'},
          {'O','.','S','.','.','O'},
          {'O','.','.','.','.','O'},
          {'O','.','.','X','.','O'},
          {'O','O','O','O','O','O'},
	   */
        Point ob1 = new Point(0,0);
        Point ob2 = new Point(1,0);
        Point ob3 = new Point(2,0);
        Point ob4 = new Point(3,0);
        Point ob5 = new Point(4,0);
        Point ob6 = new Point(5,0);

        Point ob7 = new Point(0,1);
        Point ob8 = new Point(0,2);
        Point ob9 = new Point(0,3);
        Point ob10 = new Point(0,4);
        Point ob11 = new Point(0,5);

        Point ob12 = new Point(1,5);
        Point ob13 = new Point(2,5);
        Point ob14 = new Point(3,5);
        Point ob15 = new Point(4,5);
        Point ob16 = new Point(5,5);

        Point ob17 = new Point(5,1);
        Point ob18 = new Point(5,2);
        Point ob19 = new Point(5,3);
        Point ob20 = new Point(5,4);

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

        Point start = new Point(2,2);
        Point end = new Point(3,4);
        PathFinder m = new PathFinder();
        char[][] map = m.makeMaze(6,6, start, end, obstacles);
        System.out.println();
        System.out.println("TESTING FOR FIND X LOCATION");
        System.out.println("EXPECTED x,y LOCATION (4,3)");
        System.out.println("ACTUAL x,y LOCATION " + m.findX(map)[0] +", " + m.findX(map)[1]);

        assertEquals(4, m.findX(map)[0]);
        assertEquals(3, m.findX(map)[1]);
    }

    @Test
    public void test_pathTakenLength(){
		  /* Going to make a map that looks like this:
          {'O','O','O','O','O','O'},
          {'O','.','.','.','.','O'},
          {'O','.','S','.','.','O'},
          {'O','.','.','.','.','O'},
          {'O','.','.','X','.','O'},
          {'O','O','O','O','O','O'},
          Should be solved in 4 moves. (Initial spot counts as a move)
	   */
        Point ob1 = new Point(0,0);
        Point ob2 = new Point(1,0);
        Point ob3 = new Point(2,0);
        Point ob4 = new Point(3,0);
        Point ob5 = new Point(4,0);
        Point ob6 = new Point(5,0);

        Point ob7 = new Point(0,1);
        Point ob8 = new Point(0,2);
        Point ob9 = new Point(0,3);
        Point ob10 = new Point(0,4);
        Point ob11 = new Point(0,5);

        Point ob12 = new Point(1,5);
        Point ob13 = new Point(2,5);
        Point ob14 = new Point(3,5);
        Point ob15 = new Point(4,5);
        Point ob16 = new Point(5,5);

        Point ob17 = new Point(5,1);
        Point ob18 = new Point(5,2);
        Point ob19 = new Point(5,3);
        Point ob20 = new Point(5,4);

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

        Point start = new Point(2,2);
        Point end = new Point(3,4);
        PathFinder o = new PathFinder(6,6, start, end, obstacles);
        //char[][] map = o.makeMaze(6,6, start, end, obstacles);
        int result = o.moves.size();
        //System.out.println(result);
        System.out.println();
        System.out.println("TESTING OPTIMAL LENGTH SIZE OF PATH");
        System.out.println("EXPECTED (4)");
        System.out.println("ACTUAL    " + result);

        assertEquals(4, result);
    }

    @Test
    public void test_pathTakenLength2(){
		  /* Going to make a map that looks like this:
      {'O','O','O','O','O','O'},
      {'O','S','.','.','.','O'},
      {'O','.','.','.','.','O'},
      {'O','.','O','.','O','O'},
      {'O','.','.','X','.','O'},
      {'O','O','O','O','O','O'},
      Should be solved in 6 moves. (Initial spot counts as a move)
	*/
        Point ob1 = new Point(0,0);
        Point ob2 = new Point(1,0);
        Point ob3 = new Point(2,0);
        Point ob4 = new Point(3,0);
        Point ob5 = new Point(4,0);
        Point ob6 = new Point(5,0);

        Point ob7 = new Point(0,1);
        Point ob8 = new Point(0,2);
        Point ob9 = new Point(0,3);
        Point ob10 = new Point(0,4);
        Point ob11 = new Point(0,5);

        Point ob12 = new Point(1,5);
        Point ob13 = new Point(2,5);
        Point ob14 = new Point(3,5);
        Point ob15 = new Point(4,5);
        Point ob16 = new Point(5,5);

        Point ob17 = new Point(5,1);
        Point ob18 = new Point(5,2);
        Point ob19 = new Point(5,3);
        Point ob20 = new Point(5,4);
        Point ob21 = new Point(2,3);
        Point ob22 = new Point(4,3);

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

        Point start = new Point(1,1);
        Point end = new Point(3,4);
        PathFinder o = new PathFinder(6,6, start, end, obstacles);
        int result = o.moves.size();
        assertEquals(6, result);
        System.out.println();
        System.out.println("TESTING OPTIMAL LENGTH SIZE OF PATH");
        System.out.println("EXPECTED (6)");
        System.out.println("ACTUAL    " + result);
    }
    @Test
    public void test_pathTaken(){
		  /* Going to make a map that looks like this:
	      {'O','O','O','O','O','O'},
	      {'O','S','.','.','.','O'},
	      {'O','.','.','.','.','O'},
	      {'O','.','O','.','O','O'},
	      {'O','.','.','X','.','O'},
	      {'O','O','O','O','O','O'},
	      The third move should have row 2 and column 3
		*/
        Point ob1 = new Point(0,0);
        Point ob2 = new Point(1,0);
        Point ob3 = new Point(2,0);
        Point ob4 = new Point(3,0);
        Point ob5 = new Point(4,0);
        Point ob6 = new Point(5,0);

        Point ob7 = new Point(0,1);
        Point ob8 = new Point(0,2);
        Point ob9 = new Point(0,3);
        Point ob10 = new Point(0,4);
        Point ob11 = new Point(0,5);

        Point ob12 = new Point(1,5);
        Point ob13 = new Point(2,5);
        Point ob14 = new Point(3,5);
        Point ob15 = new Point(4,5);
        Point ob16 = new Point(5,5);

        Point ob17 = new Point(5,1);
        Point ob18 = new Point(5,2);
        Point ob19 = new Point(5,3);
        Point ob20 = new Point(5,4);
        Point ob21 = new Point(2,3);
        Point ob22 = new Point(4,3);

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

        Point start = new Point(1,1);
        Point end = new Point(3,4);
        PathFinder o = new PathFinder(6,6, start, end, obstacles);
        int row = o.moves.get(2).getX();
        int column = o.moves.get(2).getY();

        assertEquals(2, row);
        assertEquals(3, column);
    }
    @Test
    public void test_mazeGetsSolved(){
		  /* Going to make a map that looks like this:
	      {'O','O','O','O','O','O'},
	      {'O','S','.','.','.','O'},
	      {'O','.','.','.','.','O'},
	      {'O','.','O','.','O','O'},
	      {'O','.','.','X','.','O'},
	      {'O','O','O','O','O','O'},
	      The coordinates of S should be the same as the coordinates of X once the function mazeMath is called
		*/
        Point ob1 = new Point(0,0);
        Point ob2 = new Point(1,0);
        Point ob3 = new Point(2,0);
        Point ob4 = new Point(3,0);
        Point ob5 = new Point(4,0);
        Point ob6 = new Point(5,0);

        Point ob7 = new Point(0,1);
        Point ob8 = new Point(0,2);
        Point ob9 = new Point(0,3);
        Point ob10 = new Point(0,4);
        Point ob11 = new Point(0,5);

        Point ob12 = new Point(1,5);
        Point ob13 = new Point(2,5);
        Point ob14 = new Point(3,5);
        Point ob15 = new Point(4,5);
        Point ob16 = new Point(5,5);

        Point ob17 = new Point(5,1);
        Point ob18 = new Point(5,2);
        Point ob19 = new Point(5,3);
        Point ob20 = new Point(5,4);
        Point ob21 = new Point(2,3);
        Point ob22 = new Point(4,3);

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

        Point start = new Point(1,1);
        Point end = new Point(3,4);
        PathFinder o = new PathFinder();
        char[][] map = o.makeMaze(6,6, start, end, obstacles);
        int[] finish = new int[2];
        finish[0] = 4;
        finish[1] = 3;
        int[] begin = new int[2];
        begin[0] = 1;
        begin[1] = 1;
        int x0 = o.findX(map)[0];
        int x1 = o.findX(map)[1];
        o.mazeMath(map, finish, begin);
        int s0 = o.findS(map)[0];
        int s1 = o.findS(map)[1];
        Boolean zeros = (s0 == x0);
        Boolean ones = (s1 == x1);
        assertEquals(true, zeros);
        assertEquals(true, ones);
    }

    @Test
    public void test_mazeGetsSolved2(){
		  /* Going to make a map that looks like this:
	      {'O','O','O','O','O','O'},
	      {'O','.','.','O','.','O'},
	      {'O','S','.','.','O','O'},
	      {'O','.','O','X','O','O'},
	      {'O','.','.','.','.','O'},
	      {'O','O','O','O','O','O'},
	      The coordinates of S should be the same as the coordinates of X once the function mazeMath is called
		*/
        Point ob1 = new Point(0,0);
        Point ob2 = new Point(1,0);
        Point ob3 = new Point(2,0);
        Point ob4 = new Point(3,0);
        Point ob5 = new Point(4,0);
        Point ob6 = new Point(5,0);

        Point ob7 = new Point(0,1);
        Point ob8 = new Point(0,2);
        Point ob9 = new Point(0,3);
        Point ob10 = new Point(0,4);
        Point ob11 = new Point(0,5);

        Point ob12 = new Point(1,5);
        Point ob13 = new Point(2,5);
        Point ob14 = new Point(3,5);
        Point ob15 = new Point(4,5);
        Point ob16 = new Point(5,5);

        Point ob17 = new Point(5,1);
        Point ob18 = new Point(5,2);
        Point ob19 = new Point(5,3);
        Point ob20 = new Point(5,4);

        Point ob21 = new Point(3,1);
        Point ob22 = new Point(4,2);
        Point ob23 = new Point(2,3);
        Point ob24 = new Point(4,3);

        Point[] obstacles = new Point[24];
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
        obstacles[22] = ob23;
        obstacles[23] = ob24;

        Point start = new Point(1,2);
        Point end = new Point(3,4);
        PathFinder o = new PathFinder();
        char[][] map = o.makeMaze(6,6, start, end, obstacles);
        int[] finish = new int[2];
        finish[0] = 4;
        finish[1] = 3;
        int[] begin = new int[2];
        begin[0] = 2;
        begin[1] = 1;
        int x0 = o.findX(map)[0];
        int x1 = o.findX(map)[1];
        o.mazeMath(map, finish, begin);
        int s0 = o.findS(map)[0];
        int s1 = o.findS(map)[1];
        Boolean zeros = (s0 == x0);
        Boolean ones = (s1 == x1);
        assertEquals(true, zeros);
        assertEquals(true, ones);
    }

    @Test
    public void test_mazeGetsSolved3(){
		  /* Going to make a map that looks like this:
	      {'O','O','O','O','O','O'},
	      {'O','.','.','O','S','O'},
	      {'O','O','.','.','.','O'},
	      {'O','X','O','.','O','O'},
	      {'O','.','.','.','.','O'},
	      {'O','O','O','O','O','O'},
	      The coordinates of S should be the same as the coordinates of X once the function mazeMath is called
		*/
        Point ob1 = new Point(0,0);
        Point ob2 = new Point(1,0);
        Point ob3 = new Point(2,0);
        Point ob4 = new Point(3,0);
        Point ob5 = new Point(4,0);
        Point ob6 = new Point(5,0);

        Point ob7 = new Point(0,1);
        Point ob8 = new Point(0,2);
        Point ob9 = new Point(0,3);
        Point ob10 = new Point(0,4);
        Point ob11 = new Point(0,5);

        Point ob12 = new Point(1,5);
        Point ob13 = new Point(2,5);
        Point ob14 = new Point(3,5);
        Point ob15 = new Point(4,5);
        Point ob16 = new Point(5,5);

        Point ob17 = new Point(5,1);
        Point ob18 = new Point(5,2);
        Point ob19 = new Point(5,3);
        Point ob20 = new Point(5,4);

        Point ob21 = new Point(3,1);
        Point ob22 = new Point(1,2);
        Point ob23 = new Point(2,3);
        Point ob24 = new Point(4,3);

        Point[] obstacles = new Point[24];
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
        obstacles[22] = ob23;
        obstacles[23] = ob24;

        Point start = new Point(4,1);
        Point end = new Point(1,3);
        PathFinder o = new PathFinder();
        char[][] map = o.makeMaze(6,6, start, end, obstacles);
        int[] finish = new int[2];
        finish[0] = 3;
        finish[1] = 1;
        int[] begin = new int[2];
        begin[0] = 1;
        begin[1] = 4;
        int x0 = o.findX(map)[0];
        int x1 = o.findX(map)[1];
        o.mazeMath(map, finish, begin);
        int s0 = o.findS(map)[0];
        int s1 = o.findS(map)[1];
        Boolean zeros = (s0 == x0);
        Boolean ones = (s1 == x1);
        assertEquals(true, zeros);
        assertEquals(true, ones);
    }

    public static void main(String args[]){
        PathFindingTest pathFindingTest = new PathFindingTest();
        pathFindingTest.test_findX();
        pathFindingTest.test_findS();
        pathFindingTest.test_mazeGetsSolved();
        pathFindingTest.test_mazeGetsSolved2();
        pathFindingTest.test_mazeGetsSolved3();
        pathFindingTest.test_no_solution();
        pathFindingTest.test_pathTaken();
        pathFindingTest.test_pathTakenLength();
        pathFindingTest.test_pathTakenLength2();

    }
}



