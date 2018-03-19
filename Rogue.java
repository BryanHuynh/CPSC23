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
    private Party party;
    DialogBox db;
    private KeyBinding kb = new KeyBinding();


    public Rogue() {

        init();
        scanner = new Scanner(System.in);
        textVersion = getVersionFromUser();
        if (textVersion) {
            textVersionInit();
            gameScreen = true;
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
        mm.createMapEntities();                 // entities are created on the map

    }


    public boolean getVersionFromUser() {
        textVersion = false;
        scanner = new Scanner(System.in);
        System.out.println("play the text [yes/no]");

        if (scanner.hasNext()) {
            String in = scanner.nextLine();
            if (in.equalsIgnoreCase("yes")) {
                textVersion = true;

            } else if (in.equalsIgnoreCase("no")) {
                textArea = new TextWindowGUI(height + 1, width + 1, this);
                combat = new CombatGUI(em, party);
                textVersion = false;
            }
        }
        return textVersion;
    }


    /**
     * game loop used for the text version of the game
     */
    public void textVersionInit() {
        party = new PartyConsole(em.getPlayer());
        textArea = new TextWindowConsole(height + 1, width + 1, this);
        combat = new CombatConsole(em, party);
        db = new DialogBoxConsole();            // create intance of dialogbox to console
        textArea.clearConsole();                //clear the screen
        mm.update();
        textArea.render(getMm().getEntityMap());//render the screen to show the map
        db.render();                            //render the dialog box
        System.out.println();
    }

    boolean inventoryScreen = false;
    boolean gameScreen = false;
    boolean battleScreen = false;

    public void textVersionLoop() {
        while (textVersion) {

            while (gameScreen) {
                ArrayList<Enemy> inRange = combat.combatCheck();
                textArea.render(getMm().getEntityMap());//render the screen to show the map
                db.render();                            //render the dialog box
                if(inRange.size() > 0){
                    System.out.println("ENEMY IN RANGE!");
                    ((CombatConsole) combat).render(em.getPlayer(), inRange);
                }

                party.render();                 //render the party members stats

                System.out.println("Enemies in range: " + inRange.size());
                System.out.println();

                kb.print();                     //keyboard instructions
                if (scanner.hasNext()) {            //check if there is a command
                    String action = scanner.nextLine(); //save command to action variable
                    if (action.equalsIgnoreCase("i")) {
                        gameScreen = false;
                        inventoryScreen = true;
                        break;
                    } else if (inRange.size() > 0 && isNumeric(action)) { //start combat if enemy in range and action is a number
                        if (Integer.valueOf(action) < inRange.size()) {   //does the enemy exist to the corresponding number
                            ((CombatConsole) combat).battle(inRange.get(Integer.valueOf(action)));
                        }
                    }

                    textPlayerControl(action);      //move the character if it is applicable
                    db.setStr(em.playerTalk());     //set the dialog box if there is chance for player to talk to NPC
                    mm.update();                    // update the map
                    em.update(mm.getCharacterMap());//update the entity manager with the new map
                    textArea.clearConsole();        //clear the screen

                    //recruitmentControl(action);           // look if there is NPC to recruit;


                    mm.update();
                    em.update(mm.getCharacterMap());

                }
            }
            while (inventoryScreen) {
                if (scanner.hasNext()) {
                    String action = scanner.nextLine(); //save command to action variable
                    if (action.equalsIgnoreCase("exit")) {
                        gameScreen = true;
                        inventoryScreen = false;

                        break;
                    }
                }

            }
        }


    }

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
            gameScreen = false;
            inventoryScreen = false;
            return;
        }
        recruitmentControl(action);
        System.out.println(em.getPlayer().toString());
        em.update(mm.getCharacterMap());
        if (textVersion == false) {
            gameStep();
        }
    }


    public void gameStep() {
        ((TextWindowGUI) textArea).getFrame().remove(((CombatGUI) combat).panel);
        ((CombatGUI) combat).setUpTabs(combat.combatCheck());
        ((TextWindowGUI) textArea).getFrame().add(((CombatGUI) combat).panel, BorderLayout.SOUTH);
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
        party = new PartyGUI(em.getPlayer());
        db = new DialogBoxGUI(getTextArea());

        ((TextWindowGUI) textArea).getFrame().add((((PartyGUI) party).getPanel()), BorderLayout.LINE_START);
        loop.start();
    }


    /**
     * gameloop for the gui
     */
    private void gameLoopGUI() {
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
        party.render();
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
