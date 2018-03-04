import java.awt.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Rogue {
    private TextWindow textArea;
    private EntityManager em = new EntityManager();
    private Player player;
    private Scanner scanner;
    private boolean textVersion;
    private static int height, width;
    private MapManager mm;
    private Combat combat;
    DialogBox db;
    private KeyBinding kb = new KeyBinding();


    public Rogue() {

        init();
        scanner = new Scanner(System.in);
        textVersion = getVersionFromUser();
        if (textVersion) {
            textVersionLoop();
        } else {
            runGameLoop();
        }
    }


    /**
     * starts basic things required to run the game, console or gui
     */
    public void init() {
        mm = new MapManager(this);
        Rogue.height = mm.getMapLength();
        Rogue.width = mm.getMapHeight();
        mm.createMapEntities();

    }


    public boolean getVersionFromUser() {
        textVersion = false;
        scanner = new Scanner(System.in);
        System.out.println("play the text [yes/no]");

        if (scanner.hasNext()) {
            String in = scanner.nextLine();
            if (in.equalsIgnoreCase("yes")) {
                textVersion = true;
                textArea = new TextWindowConsole(height + 1, width + 1, this);
                combat = new CombatConsole(em);
            } else if (in.equalsIgnoreCase("no")) {
                textArea = new TextWindowGUI(height + 1, width + 1, this);
                combat = new CombatGUI(em);
                textVersion = false;
            }
        }
        return textVersion;
    }


    /**
     * game loop used for the text version of the game
     */
    public void textVersionLoop() {
        db = new DialogBoxConsole();
        textArea.clearConsole();
        mm.update();
        textArea.render(getMm().getEntityMap());
        db.render();
        System.out.println();

        while (textVersion) {
            if (scanner.hasNext()) {
                String action = scanner.nextLine();
                textPlayerControl(action);
                db.setStr(em.playerTalk());
                mm.update();
                em.update(mm.getCharacterMap());
                textArea.clearConsole();

                ArrayList<Enemy> inRange = combat.combatCheck();
                if (inRange.size() > 0) {
                    combat.startCombat(em.getPlayer(), inRange, action);
                    kb.combat = true;
                }
                kb.combat = false;
                mm.update();
                em.update(mm.getCharacterMap());
                textArea.render(getMm().getEntityMap());
                combat.render(em.getPlayer(), inRange);
                db.render();
                kb.print();

            }
        }
    }


    /**
     * recieves action from the console and moves the player by an increment
     */
    public void textPlayerControl(String action) {
        if (action.equalsIgnoreCase("w")) {
            em.movePlayer(0, -1, mm.getCharacterMap());
        } else if (action.equalsIgnoreCase("a")) {
            em.movePlayer(-1, 0, mm.getCharacterMap());
        } else if (action.equalsIgnoreCase("s")) {
            em.movePlayer(0, +1, mm.getCharacterMap());
        } else if (action.equalsIgnoreCase("d")) {
            em.movePlayer(1, 0, mm.getCharacterMap());
        } else if (action.equalsIgnoreCase("q")) {
            textVersion = false;
        }
        System.out.println(em.getPlayer().toString());
        em.update(mm.getCharacterMap());
        if(textVersion == false){
            gameStep();
        }
    }


    public void gameStep(){
        ((TextWindowGUI) textArea).getFrame().remove(((CombatGUI)combat).panel);
        ((CombatGUI)combat).setUpTabs(combat.combatCheck());
        ((TextWindowGUI) textArea).getFrame().add(((CombatGUI)combat).panel, BorderLayout.SOUTH);
    }


    /**
     * system to start the gui game loop
     */
    public void runGameLoop() {
        Thread loop = new Thread() {
            public void run() {
                gameLoopGUI();
            }
        };
        loop.start();
    }


    /**
     * gameloop for the gui
     */
    private void gameLoopGUI() {
        db = new DialogBoxGUI(getTextArea());
        ((TextWindowGUI) textArea).getFrame().add(((CombatGUI)combat).panel, BorderLayout.SOUTH);

        long now = System.currentTimeMillis();
        long delta = 0;
        while (true) {
            delta = System.currentTimeMillis() - now;
            now = System.currentTimeMillis();
            totalTime += delta;
            update(delta);
            renderGUI();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }


    /**
     * rendering the game
     */
    public void renderGUI() {
        textArea.render(getMm().getEntityMap());
        db.render();
    }

    double totalTime = 0.0;


    /**
     * this update method is used exclusively for the GUI
     *
     * @param delta
     */
    public void update(double delta) {
        mm.update();
        totalTime += delta / 1000000000;
        em.update(delta);
        db.setStr(em.playerTalk());



    }


    public TextWindow getTextArea() {
        return textArea;
    }

    public void setTextArea(TextWindow textArea) {
        this.textArea = textArea;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public DialogBox getDb() {
        return db;
    }

    public void setDb(DialogBox db) {
        this.db = db;
    }


    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean isTextVersion() {
        return textVersion;
    }

    public void setTextVersion(boolean textVersion) {
        this.textVersion = textVersion;
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

    public void setMm(MapManager mm) {
        this.mm = mm;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

}
