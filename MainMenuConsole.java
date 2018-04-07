import java.util.Scanner;

public class MainMenuConsole extends Thread implements Runnable {

    private int difficulty = -1;
    private Scanner scanner;

    public MainMenuConsole() {
    }


    @Override
    public void run() {
        System.out.println("Rouge Main Menu : ");
        System.out.println();
        System.out.println("Start ");
        System.out.println("Read Me ");
        System.out.println("Exit ");
        scanner = new Scanner(System.in);
        System.out.println("Make A Selection : [Start, Read Me, Exit]");

        String in = scanner.nextLine();
        if (in.equalsIgnoreCase("Start")) {
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
                }
            }
            if (in.equalsIgnoreCase("medium")) {
                synchronized (this) {
                    difficulty = 1;
                    System.out.println("notify");
                    this.notify();
                }
            }
            if (in.equalsIgnoreCase("hard")) {
                synchronized (this) {
                    difficulty = 2;
                    this.notify();
                }
            }


        } else if (in.equalsIgnoreCase("Read Me")) {
            run();
        } else if (in.equalsIgnoreCase("Exit")) {

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
