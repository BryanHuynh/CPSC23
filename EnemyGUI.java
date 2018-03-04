
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyGUI {
    JPanel panel = new JPanel();
    JTable stats = new JTable();
    JProgressBar progressBar1 = new JProgressBar();
    JLabel title = new JLabel("Enemy");
    Enemy enemy;

    EnemyGUI(Enemy enemy) {
        BorderLayout layout = new BorderLayout(0, 0);
        panel.setLayout(layout);
        this.enemy = enemy;
        panel.add(stats, BorderLayout.CENTER);
        panel.add(progressBar1, BorderLayout.SOUTH);
        panel.add(title, BorderLayout.NORTH);

        DefaultTableModel model = (DefaultTableModel) stats.getModel();
        // Create a couple of columns
        model.addColumn("DATA");
        model.addColumn("VALUE");

        model.addRow(new Object[]{"HP", enemy.hp});
        model.addRow(new Object[]{"ATTACK", enemy.atk});
        model.addRow(new Object[]{"Position (x,y)", enemy.getX() + ", " + enemy.getY()});

        stats.setVisible(true);
        stats.setOpaque(false);
        panel.setBackground(Color.black);

    }

    public void update() {
        DefaultTableModel model = (DefaultTableModel) stats.getModel();
        model.setValueAt(enemy.hp, 0, 1);
        model.setValueAt(enemy.atk, 1, 1);
        model.setValueAt(enemy.getX() + ", " + enemy.getY(), 2, 1);
    }
}

