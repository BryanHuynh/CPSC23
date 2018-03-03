import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.Document;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

public class DialogBoxGUI extends DialogBox{
    private TextWindowGUI tw;
    private JPanel dialogPanel = new JPanel();
    private JTextPane ta = new JTextPane();

    DialogBoxGUI(TextWindow tw){
        this.tw = (TextWindowGUI) tw;
        setTextPaneToBlack(ta);
        dialogPanel.setBackground(Color.black);
        dialogPanel.add(ta, BorderLayout.LINE_START);
        ta.setFont(getProggyFont());
        ((TextWindowGUI) tw).getFrame().add(dialogPanel, BorderLayout.LINE_END);
    }

    /**
     * renders the str field to the textwindow, inside a box.
     * function auto wraps str
     */
    public void render(){
        ta.setText("DIALOG BOX \n");
        String[] words = getStr().split(" ");											//split the line to be printed into an array of words
        String bar = "-----------------------------------------------------";	//arbituary size of the bar that will box the textbox
        //System.out.println(bar.length());
        String sentence = "";
        append(bar + "\n");																//add the top bar to the textbox
        for(String word: words){															//start looping through the words
            if((sentence +" " + word).length() < bar.length()){	// check if the line we are creating is too long and can be wrapped to the next line
                sentence += " "+  word;														//if the line we creating isn't too long then we can appending it to the same line
                append(" "+word);
            }else{
                append("\n");																	//create a new line
                sentence = word;																	// since the line was too long with the word attached, we throw the word onto the next line
            }
        }
        append("\n");
        append(bar + "\n");																//close the box

        tw.getFrame().add(dialogPanel, BorderLayout.LINE_END);
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
    public Font getProggyFont(){
        Font f = new Font("Monospace", Font.LAYOUT_NO_START_CONTEXT, 0);
        try{
            f = Font.createFont(Font.TRUETYPE_FONT,
                    new FileInputStream(new File("src/ProggyClean.ttf"))).deriveFont(Font.PLAIN,35);
        }catch(Exception e){
            System.out.println(e);

        }
        return f;
    }

    public void setTextPaneToBlack(JTextPane pane){
        pane.setOpaque(false);
        UIManager.put("pane.disabledBackground", Color.WHITE);
    }
}
