import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CombatTabs {

    JButton attackButton = new JButton("Attack!");
    JPanel panel;
    Enemy enemy;

    public CombatTabs(Enemy enemy) {
        this.enemy = enemy;
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(attackButton);
        panel.add(enemy.gui.panel);

    }

    public void update(){
        enemy.gui.update();
    }
}
