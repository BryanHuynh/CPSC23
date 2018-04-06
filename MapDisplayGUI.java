import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class MapDisplayGUI extends MapDisplay implements KeyListener {
    private ArrayList<JTextPane> tPanes = new ArrayList<>();
    private JPanel mapPanel = new JPanel();

    public MapDisplayGUI(int height, int width, Rogue rogue) {
        super(height, width, rogue);
        initGUI();

    }

    public void keyPressed(KeyEvent e) {
        System.out.println((char) (e.getKeyCode()));
        getRogue().playerControl(String.valueOf((char) (e.getKeyCode())));
    }


    public void keyReleased(KeyEvent e) {


    }

    public void keyTyped(KeyEvent e) {

    }


    public void shiftTPane() {
        JTextPane pane = tPanes.get(0);
        tPanes.remove(0);
        tPanes.add(pane);
        mapPanel.remove(pane);
        mapPanel.add(tPanes.get(0));
        mapPanel.repaint();
    }

    public void setTextPaneToBlack(JTextPane pane) {
        pane.setOpaque(false);
        UIManager.put("pane.disabledBackground", Color.WHITE);
    }

    /**
     * initiates the frames required to used the gui
     */
    /**
     * initiates the frames required to used the gui
     */
    public void initGUI() {

        for (int i = 0; i < 2; i++) {
            JTextPane tPane = new JTextPane();

            mapPanel.setBackground(Color.black);
            setTextPaneToBlack(tPane);
            tPane.setFont(getProggyFont());
            tPanes.add(tPane);
        }

        mapPanel.addKeyListener(this);


        mapPanel.add(tPanes.get(0));

        mapPanel.setBounds(0, 0, 800, 800);

    }


    /**
     * exclusively used for the GUI version.
     * renders the entityMap to the screen
     */
    public void render(Entity[][] entityMap) {
        JTextPane tPane = tPanes.get(1);
        tPane.setText("");
        for (int y = 0; y < getHeights() - 1; y++) {
            String str = "";
            for (int x = 0; x < getWidths() - 1; x++) {
                if (entityMap[y][x] == null) {
                    appendToPane(tPane, str, Color.green);
                    appendToPane(tPane, " .", Color.black);
                    str = "";
                } else if (!entityMap[y][x].isVisable()) {
                    appendToPane(tPane, str, Color.green);
                    appendToPane(tPane, " .", Color.black);
                    str = "";
                } else if (entityMap[y][x] instanceof Player) {
                    appendToPane(tPane, str, Color.green);
                    appendToPane(tPane, " " + String.valueOf(entityMap[y][x].getSymbol()), Color.magenta);
                    str = "";
                } else if (entityMap[y][x] instanceof Enemy) {
                    appendToPane(tPane, str, Color.green);
                    appendToPane(tPane, " " + String.valueOf(entityMap[y][x].getSymbol()), Color.red);
                    str = "";
                } else {
                    if (str == "") {
                        str += " " + String.valueOf(entityMap[y][x].getSymbol());
                    } else if (entityMap[y][x].getSymbol() == str.charAt(0)) {
                        str += " " + String.valueOf(entityMap[y][x].getSymbol());
                    } else {
                        appendToPane(tPane, str, Color.green);
                        str = " " + String.valueOf(entityMap[y][x].getSymbol());
                    }
                }
            }
            appendToPane(tPane, str, Color.green);
            append("\n");
        }

        shiftTPane();


    }


    public void append(String s) {
        try {
            Document doc = tPanes.get(1).getDocument();
            doc.insertString(doc.getLength(), s, null);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
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

    /**
     * gets the font that will be used for the gui.
     * This font is specifially monospaced, making all text line up perfectly
     *
     * @return
     */
    public Font getProggyFont() {
        Font f = new Font("Monospace", Font.LAYOUT_NO_START_CONTEXT, 0);
        try {
            f = Font.createFont(Font.TRUETYPE_FONT,
                    new FileInputStream(new File("ProggyClean.ttf"))).deriveFont(Font.PLAIN, 10);
        } catch (Exception e) {
            System.out.println(e);

        }
        return f;
    }

    public ArrayList<JTextPane> gettPanes() {
        return tPanes;
    }

    public void settPanes(ArrayList<JTextPane> tPanes) {
        this.tPanes = tPanes;
    }

    public JPanel getMapPanel() {
        return mapPanel;
    }

    public void setMapPanel(JPanel mapPanel) {
        this.mapPanel = mapPanel;
    }
}
