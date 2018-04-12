package rogue;

import battlescreen.BattleScreenGUI;
import combat.CombatGUI;
import dialogbox.DialogBoxGUI;
import entity.Enemy;
import entity.NPC;
import entity.Player;
import map.MapDisplayGUI;
import party.PartyGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RogueGUI extends Rogue implements ActionListener{
    private double totalTime = 0.0;
    private JFrame gui;
    private JButton save = new JButton("save");

    /**
     * *  Rogue is a game that you control a player on a grid
     *  you are tasked with defeating all the enemies and recruiting npcs along the journey to assist you
     *  the game is visually represented through graphics
     * @param length
     * @param height
     * @param roomsize
     * @param numOfEnemies
     * @param numOfNpcs
     * @param frame
     */
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
     * this constructor is used for saved games and loading them back in
     * @param map
     * @param enemies
     * @param npcs
     * @param player
     * @param frame
     */
    public RogueGUI(char[][] map, ArrayList<Enemy> enemies, ArrayList<NPC> npcs, Player player, JFrame frame){
        super(map, enemies, npcs, player);
        this.gui = frame;
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.getContentPane().setBackground(Color.black);
        Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
        gui.setMaximumSize(DimMax);
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }


    /**
     * starts the game looping
     */
    @Override
    public void run() {
        long now = System.currentTimeMillis();
        long delta = 0;
        while (running) {
            gameEnd();
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
     * system to start the gui main.game loop
     */
    public void initGame() {
        mapDisplay = new MapDisplayGUI(height + 1, width + 1, this);
        party = new PartyGUI(em.getPlayer());
        combat = new CombatGUI(em, party);
        db = new DialogBoxGUI();
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setOpaque(true);
        panel.setLayout(new FlowLayout());
        panel.add(((PartyGUI) party).getPanel());
        panel.add(((CombatGUI) combat).getPanel());
        gui.add(save, BorderLayout.NORTH);
        save.addActionListener(this);
        gui.add(panel, BorderLayout.LINE_START);
        gui.add(((DialogBoxGUI) db).getDialogPanel(), BorderLayout.LINE_END);
        gui.add(((MapDisplayGUI) mapDisplay).getMapPanel(), BorderLayout.CENTER);
        running = true;
        for (NPC npc : em.getNpcs()) {
            if (!npc.isVisable()) {
                party.addMember(npc);
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
     * rendering the main.game
     */
    public void renderGUI() {
        if (((CombatGUI) combat).getBattleState()) {
            ((MapDisplayGUI) mapDisplay).getMapPanel().setVisible(false);
            BattleScreenGUI bs = new BattleScreenGUI(party, ((CombatGUI) combat).getTarget(), em);
            bs.getPanel().setBounds(0, 0, 1080, 720);
            gui.getContentPane().add(bs.getPanel(), BorderLayout.CENTER);
            bs.getPanel().grabFocus();
            bs.getPanel().requestFocus();
            gui.getContentPane().revalidate();
            bs.battle();
            ((CombatGUI) combat).setBattleState(false);
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


    /**
     * everytime the player makes a move, the game should do a step
     */
    public void gameStep() {
        ((CombatGUI) combat).setUpTabs(combat.combatCheck());
        party.removeDeadMembers();
    }

    /**
     * sends player move to see if user action does anything
     * causes a gamestep
     * @param action
     */
    public void playerControl(String action) {
        super.playerControl(action);
        gameStep();
    }


    /**
     * action event listener just for the save function
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == save){
            System.out.println("saving");
            super.playerControl("save");
        }
    }
}
