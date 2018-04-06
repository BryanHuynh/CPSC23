import javax.swing.*;
import java.awt.*;

public class RogueGUI extends Rogue {
    private double totalTime = 0.0;
    private Window gui = new Window();


    public RogueGUI(int length, int height, int roomsize, int numOfEnemies, int numOfNpcs) {
        super(length, height, roomsize, numOfEnemies, numOfNpcs);
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
        mapDisplay = new MapDisplayGUI(height + 1, width + 1, this);
        party = new PartyGUI(em.getPlayer());
        combat = new CombatGUI(em, party);
        db = new DialogBoxGUI();
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setOpaque(true);
        panel.setLayout(new FlowLayout());
        panel.add(((PartyGUI) party).getPanel());
        panel.add(((CombatGUI) combat).panel);
        gui.add(panel, BorderLayout.LINE_START);
        gui.add(((DialogBoxGUI) db).getDialogPanel(), BorderLayout.LINE_END);
        gui.add(((MapDisplayGUI) mapDisplay).getMapPanel(), BorderLayout.CENTER);
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
     * this update method is used exclusively for the GUI
     *
     * @param delta
     */
    public void update(double delta) {
        gameEnd();
        mm.update();
        totalTime += delta / 1000000000;
        em.update(delta);
        db.setStr(em.playerTalk());
    }

    /**
     * rendering the game
     */
    public void renderGUI() {
        if (((CombatGUI) combat).battleState) {
            ((MapDisplayGUI) mapDisplay).getMapPanel().setVisible(false);
            ((CombatGUI) combat).battle(gui.getContentPane());
            mapDisplay.render(getMm().getEntityMap());
            gameStep();
        } else {
            ((MapDisplayGUI) mapDisplay).getMapPanel().setVisible(true);
            ((MapDisplayGUI) mapDisplay).getMapPanel().requestFocusInWindow();
        }
        db.render();
        party.render();
        mapDisplay.render(getMm().getEntityMap());
    }


    public void gameStep() {
        ((CombatGUI) combat).setUpTabs(combat.combatCheck());
        party.removeDeadMembers();
    }

    public void playerControl(String action) {
        super.playerControl(action);
        if (textVersion == false) {
            gameStep();
        }
    }


}
