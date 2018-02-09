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
        Entity[][] entityMap = new Entity[map.length][map[0].length];
        EntityManager em = rogue.getEm();
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                if (map[j][i] == '#') {
                    entityMap[j][i] = em.createObstacles(i, j, '#');
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

        File inFile = new File("Map.txt");
        try{
            scanner = new Scanner(inFile);
        }catch(IOException e){
            System.out.println(e);
        }
        String[] size = scanner.nextLine().split("\\s");

        Rogue.setRow(Integer.parseInt(size[0]));
        Rogue.setCol(Integer.parseInt(size[1]));

        char[][] array = new char[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
        for(int i=0; i < Integer.parseInt(size[0]); i++) {
            array[i] = scanner.nextLine().toCharArray();
        }
        System.out.println(Arrays.deepToString(array).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
        return array;
    }


}
