import javax.swing.*;
import java.util.Scanner;

/**
 * starts Rogue
 */
public class Main {

    public static void main(String[] args) {
        boolean text = getVersionFromUser();
        initMainMenu(text);
    }


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

            loadMainGameText(mainMenu.getDifficulty());

        } else {
            JFrame frame = new JFrame();
            MainMenuGUI mainMenu = new MainMenuGUI(1080, 720, frame);
            synchronized (mainMenu) {
                try {
                    System.out.println("waiting for user");
                    mainMenu.wait();
                } catch (InterruptedException e) {
                }
                frame.getContentPane().removeAll();
                System.out.println("wait over");
                loadMainGameGUI(mainMenu.getDifficultySetting(), frame);
            }
        }
    }

    public static void loadMainGameText(int difficulty) {
        RogueText rogue;
        if (difficulty == 0) {
            rogue = new RogueText(50, 50, 10, 20, 6);
        } else if (difficulty == 1) {
            rogue = new RogueText(50, 50, 10, 35, 6);
        } else {
            rogue = new RogueText(50, 50, 10, 50, 6);
        }
        rogue.textVersionInit();
        rogue.running = true;

        synchronized (rogue) {
            try {
                rogue.wait();
            } catch (InterruptedException e) {

            }
        }
        initMainMenu(true);
    }


    public static void loadMainGameGUI(int difficulty, JFrame frame) {

        RogueGUI rogue;
        if (difficulty == 0) {
            rogue = new RogueGUI(50, 50, 10, 20, 6, frame);
        } else if (difficulty == 1) {
            rogue = new RogueGUI(50, 50, 10, 35, 6, frame);
        } else {
            rogue = new RogueGUI(50, 50, 10, 50, 6, frame);
        }

        rogue.runGameLoop();
        synchronized (rogue) {
            try {
                rogue.wait();
            } catch (InterruptedException e) {

            }

        }
        initMainMenu(false);
    }


    public static boolean getVersionFromUser() {
        boolean textVersion = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("play the text [yes/no]");

        if (scanner.hasNext()) {
            String in = scanner.nextLine();
            if (in.equalsIgnoreCase("yes")) {
                textVersion = true;

            } else if (in.equalsIgnoreCase("no")) {
                textVersion = false;
            }
        }
        return textVersion;
    }


}
