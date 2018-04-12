package battlescreen;

import entity.Enemy;
import entity.EntityCharacter;
import entity.EntityManager;
import party.Party;
import party.PartyGUI;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class BattleScreenGUI extends battlescreen.BattleScreen {
    private JPanel panel = new JPanel(new BorderLayout(), true);
    private ArrayList<CharacterMenu> characterMenus = new ArrayList<>();
    private JPanel center;
    private JPanel south;
    private JTextPane updateFeed = new JTextPane();
    private int turn = 0;           // used to keep track of whose run it is
    private boolean wait = false;   // used to see if member has made move yet

    /**
     * battle screen deals with the combat involved between a single enemy and a party
     * displayed visually through a graphics
     *
     * @param party
     * @param enemy
     */
    public BattleScreenGUI(Party party, Enemy enemy, EntityManager em) {
        super(party, enemy, em);
        panel.setBackground(Color.black);
        this.em = em;
        this.party = party;
        this.enemy = enemy;
        center = new JPanel();
        center.setBackground(Color.black);
        panel.add(center, BorderLayout.NORTH);
        panel.add(updateFeed, BorderLayout.CENTER);

        updateFeed.setBackground(Color.BLACK);
        center.add(new CharacterMenu(enemy));
        south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS));
        System.out.println("PARTY SIZE: " + party.getPartyList().size());
        for (EntityCharacter member : party.getPartyList()) {
            CharacterMenu cm = new CharacterMenu(member);
            characterMenus.add(cm);
            south.add(cm);
        }

        panel.add(south, BorderLayout.SOUTH);
    }


    @Override
    public void battle() {
        loop();
    }

    /**
     * loops through every character giving them a chance to act. the loop stops when the enemy is dead or the entire party
     * is eliminated
     */
    public void loop() {

        while (enemy.getHp() > 0 && !ran && party.getPlayer().getHp() > 0 && !party.isPartyDead()) {
            if (!wait) {
                JPanel sMenu = selectionMenu();
                System.out.println("TURN: " + turn);
                if (party.getPartyList().get(turn).getHp() > 0) {
                    characterMenus.get(turn).add(sMenu);
                    wait = true;
                } else {
                    System.out.println(party.getPartyList().get(turn).getName() + " is dead");
                    updateTurn();
                }

            }
            panel.revalidate();
        }
        if (enemy.getHp() <= 0) {
            enemy.setVisable(false);
            party.teamRestoreHealth(enemy.getMaxHp() / 2 / party.getLivePartyMembers().size());
        }
    }


    /**
     * updates the turn to the next character
     */
    public void updateTurn() {
        turn++;
        if (turn > party.getPartyList().size() - 1) {
            enemyTurn();
            update();
            turn = 0;
        }

    }


    /**
     * updates the gui with the updates of the party and enemy
     */
    public void update() {
        south.removeAll();
        characterMenus.removeAll(characterMenus);
        for (EntityCharacter member : party.getPartyList()) {
            CharacterMenu cm = new CharacterMenu(member);
            characterMenus.add(cm);
            south.add(cm);
        }
        south.revalidate();
        panel.revalidate();
    }

    /**
     * event that occurs when the user has clicked the option for the member. also updates turn to next member
     */
    public void endWait() {
        characterMenus.get(turn).remove(1);
        updateTurn();
        center.remove(0);
        center.add(new CharacterMenu(enemy));

        wait = false;
    }


    /**
     * when all members have finished making their move. the entity.Enemy can attack
     * also deals with enemy chance hit rate and blocking
     */
    public void enemyTurn() {
        super.enemyTurn();
        appendToPane(updateFeed, feed + "\n", isGoodNews);
        party.resetBlock();
    }


    /**
     * a menu that attaches to the members to do actions like attacking, skips and blocking
     * this itself method does not do any logical work, instead it only handles user input
     * @return
     */
    public JPanel selectionMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(2, 2));
        menu.setBackground(Color.black);

        ActionListener attackEvent = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action("a", party.getPartyList().get(turn));
                appendToPane(updateFeed, feed + "\n", isGoodNews);
                endWait();

            }
        };


        ActionListener skipEvent = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endWait();
            }
        };


        ActionListener runEvent = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action("r", party.getPartyList().get(turn));
                if (!ran) appendToPane(updateFeed, feed, isGoodNews);
                endWait();
            }
        };

        ActionListener blockEvent = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action("b", party.getPartyList().get(turn));
                appendToPane(updateFeed, feed, isGoodNews);
                appendBlank(updateFeed);
                endWait();
            }
        };

        JButton runButton = new JButton("Run");
        JButton attackButton = new JButton("Attack");
        JButton skipButton = new JButton("Skip");
        JButton blockButton = new JButton("Block");
        setButtonAppearance(attackButton, Color.RED);
        setButtonAppearance(skipButton, Color.MAGENTA);
        setButtonAppearance(blockButton, Color.LIGHT_GRAY);
        setButtonAppearance(runButton, Color.WHITE);
        runButton.addActionListener(runEvent);
        attackButton.addActionListener(attackEvent);
        skipButton.addActionListener(skipEvent);
        blockButton.addActionListener(blockEvent);
        menu.add(attackButton);
        menu.add(skipButton);
        menu.add(blockButton);
        menu.add(runButton);
        return menu;
    }


    /**
     * customize the button to be black. matching the appearance of the main.game
     *
     * @param button
     * @param color
     */
    public void setButtonAppearance(JButton button, Color color) {
        button.setBackground(Color.black);
        button.setBorder(BorderFactory.createLineBorder(Color.white));
        button.setForeground(color);
        button.setFocusPainted(false);
    }

    public class CharacterMenu extends JPanel {

        /**
         * a class that returns a JPanel consisting of the formatted player stats
         *
         * @param character
         */
        CharacterMenu(EntityCharacter character) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.black);
            add(characterMenu(character));

        }

        /**
         * loads character stats onto a jtextpane
         *
         * @param character
         * @return
         */
        public JTextPane characterMenu(EntityCharacter character) {
            JTextPane pane = new JTextPane();
            int padding = 15;
            appendToPane(pane, "|" + padLeft(character.getName(), 10 - 1) + "|", Color.orange);

            if (character.isDead()) {
                appendToPane(pane, " DEAD! ", Color.red);
                appendToPane(pane, "\n", Color.black);
            }


            appendToPane(pane, "\n", Color.black);
            appendToPane(pane, padLeft("HP ", 10), Color.green);

            appendToPane(pane, "|" + padLeft(String.valueOf(character.getHp()), padding) + "|", Color.gray);

            appendToPane(pane, "\n", Color.black);
            appendToPane(pane, padLeft("attack ", 10), Color.green);

            appendToPane(pane, "|" + padLeft(String.valueOf(character.getAtk()), padding) + "|", Color.gray);


            appendToPane(pane, "\n", Color.black);
            appendToPane(pane, padLeft("weapon ", 10), Color.green);

            appendToPane(pane, "|" + padLeft(character.getWeapon(), padding) + "|", Color.gray);

            appendToPane(pane, "\n", Color.black);
            appendToPane(pane, padLeft("accuracy ", 10), Color.green);

            appendToPane(pane, "|" + padLeft(String.valueOf(character.getAccuracy()) + "%", padding) + "|", Color.gray);
            appendToPane(pane, "\n", Color.black);
            pane.setBackground(Color.black);
            pane.setEditable(false);
            return pane;
        }


    }

    /**
     * moves everything to the left in a string with padding
     *
     * @param s
     * @param n
     * @return
     */
    public String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public void appendBlank(JTextPane tp) {
        appendToPane(tp, "\n", Color.black);
    }

    /**
     * adds text to a JTextPane
     *
     * @param tp
     * @param msg
     * @param c
     */
    public void appendToPane(JTextPane tp, String msg, Color c) {
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


    /**
     * for debugging purposes and testing
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        EntityManager em = new EntityManager();
        em.createPlayer(0, 0);

        Party party = new PartyGUI(em.getPlayer());
        party.addMember(em.createNPC(0, 0, 'c'));

        party.addMember(em.createNPC(0, 0, 'c'));
        Enemy enemy = em.createEnemy(0, 0, 'e');

        party.addMember(em.createNPC(0, 0, 'c'));

        party.addMember(em.createNPC(0, 0, 'c'));
        enemy.setHp(100);
        enemy.setAccuracy(75);
        enemy.setAtk(100);
        BattleScreenGUI bsg = new BattleScreenGUI(party, enemy, em);
        frame.setSize(1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(bsg.getPanel());
        frame.setVisible(true);
        bsg.loop();
    }


}
