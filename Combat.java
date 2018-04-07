import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Combat {
    EntityManager em;
    protected Party party;
    /**
     * handles the combat involved with the player and enemies
     * @param em
     */
    Combat(EntityManager em, Party party) {
        this.em = em;
        this.party = party;
    }



    /**
     * looks for enemies that are in range of the player by 1 and returns a list of those enemies
     * @return
     */
    public ArrayList<Enemy> combatCheck() {
        ArrayList<Enemy> inRange = new ArrayList<Enemy>();
        for (Enemy enemy : em.getEnemies()) {
            if (em.getDistanceBetweenEntities(em.getPlayer(), enemy) <= 1.5) {
                if(enemy.isVisable()){
                    inRange.add(enemy);
                }
            }
        }
        return inRange;
    }
}
