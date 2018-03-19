import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CombatGUI extends Combat {
    JPanel panel;
    ArrayList<JPanel> panels = new ArrayList<>();



    public CombatGUI(EntityManager em, Party party) {
        super(em, party);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    public void setUpTabs(ArrayList<Enemy> enemies){
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (Enemy enemy : enemies) {
            CombatTabs tab = new CombatTabs(enemy);
            panel.add(tab.panel);
            tab.attackButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    damageEnemy(tab.enemy);
                    tab.update();
                }
            });
        }
    }

    public void battle(){

    }

    public void damageEnemy(Enemy enemy){
        enemy.hp -= em.getPlayer().atk;
        if (enemy.hp <= 0) {
            //toBeRemoved.add(enemies.get(target));
            em.getEntities().remove(enemy);
            em.getEnemies().remove(enemy);
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        EntityManager em = new EntityManager();
        em.createPlayer(0, 1);

        em.createEnemy(0, 0, 'e');
        em.createEnemy(1, 2, 'e');
        em.createEnemy(1, 3, 'e');
        Party party = new PartyConsole(em.getPlayer());         //to be changed
        CombatGUI cg = new CombatGUI(em, party);

        frame.add(cg.panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}