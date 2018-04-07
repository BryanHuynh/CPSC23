import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RogueGUI extends Rogue {
    private double totalTime = 0.0;
    private JFrame gui;


    public RogueGUI(int length, int height, int roomsize, int numOfEnemies, int numOfNpcs, JFrame frame) {
        super(length, height, roomsize, numOfEnemies, numOfNpcs);
        this.gui = frame;

        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.getContentPane().setBackground(Color.black);
        Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
        gui.setMaximumSize(DimMax);
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }


    /**
     * system to start the gui game loop
     */
    public void runGameLoop() {
        Thread loop = new Thread() {
            public void run() {
                try {
                    gameLoopGUI();
                } catch (InterruptedException e) {

                }
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
        running = true;
        loop.start();
    }

    /**
     * gameloop for the gui
     */
    private void gameLoopGUI() throws InterruptedException {
        long now = System.currentTimeMillis();
        long delta = 0;
        while (running) {
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
        gui.setVisible(false);
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
            BattleScreenGUI bs = new BattleScreenGUI(party, ((CombatGUI) combat).target, em);
            bs.getPanel().setBounds(0, 0, 1080, 720);
            gui.getContentPane().add(bs.getPanel(), BorderLayout.CENTER);
            bs.getPanel().grabFocus();
            bs.getPanel().requestFocus();
            gui.getContentPane().revalidate();
            bs.battle();
            ((CombatGUI) combat).battleState = false;
            gui.getContentPane().remove(bs.getPanel());
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
        gameStep();

    }


}
