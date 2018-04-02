import javafx.scene.control.Button;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class BattleScreenGUI extends JPanel {
    private Party party;
    private Enemy enemy;
    private ArrayList<CharacterMenu> characterMenus = new ArrayList<>();
    private JPanel center;
    private JPanel south;
    private JTextPane updateFeed = new JTextPane();
    private EntityManager em;
    /**
     * handles the interaction between party members and the enemy via combat
     * @param layout
     * @param isDoubleBuffered
     * @param party
     * @param enemy
     */
    public BattleScreenGUI(LayoutManager layout, boolean isDoubleBuffered, Party party, Enemy enemy, EntityManager em) {
        super(layout, isDoubleBuffered);
        this.setBackground(Color.black);
        this.em = em;
        this.party = party;
        this.enemy = enemy;
        center = new JPanel();
        center.setBackground(Color.black);
        this.setPreferredSize(new Dimension(500,720));
        center.setBounds(0, 0, 300, 300);
        this.add(center, BorderLayout.NORTH);
        this.add(updateFeed, BorderLayout.CENTER);

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

        this.add(south, BorderLayout.SOUTH);

    }

    private int turn = 0;
    private boolean wait = false;

    /**
     * loops through every character giving them a chance to act. the loop stops when the enemy is dead or the entire party
     * is eliminated
     */
    public void loop() {

        while (enemy.getHp() > 0) {
            if(party.isPartyDead()){
                return;
            }
            if (!wait) {
                JPanel sMenu = selectionMenu();
                System.out.println("TURN: " +  turn);
                if(party.getPartyList().get(turn).getHp() > 0){
                    characterMenus.get(turn).add(sMenu);
                    wait = true;
                }else{
                    System.out.println(party.getPartyList().get(turn).getName() + " is dead");
                    updateTurn();
                }

            }
            revalidate();
        }
        enemy.setVisable(false);
    }


    /**
     * updates the turn to the next character
     *
     */
    public void updateTurn(){
        turn++;
        if (turn > party.getPartyList().size() -1) {
            enemyTurn();
            update();
            turn = 0;
        }

    }


    /**
     * updates the gui with the updates of the party and enemy
     */
    public void update(){
        south.removeAll();
        characterMenus.removeAll(characterMenus);
        for (EntityCharacter member : party.getPartyList()) {
            CharacterMenu cm = new CharacterMenu(member);
            characterMenus.add(cm);
            south.add(cm);
        }
        south.revalidate();
        this.revalidate();
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
     * when all members have finished making their move. the Enemy can attack
     * also deals with enemy chance hit rate and blocking
     */
    public void enemyTurn(){
        Random rand = new Random();
        EntityCharacter target = party.getLivePartyMembers().get(rand.nextInt(party.getLivePartyMembers().size()));
        boolean isAttack = enemy.getChance();
        if(isAttack){
            if(!target.isBlocking()){
                System.out.println(target.getName() + " status of blocking: " + target.isBlocking());
                party.damageCharacter(target, enemy.getAtk());
                appendToPane(updateFeed, target.getName() + " was hit for " + enemy.getAtk(), Color.red);
                appendBlank(updateFeed);
                if(party.getMember(target.getName()).getHp() <= 0){
                    appendToPane(updateFeed, target.getName() + " IS DEAD!" , Color.red);
                    party.removeDeadMembers();
                    appendBlank(updateFeed);
                }
            }else{
                System.out.println(target.getName() + " Blocked the attack");
                appendToPane(updateFeed, target.getName() + " Blocked the attack", Color.green);
                appendBlank(updateFeed);
            }
        }else{
            System.out.println("Enemy missed.");
            appendToPane(updateFeed, "Enemy Missed.", Color.green);
            appendBlank(updateFeed);
        }
        party.resetBlock();
    }





    /**
     * a menu that attaches to the members to do actions like attacking, items and blocking
     * @return
     */
    public JPanel selectionMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(2, 2));
        menu.setBackground(Color.black);

        ActionListener attackEvent = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EntityCharacter member = party.getPartyList().get(turn);
                boolean isHit = member.getChance();
                if(isHit){
                    em.damageEnemy(enemy, member.getAtk());
                    appendToPane(updateFeed, member.getName() + " hit enemy for " + member.getAtk(), Color.green);
                    appendBlank(updateFeed);

                }else{
                    appendToPane(updateFeed, member.getName() + " missed", Color.RED);
                    appendBlank(updateFeed);
                }
                endWait();

            }
        };


        ActionListener itemEvent = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endWait();
            }
        };

        ActionListener blockEvent = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                party.setToBlock(turn);
                appendToPane(updateFeed, party.getPartyList().get(turn).getName() + " is blocking " , Color.gray);
                appendBlank(updateFeed);

                endWait();
            }
        };


        JButton attackButton = new JButton("Attack");
        JButton itemButton = new JButton("Items");
        JButton blockButton = new JButton("Block");
        setButtonAppearance(attackButton, Color.RED);
        setButtonAppearance(itemButton, Color.MAGENTA);
        setButtonAppearance(blockButton, Color.LIGHT_GRAY);
        attackButton.addActionListener(attackEvent);
        itemButton.addActionListener(itemEvent);
        blockButton.addActionListener(blockEvent);
        menu.add(attackButton);
        menu.add(itemButton);
        menu.add(blockButton);
        return menu;
    }


    /**
     * customize the button to be black. matching the appearance of the game
     * @param button
     * @param color
     */
    public void setButtonAppearance(JButton button, Color color){
        button.setBackground(Color.black);
        button.setBorder(BorderFactory.createLineBorder(Color.white));
        button.setForeground(color);
        button.setFocusPainted(false);}

    public class CharacterMenu extends JPanel {

        /**
         * a class that returns a JPanel consisting of the formatted player stats
         * @param character
         */
        CharacterMenu(EntityCharacter character) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.black);
            add(characterMenu(character));

        }

        /**
         * loads character stats onto a jtextpane
         * @param character
         * @return
         */
        public JTextPane characterMenu(EntityCharacter character) {
            JTextPane pane = new JTextPane();
            int padding = 15;
            appendToPane(pane, "|" + padLeft(character.getName(), 10 - 1) + "|", Color.orange);

            if(character.isDead){
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
     * @param s
     * @param n
     * @return
     */
    public String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public void appendBlank(JTextPane tp){
        appendToPane(tp, "\n", Color.black);
    }
    /**
     * adds text to a JTextPane
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


    /**
     * for debugging purposes and testing
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
        BattleScreenGUI bsg = new BattleScreenGUI(new BorderLayout(), true, party, enemy, em);
        frame.setSize(1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(bsg);
        frame.setVisible(true);
        bsg.loop();
    }


}
