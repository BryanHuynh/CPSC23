import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Combat {
    EntityManager em;

    Combat(EntityManager em) {
        this.em = em;
    }

    ArrayList<Enemy> toBeRemoved = new ArrayList<Enemy>();

    public ArrayList<Enemy> combatCheck() {
        ArrayList<Enemy> inRange = new ArrayList<Enemy>();
        for (Enemy enemy : em.getEnemies()) {
            if (em.getDistanceBetweenEntities(em.getPlayer(), enemy) < 2) {
                inRange.add(enemy);
            }
        }

        return inRange;
    }

    public void startCombat(Player player, ArrayList<Enemy> enemies, String action) {
        render(em.getPlayer(), enemies);
        action(enemies, action);
    }

    public void render(Player player, ArrayList<Enemy> enemies){

    }


    public void action(ArrayList<Enemy> enemies, String action) {
        System.out.println(isNumeric(action));
        if (isNumeric(action)) {
            int target = (int) Double.parseDouble(action);
            enemies.get(target).hp -= em.getPlayer().atk;
            if (enemies.get(target).hp <= 0) {
                //toBeRemoved.add(enemies.get(target));
                em.getEntities().remove(enemies.get(target));
                em.getEnemies().remove(enemies.get(target));
            }
        }
        toBeRemoved = new ArrayList<Enemy>();
    }

    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
