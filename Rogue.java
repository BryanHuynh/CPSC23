import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class Rogue {
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


    public Rogue(int length, int height, int roomsize, int numOfEnemies, int numOfNpcs) {
        init(length, height, roomsize, numOfEnemies, numOfNpcs);
    }


    /**
     * starts basic things required to run the game, console or gui
     */
    public void init(int length, int height, int roomsize, int numOfEnemies, int numOfNpcs) {
        mm = new MapManager(this, length, height, roomsize, numOfEnemies, numOfNpcs);
        Rogue.height = mm.getMapLength();
        Rogue.width = mm.getMapHeight();
        mm.createMapEntities();                 // entities are created on the map
        em.getPlayer().setHp(10);
        em.getPlayer().setAccuracy(95);
    }



    boolean gameScreen = false;


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


    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
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
            gameScreen = false;
        }
        recruitmentControl(action);
        em.update(mm.getCharacterMap());
    }


    public void gameEnd(){
        if(em.getPlayer().getHp() <= 0){
            System.out.println("HERO IS DEAD. GAME OVER");
            running = false;
            synchronized (this){
                System.out.println("Notified");
                notify();
            }

        }
    }

    public boolean isRunning() {
        return running;
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
