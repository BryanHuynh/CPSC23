package mainmenu;

import java.util.Scanner;

public class MainMenuConsole extends Thread implements Runnable {

    private int difficulty = -1;    // how hard the game will be
    private Scanner scanner;
    public boolean loadingFile = false;

    /**
     * handles the main menu to set up the game
     * visually through console
     */
    public MainMenuConsole() {
    }


    @Override
    public void run() {
        System.out.println("Rouge Main Menu : ");
        System.out.println();

        scanner = new Scanner(System.in);
        System.out.println("Start ");
        System.out.println("Load  ");
        System.out.println("Exit ");
        System.out.println("Make A Selection : [Start, Load, Exit]");
        while (scanner.hasNext()) {

            String in = scanner.nextLine();
            if (in.equalsIgnoreCase("load")) {
                loadingFile = true;
                return;
            } else if (in.equalsIgnoreCase("Start")) {
                System.out.println("Difficult:  ");
                System.out.println("Easy ");
                System.out.println("Medium ");
                System.out.println("Hard ");

                in = scanner.nextLine();
                if (in.equalsIgnoreCase("easy")) {

                    synchronized (this) {
                        difficulty = 0;
                        System.out.println("notify");
                        this.notify();
                        return;
                    }
                } else if (in.equalsIgnoreCase("medium")) {
                    synchronized (this) {
                        difficulty = 1;
                        System.out.println("notify");
                        this.notify();
                        return;
                    }
                } else if (in.equalsIgnoreCase("hard")) {
                    synchronized (this) {
                        difficulty = 2;
                        this.notify();
                        return;
                    }
                }
            } else if (in.equalsIgnoreCase("Exit")) {
                System.exit(0);
            }
            System.out.println("Start ");
            System.out.println("Load  ");
            System.out.println("Exit ");
            System.out.println("Make A Selection : [Start, Load, Exit]");
        }
    }

    public int getDifficulty() {
        return difficulty;
    }

    public static void main(String args[]) {
        MainMenuConsole m = new MainMenuConsole();
        m.run();
    }

}
