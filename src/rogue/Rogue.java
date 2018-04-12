package rogue;

import battlescreen.BattleScreen;
import combat.Combat;
import dialogbox.DialogBox;
import entity.*;
import gameio.Save;
import map.MapDisplay;
import map.MapManager;
import party.Party;
import utils.KeyBinding;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Rogue extends Thread {
    protected MapDisplay mapDisplay;
    protected EntityManager em = new EntityManager();
    protected Player player;
    protected Scanner scanner;
    protected static int height, width;
    protected MapManager mm;
    protected Combat combat;
    protected Party party;
    protected DialogBox db;
    protected KeyBinding kb = new KeyBinding();
    protected BattleScreen bs;
    protected boolean running;


    /**
     * Rogue is a game that you control a player on a grid
     * you are tasked with defeating all the enemies and recruiting npcs along the journey to assist you
     *
     * @param length
     * @param height
     * @param roomsize
     * @param numOfEnemies
     * @param numOfNpcs
     */
    public Rogue(int length, int height, int roomsize, int numOfEnemies, int numOfNpcs) {
        init(length, height, roomsize, numOfEnemies, numOfNpcs);
    }

    public Rogue(char[][] map, ArrayList<Enemy> enemies, ArrayList<NPC> npcs, Player player) {
        mm = new MapManager(this, map);
        Rogue.height = mm.getMapLength() - 1;
        Rogue.width = mm.getMapHeight() - 1;
        mm.createMapEntities();                 // entities are created on the map

        em.setObstacles(new ArrayList<>());     //reset all list back to null due to side effect of mm.createMapEntities
        em.setEntities(new ArrayList<>());
        em.setNpcs(new ArrayList<>());
        em.setEnemies(new ArrayList<>());

        em.setPlayer(player);
        em.setEnemies(enemies);
        em.setNpcs(npcs);


        // combine list to add to entities
        ArrayList<Entity> entities = new ArrayList<>();
        entities.addAll(enemies);
        entities.addAll(npcs);
        entities.add(player);
        em.setEntities(entities);


        mm.createMapObstacles();
        mm.printMap();

    }

    /**
     * starts basic things required to run the main.game, console or gui
     */
    public void init(int length, int height, int roomsize, int numOfEnemies, int numOfNpcs) {
        mm = new MapManager(this, length, height, roomsize, numOfEnemies, numOfNpcs);
        Rogue.height = mm.getMapLength();
        Rogue.width = mm.getMapHeight();
        mm.createMapEntities();                 // entities are created on the map
        em.setPlayerStats(50, 75, 20);
    }


    boolean gameScreen = false;


    /**
     * if npc is near, sees if your action is to recruit
     *
     * @param action
     */
    public void recruitmentControl(String action) {
        NPC recruit = em.recuitment();
        if (recruit != null) {
            if (recruit.isVisable()) {
                System.out.println("Recruit available!");
                boolean recruited = party.lookForRecruitment(recruit, action);
                if (recruited) {
                    recruit.setVisable(false);
                    System.out.println("RECRUITED!");
                    mm.update();
                }
            }
        }
    }


    /**
     * recieves action from the console and moves the player by an increment
     */
    public void playerControl(String action) {
        if (action.equalsIgnoreCase("w")) {
            em.movePlayer(0, -1, mm.getCharacterMap());
        } else if (action.equalsIgnoreCase("a")) {
            em.movePlayer(-1, 0, mm.getCharacterMap());
        } else if (action.equalsIgnoreCase("s")) {
            em.movePlayer(0, +1, mm.getCharacterMap());
        } else if (action.equalsIgnoreCase("d")) {
            em.movePlayer(1, 0, mm.getCharacterMap());
        } else if (action.equalsIgnoreCase("q")) {
            System.exit(0);
        } else if (action.equalsIgnoreCase("save")) {
            try {
                for(File file: new File("gamesave").listFiles())
                    if (!file.isDirectory())
                        file.delete();
                Save.SaveMap("map.txt", mm.getCharacterMap());
                Save.SaveEntity(em.getPlayer(), 0);
                int index = 0;
                for (Enemy enemy : em.getEnemies()) {
                    Save.SaveEntity(enemy, index);
                    index++;
                }
                Save.SaveNumber("nEnemy",index);
                index = 0;
                for (NPC npc : em.getNpcs()) {
                    Save.SaveEntity(npc, index);
                    index++;
                }
                Save.SaveNumber("nNPC",index);

            } catch (IOException e) {

            }
        }
        recruitmentControl(action);
        em.update(mm.getCharacterMap());
    }





    public void gameEnd() {
        if (em.getPlayer().getHp() <= 0) {
            System.out.println("HERO IS DEAD. GAME OVER");
            running = false;
        }
        if (em.isAllEnemiesDead()) {
            running = false;
        }
    }


    public void setRunning(boolean running) {
        this.running = running;
    }

    public EntityManager getEm() {
        return em;
    }

    public static int getheight() {
        return height;
    }

    public static void setheight(int height) {
        Rogue.height = height;
    }

    public static int getwidth() {
        return width;
    }

    public static void setwidth(int width) {
        Rogue.width = width;
    }

    public MapManager getMm() {
        return mm;
    }

}
