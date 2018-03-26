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

    public BattleScreenGUI(LayoutManager layout, boolean isDoubleBuffered, Party party, Enemy enemy) {
        super(layout, isDoubleBuffered);
        this.party = party;
        this.enemy = enemy;
        center = new JPanel();
        center.setBounds(0, 0, 300, 300);
        this.add(center, BorderLayout.CENTER);
        center.add(new CharacterMenu(enemy));
        south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS));

        for (NPC npc : party.getPartyList()) {
            CharacterMenu cm = new CharacterMenu(npc);
            characterMenus.add(cm);
            south.add(cm);
        }

        this.add(south, BorderLayout.SOUTH);

    }

    private int turn = 0;
    private boolean wait = false;

    public void loop() {
        while (enemy.getHp() > 0) {
            if (!wait) {

                System.out.println("PARTY SIZE: " + party.getPartyList().size());

                JPanel sMenu = selectionMenu();
                System.out.println("TURN: " +  turn);
                characterMenus.get(turn).add(sMenu);
                wait = true;

            }
            revalidate();
        }
    }


    public void update(){
        south.removeAll();
        characterMenus.removeAll(characterMenus);

        for (NPC npc : party.getPartyList()) {
            System.out.println(padLeft(""+ npc.getHp(), 15));
            CharacterMenu cm = new CharacterMenu(npc);
            characterMenus.add(cm);
            south.add(cm);
        }
        south.revalidate();
        this.revalidate();
    }

    public void endWait() {
        characterMenus.get(turn).remove(1);
        turn++;
        if (turn > party.getPartyList().size() -1) {
            enemyTurn();
            //party.removeDeadMembers();
            update();
            turn = 0;
        }
        center.remove(0);
        center.add(new CharacterMenu(enemy));

        wait = false;
    }


    public void enemyTurn(){
        Random rand = new Random();
        EntityCharacter target = party.getPartyList().get(rand.nextInt(party.getPartyList().size()));
        boolean isAttack = enemy.getChance();
        if(isAttack){
            party.damageCharacter(target, enemy.getAtk());
            System.out.println("" + target.getName() + " was attacked for " + enemy.getAtk() + ".");
            System.out.println("" + target.getName() + " HP is now " + target.getHp() + ".");
        }else{
            System.out.println("Enemy missed.");
        }
    }

    public String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public JPanel selectionMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(2, 2));
        ActionListener attackEvent = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enemy.damage(party.getPartyList().get(turn).getAtk());
                endWait();

            }
        };

        ActionListener runEvent = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enemy.damage(party.getPartyList().get(turn).getAtk());
                endWait();

            }
        };

        ActionListener itemEvent = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enemy.damage(party.getPartyList().get(turn).getAtk());
                endWait();
            }
        };

        JButton attackButton = new JButton("|Attack|");
        JButton itemButton = new JButton("|items|");
        JButton runButton = new JButton("|run|");
        JButton blockButton = new JButton("|block|");
        attackButton.addActionListener(attackEvent);
        itemButton.addActionListener(itemEvent);
        runButton.addActionListener(runEvent);

        menu.add(attackButton);

        menu.add(itemButton);
        menu.add(runButton);
        menu.add(blockButton);
        return menu;
    }

    public class CharacterMenu extends JPanel {

        CharacterMenu(EntityCharacter character) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(characterMenu(character));

        }

        public JTextPane characterMenu(EntityCharacter character) {
            JTextPane pane = new JTextPane();
            int padding = 15;
            appendToPane(pane, "|" + padLeft(character.getName(), 10 - 1) + "|", Color.orange);


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


        public String padLeft(String s, int n) {
            return String.format("%1$" + n + "s", s);
        }

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
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        EntityManager em = new EntityManager();
        em.createPlayer(0, 0);

        Party party = new PartyGUI(em.getPlayer());
        party.addMember(em.createNPC(0, 0, 'c'));
        party.addMember(em.createNPC(0, 0, 'c'));

        party.addMember(em.createNPC(0, 0, 'c'));
        party.addMember(em.createNPC(0, 0, 'c'));
        Enemy enemy = em.createEnemy(0, 0, 'e');
        enemy.setHp(10000);

        BattleScreenGUI bsg = new BattleScreenGUI(new BorderLayout(), true, party, enemy);
        frame.setSize(1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(bsg);
        frame.setVisible(true);
        bsg.loop();
    }


}
