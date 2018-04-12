package combat;

import entity.Enemy;
import entity.EntityManager;
import party.Party;
import party.PartyConsole;
import utils.Point;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class CombatGUI extends Combat {
    JPanel panel;
    private HashMap<Enemy, JTextPane> textPanes = new HashMap<>();
    private boolean battleState = false;
    private Enemy target = null;


    /**
     * mainly used to visually show the stats of enemies
     *
     * @param em
     * @param party
     */
    public CombatGUI(EntityManager em, Party party) {
        super(em, party);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
    }

    /**
     * sets up tabs that player can use to:
     * - see enemy stats
     * - engage in combat
     *
     * @param enemies
     */
    public void setUpTabs(ArrayList<Enemy> enemies) {
        panel.removeAll();

        for (Enemy enemy : enemies) {
            JTextPane pane = new JTextPane();
            if (textPanes.containsKey(enemy)) {
                pane = textPanes.get(enemy);
                pane.setText("");
            } else {
                textPanes.put(enemy, pane);
                pane.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        battleState = true;
                        target = enemy;
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
            }

            addEnemyStatsToPane(pane, enemy);
            pane.setBackground(Color.black);

            Action action = pane.getActionMap().get(DefaultEditorKit.beepAction);
            action.setEnabled(false);
            panel.add(pane);
        }

    }

    /**
     * adds enemy stats to a pane
     *
     * @param pane
     * @param enemy
     */
    public void addEnemyStatsToPane(JTextPane pane, Enemy enemy) {
        int padding = 15;
        pane.setEditable(true);

        appendToPane(pane, "|" + padLeft("ENEMY", 10 - 1) + "|", Color.orange);
        appendToPane(pane, "\n", Color.black);
        appendToPane(pane, padLeft("HP ", 10), Color.green);
        appendToPane(pane, "|" + padLeft(String.valueOf(enemy.getHp()), padding) + "|", Color.gray);

        appendToPane(pane, "\n", Color.black);
        appendToPane(pane, padLeft("attack ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(String.valueOf(enemy.getAtk()), padding) + "|", Color.gray);


        appendToPane(pane, "\n", Color.black);
        appendToPane(pane, padLeft("weapon ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(enemy.getWeapon(), padding) + "|", Color.gray);

        appendToPane(pane, "\n", Color.black);
        appendToPane(pane, padLeft("accuracy ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(String.valueOf(enemy.getAccuracy()) + "%", padding) + "|", Color.gray);
        appendToPane(pane, "\n", Color.black);


        utils.Point pt = new Point(enemy.getX() - em.getPlayer().getX(), em.getPlayer().getY() - enemy.getY());

        appendToPane(pane, padLeft("x ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(String.valueOf(pt.getX()), padding) + "|", Color.gray);

        appendToPane(pane, "\n", Color.black);

        appendToPane(pane, padLeft("y ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(String.valueOf(pt.getY()), padding) + "|", Color.gray);
        appendToPane(pane, "\n", Color.black);

        appendToPane(pane, "|" + padLeft("Click to start combat", padding) + "|", Color.gray);
        appendToPane(pane, "\n", Color.black);
        pane.setEditable(false);
    }

    /**
     * pads a string
     * @param s
     * @param n
     * @return
     */
    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    /**
     * adds action text to textpane
     * @param tp
     * @param msg
     * @param c
     */
    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Monospaced");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    public JPanel getPanel() {
        return panel;
    }

    public boolean getBattleState() {
        return battleState;
    }

    public void setBattleState(boolean battleState) {
        this.battleState = battleState;
    }

    public Enemy getTarget() {
        return target;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        EntityManager em = new EntityManager();
        em.createPlayer(0, 1);

        em.createEnemy(0, 0, 'e');
        em.createEnemy(1, 2, 'e');
        em.createEnemy(1, 3, 'e');
        Party party = new PartyConsole(em.getPlayer());         //to be changed
        CombatGUI cg = new CombatGUI(em, party);
        cg.setUpTabs(em.getEnemies());

        frame.add(cg.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}