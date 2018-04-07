import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

public class DialogBoxGUI extends DialogBox {

    private JPanel dialogPanel = new JPanel();
    private JTextArea ta = new JTextArea();

    DialogBoxGUI() {

        setTextPaneToBlack(ta);
        dialogPanel.setBackground(Color.black);
        dialogPanel.add(ta, BorderLayout.LINE_START);
        ta.setFont(getProggyFont());
        ta.setSize(1000,500);
        ta.setMaximumSize(new Dimension(500,720));
        ta.setPreferredSize(new Dimension(500,720));
        ta.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    public JPanel getDialogPanel() {
        return dialogPanel;
    }

    public void setDialogPanel(JPanel dialogPanel) {
        this.dialogPanel = dialogPanel;
    }

    /**
     * renders the str field to the textwindow, inside a box.
     * function auto wraps str
     */
    public void render() {
        if (super.getStr().equals("")) {
            ta.setText("");
            return;

        }
        ta.setText("DIALOG BOX \n");
        String string = getStr().replace("\n", " ");
        String[] words = string.split(" ");                                            //split the line to be printed into an array of words
        String bar = "-----------------------------------";    //arbituary size of the bar that will box the textbox
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setText(string);


    }

    public void append(String s) {
        try {
            Document doc = ta.getDocument();
            doc.insertString(doc.getLength(), s, null);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
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
                    new FileInputStream(new File("ProggyClean.ttf"))).deriveFont(Font.PLAIN, 35);
        } catch (Exception e) {
            System.out.println(e);

        }
        return f;
    }

    public void setTextPaneToBlack(JTextArea pane) {
        pane.setOpaque(false);
        UIManager.put("pane.disabledBackground", Color.WHITE);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1080, 720);
        DialogBoxGUI db = new DialogBoxGUI();
        frame.add(db.getDialogPanel(), BorderLayout.CENTER);
        db.setStr("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG ");
        db.render();
        NameGenerator nameGenerator = new NameGenerator("src/Names.txt");
        long period = 0;
        long now = System.currentTimeMillis();
        long delta = 0;
        while (true) {
            delta = System.currentTimeMillis() - now;
            now = System.currentTimeMillis();
            period += delta;

            db.render();
            if (period >= 1000) {
                System.out.println("tick");
                period = 0;
                db.setStr(nameGenerator.getAName());
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}
