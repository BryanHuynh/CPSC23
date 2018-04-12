package gameio;

import entity.Enemy;
import entity.EntityCharacter;
import entity.NPC;
import entity.Player;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Save {

    /**
     * saves a map as a text file
     * @param filename
     * @param map
     * @throws IOException
     */
    public static void SaveMap(String filename, char[][] map) throws IOException {
        System.out.println("SAVING!");
        PrintWriter printWriter = new PrintWriter(new File(combine("gamesave", filename)));
        printWriter.print(map.length + " " );
        printWriter.print(map[0].length + " " );
        printWriter.println();


        for (int y = 0; y < map.length -1 ; y++) {
            for (int x = 0; x < map[0].length -1 ; x++) {
                printWriter.print(map[y][x]);
            }
            printWriter.println();
        }
        printWriter.close();
    }

    /**
     * saves a number to a file of a given name
     * using it store the number of entities for NPC, and Enemy
     * to count later on
     * @param filename
     * @param index
     * @throws IOException
     */
    public static void SaveNumber(String filename, int index) throws IOException{
        PrintWriter printWriter = new PrintWriter(combine("gamesave", filename+".txt"));
        printWriter.println(index);
        printWriter.close();
    }


    /**
     * Serializes an Player to the gamesave folder
     * @param entityCharacter
     * @param index
     */
    public static void SaveEntity(Player entityCharacter, int index) {
        FileOutputStream fout = null;
        ObjectOutput oos = null;
        try {
            fout = new FileOutputStream(combine("gamesave", "player")+index+".ser");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(entityCharacter);
            System.out.println("Entity was saved");
        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {

            if (fout != null) {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    /**
     * Serializes an NPC to the gamesave folder
     * @param entityCharacter
     * @param index
     */
    public static void SaveEntity(NPC entityCharacter, int index) {
        FileOutputStream fout = null;
        ObjectOutput oos = null;
        try {
            fout = new FileOutputStream(combine("gamesave", "npc")+index+".ser");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(entityCharacter);
            System.out.println("Entity was saved");
        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {

            if (fout != null) {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Serializes an Enemy to the gamesave folder
     * @param entityCharacter
     * @param index
     */
    public static void SaveEntity(Enemy entityCharacter, int index) {
        FileOutputStream fout = null;
        ObjectOutput oos = null;
        try {
            fout = new FileOutputStream(combine("gamesave", "enemy")+index+".ser");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(entityCharacter);
            System.out.println("Entity was saved");
        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {

            if (fout != null) {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
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
