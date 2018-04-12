import entity.Enemy;
import entity.EntityCharacter;
import entity.NPC;
import entity.Player;
import gameio.Load;
import mainmenu.MainMenuConsole;
import mainmenu.MainMenuGUI;
import rogue.RogueGUI;
import rogue.RogueText;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * starts rogue.Rogue
 */
public class Main {

    public static void main(String[] args) {
        boolean text = getVersionFromUser();
        initMainMenu(text);
    }


    /**
     * starts by opening the mainmenu that waits for users to input actions
     * has the capacity to load in games
     *
     * @param text
     */
    public static void initMainMenu(boolean text) {
        if (text) {
            MainMenuConsole mainMenu = new MainMenuConsole();

            mainMenu.start();
            synchronized (mainMenu) {
                try {
                    mainMenu.wait();
                } catch (InterruptedException e) {
                }
            }
            if (mainMenu.loadingFile) {
                Player player = Load.loadPlayer("gamesave/player" + 0 + ".ser");
                loadMainGameText(Load.mapLoad(), Load.loadEnemies(), Load.loadNPCS(), player);
            } else {
                loadMainGameText(mainMenu.getDifficulty());
            }

        } else {
            JFrame frame = new JFrame();
            MainMenuGUI mainMenu = new MainMenuGUI(1080, 720, frame);
            synchronized (mainMenu) {
                try {
                    System.out.println("waiting for user");
                    mainMenu.wait();
                } catch (InterruptedException e) {
                }
            }
            frame.getContentPane().removeAll();
            System.out.println("wait over");
            if (mainMenu.loadingFile) {
                System.out.println("loading file");
                Player player = Load.loadPlayer("gamesave/player" + 0 + ".ser");
                loadMainGameGUI(Load.mapLoad(), Load.loadEnemies(), Load.loadNPCS(), player, frame);

            } else {

                loadMainGameGUI(mainMenu.getDifficultySetting(), frame);
            }


        }
    }

    /**
     * loads the text version of the game. based on what was selected on main menu for difficulty the game will get harder accordingly
     *
     * @param difficulty
     */
    public static void loadMainGameText(int difficulty) {
        RogueText rogue;
        if (difficulty == 0) {
            rogue = new RogueText(50, 50, 10, 20, 6);
        } else if (difficulty == 1) {
            rogue = new RogueText(50, 50, 10, 35, 10);
        } else {
            rogue = new RogueText(50, 50, 10, 50, 10);
        }
        rogue.textVersionInit();
        rogue.setRunning(true);
        rogue.start();
        synchronized (rogue) {
            try {
                rogue.wait();
            } catch (InterruptedException e) {

            }

        }

        initMainMenu(true);
    }


    /**
     * loads the text version of the game. based on what was selected on main menu for difficulty the game will get harder accordingly
     */
    public static void loadMainGameText(char[][] map, ArrayList<Enemy> enemies, ArrayList<NPC> npcs, Player player) {
        RogueText rogue = new RogueText(map, enemies, npcs, player);
        rogue.textVersionInit();
        rogue.setRunning(true);
        rogue.start();
        synchronized (rogue) {
            try {
                rogue.wait();
            } catch (InterruptedException e) {

            }

        }
        initMainMenu(true);
    }

    /**
     * loads in an old save into the GUI
     *
     * @param map
     * @param enemies
     * @param npcs
     * @param player
     * @param frame
     */
    public static void loadMainGameGUI(char[][] map, ArrayList<Enemy> enemies, ArrayList<NPC> npcs, Player player, JFrame frame) {
        RogueGUI rogue = new RogueGUI(map, enemies, npcs, player, frame);
        rogue.initGame();
        rogue.start();
        synchronized (rogue) {
            try {
                rogue.wait();
            } catch (InterruptedException e) {

            }

        }
        initMainMenu(false);

    }

    /**
     * loads the GUI version of the game. based on what was selected on main menu for difficulty the game will get harder accordingly
     *
     * @param difficulty
     * @param frame
     */
    public static void loadMainGameGUI(int difficulty, JFrame frame) {

        RogueGUI rogue;
        if (difficulty == 0) {
            rogue = new RogueGUI(50, 50, 10, 20, 6, frame);
        } else if (difficulty == 1) {
            rogue = new RogueGUI(50, 50, 10, 35, 6, frame);
        } else {
            rogue = new RogueGUI(50, 50, 10, 50, 6, frame);
        }
        rogue.initGame();
        rogue.start();
        synchronized (rogue) {
            try {
                rogue.wait();
            } catch (InterruptedException e) {

            }

        }
        initMainMenu(false);
    }


    /**
     * gets the user to decide between console or gui
     *
     * @return
     */
    public static boolean getVersionFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("play the text [yes/no]");

        while (scanner.hasNext()) {
            String in = scanner.nextLine();
            if (in.equalsIgnoreCase("yes")) {
                return true;

            } else if (in.equalsIgnoreCase("no")) {
                return false;
            }
        }
        return false;
    }


}
