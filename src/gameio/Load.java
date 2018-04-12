package gameio;

import entity.Enemy;
import entity.EntityCharacter;
import entity.NPC;
import entity.Player;
import rogue.Rogue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Load {

    /**
     * loads a Player serialized object and returns it
     *
     * @param filename
     * @return
     */
    public static Player loadPlayer(String filename) {
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        Player entityCharacter = null;
        try {
            fin = new FileInputStream(filename);
            ois = new ObjectInputStream(fin);
            entityCharacter = (Player) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (entityCharacter != null) {
                return entityCharacter;
            }
        }
        return null;
    }


    /**
     * loads a NPC serialized object and returns it
     *
     * @param filename
     * @return
     */
    public static NPC loadNPC(String filename) {
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        NPC entityCharacter = null;
        try {
            fin = new FileInputStream(filename);
            ois = new ObjectInputStream(fin);
            entityCharacter = (NPC) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (entityCharacter != null) {
                return entityCharacter;
            }
        }
        return null;
    }

    /**
     * loads a Enemy serialized object and returns it
     *
     * @param filename
     * @return
     */
    public static Enemy loadEnemy(String filename) {
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        Enemy entityCharacter = null;
        try {
            fin = new FileInputStream(filename);
            ois = new ObjectInputStream(fin);
            entityCharacter = (Enemy) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (entityCharacter != null) {
                return entityCharacter;
            }
        }
        return null;
    }


    /**
     * loads a files that contains the number
     * using it as a way to count the number of entities to load
     *
     * @param filename
     * @return
     */
    public static int getIndex(String filename) {
        Scanner scanner;
        File inFile = new File(filename);
        try {
            scanner = new Scanner(inFile);
            return scanner.nextInt();
        } catch (IOException e) {
            System.out.println(e);
        }
        return -1;
    }


    /**
     * loads a map
     * map requires the first line to be the number of rows followed by the number of columns
     *
     * @return
     */
    public static char[][] mapLoad() {
        Scanner scanner;

        File inFile = new File(combine("gamesave", "map.txt"));                                      //loading in the text file map
        try {
            scanner = new Scanner(inFile);
            String[] size = scanner.nextLine().split("\\s");
            Rogue.setheight(Integer.parseInt(size[0]));                                // on the top line of the map, state the number of rows as the first parameter
            Rogue.setwidth(Integer.parseInt(size[1]));                                // on the top line of the map, state the number of cols as the second parameter

            char[][] array = new char[Integer.parseInt(size[0])][Integer.parseInt(size[1])];        //create a map of size stated on the top line, of type character
            for (int i = 0; i < Integer.parseInt(size[0]) - 1; i++) {                      //loop through all the line s of the array
                array[i] = scanner.nextLine().toCharArray();                        // convert that line from a string to an array of chars
            }
            System.out.println(Arrays.deepToString(array).replace("], ", "]\n").replace("[[", "[").replace("]]", "]")); // prints out the map
            return array;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }


    public static ArrayList<NPC> loadNPCS() {
        int nNPC = Load.getIndex(combine("gamesave", "nNPC.txt"));

        ArrayList<NPC> npcs = new ArrayList<>();
        for (int i = 0; i < nNPC; i++) {
            npcs.add(Load.loadNPC(combine("gamesave", "npc") + i + ".ser"));
        }
        return npcs;

    }

    public static ArrayList<Enemy> loadEnemies() {
        int nEnemy = Load.getIndex(combine("gamesave","nEnemy.txt"));
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < nEnemy; i++) {
            enemies.add(Load.loadEnemy(combine("gamesave", "enemy") + i + ".ser"));
        }
        return enemies;
    }


    /**
     * combines 2 paths together
     * @param path1
     * @param path2
     * @return
     */
    public static String combine(String path1, String path2)
    {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(currentPath.toString(), path1, path2);
        return filePath.toString();
    }

}
