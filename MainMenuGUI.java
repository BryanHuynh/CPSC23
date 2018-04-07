import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MainMenuGUI extends Thread implements ActionListener, Runnable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int width, height;
    private int difficultySetting = -1;
    private JButton play;
    private JButton quit;
    private JLabel title;
    private JButton difficultyEasy;
    private JButton difficultyMedium;
    private JButton difficultyHard;
    private JButton mainMenu;

    private CardLayout layout = new CardLayout();
    private JFrame frame;
    private JPanel panel = new JPanel();
    private JPanel game = new JPanel();
    private JPanel menu = new JPanel();


    public MainMenuGUI(int width, int height, JFrame frame) {
        this.width = width;
        this.height = height;
        this.frame = frame;


        quit = new JButton ("Quit");
        play = new JButton ("Play");
        title = new JLabel("Rogue");
        title.setForeground(Color.green);



        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(width, height));
        layout.addLayoutComponent(panel, "Menu");

        play.addActionListener(this);
        quit.addActionListener(this);


        menu.setLayout(new FlowLayout());
        menu.add(title);
        menu.add(play);
        menu.add(quit);




        mainMenu = new JButton("Main Menu");
        difficultyHard = new JButton("Hard");
        difficultyMedium = new JButton("Medium");
        difficultyEasy = new JButton("Easy");


        mainMenu.addActionListener(this);
        difficultyEasy.addActionListener(this);
        difficultyMedium.addActionListener(this);
        difficultyHard.addActionListener(this);


        game.add(mainMenu);
        game.add(difficultyEasy);
        game.add(difficultyMedium);
        game.add(difficultyHard);

        menu.setBackground(Color.BLACK);
        game.setBackground(Color.DARK_GRAY);
        panel.add(menu, "Menu");
        panel.add(game, "Game");

        frame.add(panel);
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Rouge Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.requestFocus();

    }

    public void actionPerformed(ActionEvent event) {

        Object source = event.getSource();

        if (source == quit) {
            System.exit(0);
        } else if (source == play) {
            layout.show(panel, "Game");
        } else if (source == mainMenu) {
            layout.show(panel, "Menu");
        } else if (source == difficultyEasy) {
            synchronized (this) {
                difficultySetting = 0;
                notify();
            }
        } else if (source == difficultyMedium) {
            synchronized (this) {
                difficultySetting = 1;
                notify();
            }
        } else if (source == difficultyHard) {

            synchronized (this) {
                difficultySetting = 2;
                notify();
            }
        }


    }


    public int getDifficultySetting() {
        return difficultySetting;
    }

    public static void main(String args[]) {
        new MainMenuGUI(600, 600, new JFrame());
    }

}