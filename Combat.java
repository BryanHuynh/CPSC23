import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Combat {
    EntityManager em;
    private Party party;
    /**
     * handles the combat involved with the player and enemies
     * @param em
     */
    Combat(EntityManager em, Party party) {
        this.em = em;
        this.party = party;
    }

    ArrayList<Enemy> toBeRemoved = new ArrayList<Enemy>();


    /**
     * looks for enemies that are in range of the player by 1 and returns a list of those enemies
     * @return
     */
    public ArrayList<Enemy> combatCheck() {
        ArrayList<Enemy> inRange = new ArrayList<Enemy>();
        for (Enemy enemy : em.getEnemies()) {
            if (em.getDistanceBetweenEntities(em.getPlayer(), enemy) < 2) {
                inRange.add(enemy);
            }
        }
        return inRange;
    }

    /*
    /**
     *
     * @param player
     * @param enemies
     * @param action

    public void startCombat(Player player, ArrayList<Enemy> enemies, String action) {
        render(em.getPlayer(), enemies);
        action(enemies, action);
    }

    public void render(Player player, ArrayList<Enemy> enemies){

    }

    /**
     * passing in a number reference the enemy in the list. attack that enemy
     * killing them causes them to be removed from the map
     * @param enemies
     * @param action

    public void action(ArrayList<Enemy> enemies, String action) {
        System.out.println(isNumeric(action));
        if (isNumeric(action)) {                                        // check if the action is a number that corresponds to an enemy
            int target = (int) Double.parseDouble(action);











            if (enemies.get(target).hp <= 0) {
                //toBeRemoved.add(enemies.get(target));
                em.getEntities().remove(enemies.get(target));
                em.getEnemies().remove(enemies.get(target));
            }
        }
        toBeRemoved = new ArrayList<Enemy>();
    }
    */



    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }




}
