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

    private Entity[][] entityMap;
    private int height, width;

    /**
     * manages the map of the game. handles the representation of entities on the map.
     * handles loading and random generation of maps
     *
     * @param rogue the game
     */
    MapManager(Rogue rogue, int length, int height, int roomsize, int numOfEnemies, int numOfNpcs) {

        this.rogue = rogue;
        map = randomlyGenMap(length, height, roomsize, numOfEnemies, numOfNpcs);
        this.height = getMapHeight() + 1;
        this.width = getMapLength() + 1;
    }


    /**
     * updates the entitymap
     */
    public void update() {
        updateEntityMap();
    }


    /**
     * refreshes the entity map. has a priority system.
     * player>enemy>npc>obstacles
     */
    private void updateEntityMap() {
        entityMap = new Entity[height][width];

        EntityManager em = rogue.getEm();


        for (NPC n : em.getNpcs()) {
            entityMap[n.getY()][n.getX()] = n;
        }

        for (Enemy en : em.getEnemies()) {
            entityMap[en.getY()][en.getX()] = en;
        }


        for (Obstacle ob : em.getObstacles()) {
            entityMap[ob.getY()][ob.getX()] = ob;
        }
        entityMap[em.getPlayer().getY()][em.getPlayer().getX()] = em.getPlayer();
    }

    /**
     * get a map from the entitiyMap but only the symbols expressed for each entity
     *
     * @return a 2d array of entity symbols that are currently on the EntityMap
     */
    public char[][] getCharacterMap() {
        char[][] characterMap = new char[height][width];

        for (int j = 0; j < characterMap.length; j++) {
            for (int i = 0; i < characterMap[0].length; i++) {
                characterMap[j][i] = '.';
            }
        }

        EntityManager em = rogue.getEm();

        for (NPC n : em.getNpcs()) {
            if (n.isVisable()) {
                characterMap[n.getY()][n.getX()] = n.getSymbol();
            }
        }

        for (Obstacle ob : em.getObstacles()) {
            characterMap[ob.getY()][ob.getX()] = ob.getSymbol();
        }

        for (Enemy en : em.getEnemies()) {
            if (en.isVisable()) {
                characterMap[en.getY()][en.getX()] = en.getSymbol();
            }
        }

        return characterMap;
    }


    /**
     * gets the symbol of the entity at a current location
     *
     * @param x
     * @param y
     * @return
     */
    public char getCharacter(int x, int y) {
        char[][] characterMap = getCharacterMap();
        try {
            return characterMap[y][x];
        } catch (Exception e) {
            return ' ';
        }
    }

    public char[][] randomlyGenMap(int length, int height, int roomsize, int numOfEnemies, int numOfNpcs) {
        char[][] m;
        try {
            m = convertMap(new MapGenerator(length, height, roomsize, numOfEnemies, numOfNpcs).map);
        } catch (Exception e) {
            return randomlyGenMap(length, height, roomsize, numOfEnemies, numOfNpcs);
        }
        return m;
    }

    public int getMapLength() {
        return map.length;
    }

    public int getMapHeight() {
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
                } else if (map[j][i] == 'x') {
                    entityMap[j][i] = em.createPlayer(i, j);
                } else if (map[j][i] == 'e') {
                    entityMap[j][i] = em.createEnemy(i, j, 'e');
                } else if (map[j][i] == 'c') {
                    entityMap[j][i] = em.createNPC(i, j, 'c');
                }
            }
        }

    }

    /**
     * converts the . on the map from the generater to obs
     */
    public char[][] convertMap(char[][] map) {
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                if (map[j][i] == '.') map[j][i] = '#';
            }
        }
        return map;
    }

    public char[][] getMap() {
        return this.map;
    }


    /**
     * loads a map
     * map requires the first line to be the number of rows followed by the number of columns
     *
     * @return
     */
    public char[][] mapLoad() {

        File inFile = new File("Map.txt");                                      //loading in the text file map
        try {
            scanner = new Scanner(inFile);
        } catch (IOException e) {
            System.out.println(e);
        }
        String[] size = scanner.nextLine().split("\\s");

        Rogue.setheight(Integer.parseInt(size[0]));                                // on the top line of the map, state the number of rows as the first parameter
        Rogue.setwidth(Integer.parseInt(size[1]));                                // on the top line of the map, state the number of cols as the second parameter

        char[][] array = new char[Integer.parseInt(size[0])][Integer.parseInt(size[1])];        //create a map of size stated on the top line, of type character
        for (int i = 0; i < Integer.parseInt(size[0]); i++) {                      //loop through all the line s of the array
            array[i] = scanner.nextLine().toCharArray();                        // convert that line from a string to an array of chars
        }
        System.out.println(Arrays.deepToString(array).replace("], ", "]\n").replace("[[", "[").replace("]]", "]")); // prints out the map
        return array;
    }


    public Entity[][] getEntityMap() {
        return entityMap.clone();
    }


}
