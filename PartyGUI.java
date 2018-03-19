import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PartyGUI extends Party {
    private JPanel panel;
    private HashMap<String, JTextPane> textPanes = new HashMap<>();

    public PartyGUI(Player player) {
        super(player);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.black);


    }

    /**
     * adds npc stats to the screen
     * @param npc
     */
    public void addNPCTextArea(NPC npc) {
        JTextPane pane = new JTextPane();

        if(!textPanes.containsKey(npc.getName())){  //check if there already exist a JTextPane for an npc. Prevents from constantly making new TextPanes
            textPanes.put(npc.getName(), pane);
            panel.add(pane);
        }else{
            pane = textPanes.get(npc.getName());    //if there is an associated pane to an npc, just reuse
            if(doesTextAreaRequireUpdate(npc, pane.getText())){ //check if there's anything that would require an update to be made
                pane.setText("");
            }else{
                return;                             // if there is no update required then just leave
            }

        }

        // add the stats to the pane
        int padding = 15;

        appendToPane(pane,"|" + padLeft(npc.getName(), 10-1) + "|",Color.orange);

        appendToPane(pane,"\n", Color.black);
        appendToPane(pane,padLeft("attack ", 10),Color.green);

        appendToPane(pane,"|" + padLeft(String.valueOf(npc.getAtk()), padding) + "|", Color.gray);


        appendToPane(pane,"\n", Color.black);
        appendToPane(pane,padLeft("weapon ", 10),Color.green);

        appendToPane(pane,"|" + padLeft(npc.getWeapon(), padding) + "|", Color.gray);

        appendToPane(pane,"\n", Color.black);
        appendToPane(pane,padLeft("accuracy ", 10),Color.green);

        appendToPane(pane,"|" + padLeft(String.valueOf(npc.getAccuracy()) + "%", padding) + "|", Color.gray);
        appendToPane(pane,"\n", Color.black);
        pane.setBackground(Color.black);
        pane.setEditable(false);
    }

    /**
     * to prevent the jTextPane from constantly setting text to nothing every loop. it checks too see if anything has a changed
     * that would require an update. recreates the new TextPane that would be the newest version and compares. writing to the pane doesn't take that long
     * but setting text to nothing and rewriting is noticeable
     * @param npc
     * @param text
     * @return
     */
    public boolean doesTextAreaRequireUpdate(NPC npc, String text ){
        JTextPane pane = new JTextPane();
        int padding = 15;

        appendToPane(pane,"|" + padLeft(npc.getName(), 10-1) + "|",Color.green);

        appendToPane(pane,"\n", Color.black);
        appendToPane(pane,padLeft("attack ", 10),Color.green);

        appendToPane(pane,"|" + padLeft(String.valueOf(npc.getAtk()), padding) + "|", Color.black);


        appendToPane(pane,"\n", Color.black);
        appendToPane(pane,padLeft("weapon ", 10),Color.green);

        appendToPane(pane,"|" + padLeft(npc.getWeapon(), padding) + "|", Color.black);

        appendToPane(pane,"\n", Color.black);
        appendToPane(pane,padLeft("accuracy ", 10),Color.black);

        appendToPane(pane,"|" + padLeft(String.valueOf(npc.getAccuracy()) + "%", padding) + "|", Color.black);
        appendToPane(pane,"\n", Color.black);

        if(pane.getText().equals(text)){
            return false;
        }
        return true;
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


    @Override
    public void render() {
        ArrayList<NPC> partyList = super.getPartyList();
        if (partyList.size() == 0) {
            return;
        }

        //if a party member is removed, then the entire map is erased and all current existing members are re-added
        if(partyList.size() < textPanes.size()){
            textPanes = new HashMap<String, JTextPane>();
            panel.removeAll();
        }

        // loop through the npc's to add their things to the jpanel
        for(NPC npc: partyList){
            addNPCTextArea(npc);

        }
    }


    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

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
        while (true) {
            party.render();
            delta = System.currentTimeMillis() - now;
            now = System.currentTimeMillis();
            period += delta;
            if(period >= 5000){
                party.removeMember(npc);
                System.out.println("npc Removed");
                period = 0;
            }

            try {
                    Thread.sleep(1000);
            } catch (Exception e) {
            }
        }

    }
}
