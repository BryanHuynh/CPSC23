import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    //private JLayeredPane layerPane = new JLayeredPane();


    public Window() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.black);
        Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
        setMaximumSize(DimMax);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //add(layerPane,BorderLayout.CENTER);
       // layerPane.setBackground(Color.black);
        //layerPane.setBounds(0,0,800,800);
       // layerPane.setPreferredSize(new Dimension(800,800));
       // layerPane.setOpaque(true);

        setVisible(true);
    }


    public static void main(String args[]){
        new Window();
    }

}
