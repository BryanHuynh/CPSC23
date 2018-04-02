import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class PartyGUI extends Party {
    private JPanel panel;
    private HashMap<String, JTextPane> textPanes = new HashMap<>();

    /**
     * an extension of the party class. PartyGUI deals with representing the party with an organized and formatted representation
     * of the party members on a JPanel
     * @param player
     */
    public PartyGUI(Player player) {
        super(player);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.black);


    }

    /**
     * adds npc stats to the screen
     *
     * @param npc
     */
    public void addNPCTextArea(EntityCharacter npc) {
        JTextPane pane = new JTextPane();


        pane.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

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


        // add the stats to the pane
        int padding = 15;

        appendToPane(pane, "|" + padLeft(npc.getName(), 10 - 1) + "|", Color.orange);

        if(npc.isDead){
            appendToPane(pane, "DEAD! ", Color.red);
            appendToPane(pane, "\n", Color.black);
        }

        appendToPane(pane, "\n", Color.black);
        appendToPane(pane, padLeft("Health ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(String.valueOf(npc.getHp()), padding) + "|", Color.gray);


        appendToPane(pane, "\n", Color.black);
        appendToPane(pane, padLeft("attack ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(String.valueOf(npc.getAtk()), padding) + "|", Color.gray);


        appendToPane(pane, "\n", Color.black);
        appendToPane(pane, padLeft("weapon ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(npc.getWeapon(), padding) + "|", Color.gray);

        appendToPane(pane, "\n", Color.black);
        appendToPane(pane, padLeft("accuracy ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(String.valueOf(npc.getAccuracy()) + "%", padding) + "|", Color.gray);
        appendToPane(pane, "\n", Color.black);


        pane.setBackground(Color.black);
        pane.setEditable(false);
        pane.revalidate();
        panel.add(pane);
    }

    /**
     * to prevent the jTextPane from constantly setting text to nothing every loop. it checks too see if anything has a changed
     * that would require an update. recreates the new TextPane that would be the newest version and compares. writing to the pane doesn't take that long
     * but setting text to nothing and rewriting is noticeable
     *
     * @param npc
     * @param text
     * @return
     */
    public boolean doesTextAreaRequireUpdate(EntityCharacter npc, String text) {
        JTextPane pane = new JTextPane();
        int padding = 15;

        appendToPane(pane, "|" + padLeft(npc.getName(), 10 - 1) + "|", Color.orange);

        appendToPane(pane, "\n", Color.black);
        appendToPane(pane, padLeft("Health ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(String.valueOf(npc.getHp()), padding) + "|", Color.gray);


        appendToPane(pane, "\n", Color.black);
        appendToPane(pane, padLeft("attack ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(String.valueOf(npc.getAtk()), padding) + "|", Color.gray);


        appendToPane(pane, "\n", Color.black);
        appendToPane(pane, padLeft("weapon ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(npc.getWeapon(), padding) + "|", Color.gray);

        appendToPane(pane, "\n", Color.black);
        appendToPane(pane, padLeft("accuracy ", 10), Color.green);

        appendToPane(pane, "|" + padLeft(String.valueOf(npc.getAccuracy()) + "%", padding) + "|", Color.gray);
        appendToPane(pane, "\n", Color.black);

        if (pane.getText().equals(text)) {
            return false;
        }
        return true;
    }
    /**
     * adds text to a JTextPane
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


    /**
     * draws the things onto the JPanel
     */
    @Override
    public void render() {
        if (partyMembers.size() == 0) {
            return;
        }


        // loop through the npc's to add their things to the jpanel
        panel.removeAll();
        for (EntityCharacter npc : partyMembers) {
            addNPCTextArea(npc);
        }
        panel.revalidate();
    }


    public JPanel getPanel() {
        return panel;
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    /**
     * for debugging purposes
     * @param args
     */
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.black);
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new FlowLayout());

        EntityManager em = new EntityManager();
        em.createPlayer(0, 0);
        PartyGUI party = new PartyGUI(em.getPlayer());


        NPC npc = em.createNPC(5, 5, 'c');
        npc.setDead(true);

        party.addMember(npc);
        party.addMember(em.createNPC(5, 5, 'c'));
        party.addMember(em.createNPC(5, 5, 'c'));
        party.addMember(em.createNPC(5, 5, 'c'));
        party.addMember(em.createNPC(5, 5, 'c'));



        frame.add(party.getPanel());

        frame.pack();
        frame.setVisible(true);
        long period = 0;
        long now = System.currentTimeMillis();
        long delta = 0;
        boolean flag = true;
        while (true) {

            delta = System.currentTimeMillis() - now;
            now = System.currentTimeMillis();
            period += delta;
            party.render();


            if (period >= 1000) {
                party.removeMember(npc);
                party.damageCharacter(em.getPlayer(), 1);

                System.out.println("tick");
                flag = false;
                period = 0;

            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }


}
