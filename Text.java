import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Text {
    JFrame frame = new JFrame();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JTextArea ta1 = new JTextArea();
    JTextArea ta2 = new JTextArea();
    Text(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ta1.append("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        ta2.append("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        panel1.add(ta1);
        panel2.add(ta2);
        panel1.setBackground(Color.magenta);
        panel1.setSize(700,700);
        panel2.setBackground(Color.cyan);
        panel2.setSize(700,700);
        frame.add(panel1, BorderLayout.LINE_START);
        frame.add(panel2, BorderLayout.LINE_END);
        frame.invalidate();
        frame.validate();
        frame.repaint();
        frame.setVisible(true);
        frame.pack();
    }

    public static void main(String args[]){
        new Text();
    }
}

