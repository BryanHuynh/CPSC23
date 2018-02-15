import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Manages map: load and generate entities
 */
public class MapManager {
    private Scanner scanner = new Scanner(System.in);
    private Rogue rogue;
    private char[][] map;

    /**
     *
     * @param rogue the game
     */
    MapManager(Rogue rogue){
        this.rogue = rogue;
        map = mapLoad();
    }


    public int getMapLength(){
        return map.length;
    }

    public int getMapHeight(){
        return map[0].length;
    }

    /**
     * with the map, create all the entities using entityManager from Rogue
     */
    public void createMapEntities() {
        Entity[][] entityMap = new Entity[map.length][map[0].length];           //create a 2d array of type entity, size of map
        EntityManager em = rogue.getEm();                                       //get the entityManager to use to create the entities are on the character map
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                if (map[j][i] == '#') {
                    entityMap[j][i] = em.createObstacles(i, j, '#');            //if the 2d char map has an obstacle, turn that obstacle into an actual entity using the entity manager
                }
            }
        }
    }


    /**
     * loads a map
     * map requires the first line to be the number of rows followed by the number of columns
     * @return
     */
    public char[][] mapLoad(){

        File inFile = new File("Map.txt");                                      //loading in the text file map
        try{
            scanner = new Scanner(inFile);
        }catch(IOException e){
            System.out.println(e);
        }
        String[] size = scanner.nextLine().split("\\s");

        Rogue.setRow(Integer.parseInt(size[0]));                                // on the top line of the map, state the number of rows as the first parameter
        Rogue.setCol(Integer.parseInt(size[1]));                                // on the top line of the map, state the number of cols as the second parameter

        char[][] array = new char[Integer.parseInt(size[0])][Integer.parseInt(size[1])];        //create a map of size stated on the top line, of type character
        for(int i=0; i < Integer.parseInt(size[0]); i++) {                      //loop through all the line s of the array
            array[i] = scanner.nextLine().toCharArray();                        // convert that line from a string to an array of chars
        }
        System.out.println(Arrays.deepToString(array).replace("], ", "]\n").replace("[[", "[").replace("]]", "]")); // prints out the map
        return array;
    }


}
